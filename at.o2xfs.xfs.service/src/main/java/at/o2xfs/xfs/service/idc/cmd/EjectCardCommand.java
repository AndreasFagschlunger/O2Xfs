/*
 * Copyright (c) 2017, Andreas Fagschlunger. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.xfs.service.idc.cmd;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsCancelledException;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.idc.IdcExecuteCommand;
import at.o2xfs.xfs.idc.MediaRetainedException;
import at.o2xfs.xfs.v3_00.idc.CardAction3;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.AbstractAsyncCommand;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.idc.IDCService;
import at.o2xfs.xfs.service.idc.IDCServiceListener;

/**
 * @author Andreas Fagschlunger
 */
public class EjectCardCommand extends AbstractAsyncCommand<EjectCardListener> implements IDCServiceListener {

	private class RetainCardHelper implements RetainCardCommandListener {

		private RetainCardCommand retainCardCommand;

		public RetainCardHelper(final IDCService cardReader) {
			retainCardCommand = new RetainCardCommand(cardReader);
		}

		public void retainCard() {
			retainCardCommand.addCommandListener(this);
			retainCardCommand.execute();
		}

		@Override
		public void commandSuccessful() {
			try {
				notifyCommandSuccessful();
			} finally {
				retainCardCommand.removeCommandListener(this);
			}
		}

		@Override
		public void commandCancelled() {
			try {
				notifyCommandCancelled();
			} finally {
				retainCardCommand.removeCommandListener(this);
			}
		}

		@Override
		public void commandFailed(final Exception e) {
			try {
				notifyCommandFailed(e);
			} finally {
				retainCardCommand.removeCommandListener(this);
			}
		}

		@Override
		public void cardRetained() {
			notifyCardRetained();
		}

	}

	private final static Logger LOG = LoggerFactory.getLogger(EjectCardCommand.class);

	private final XfsServiceManager serviceManager = XfsServiceManager.getInstance();

	private final IDCService idcService;

	private final Timer timer;

	private XfsCommand ejectCardCommand = null;

	private boolean cardTaken = false;

	private boolean takeTimedOut = false;

	public EjectCardCommand(final IDCService idcService) {
		if (idcService == null) {
			throw new IllegalArgumentException("idcService must not be null");
		}
		this.idcService = idcService;
		timer = new Timer();
	}

	private void notifyCardRetained() {
		for (final EjectCardListener listener : commandListeners) {
			listener.cardRetained();
		}
	}

	private void notifyCardPresented() {
		for (final EjectCardListener listener : commandListeners) {
			listener.cardPresented();
		}
	}

	private void notifyCardTaken() {
		for (final EjectCardListener listener : commandListeners) {
			listener.cardTaken();
		}
	}

	protected List<EjectCardListener> getEjectCardListeners() {
		return commandListeners;
	}

	protected IDCService getIDCService() {
		return idcService;
	}

	@Override
	protected void doExecute() {
		final String method = "doExecute()";
		try {
			idcService.addIDCServiceListener(this);
			ejectCard();
		} catch (final XfsCancelledException e) {
			if (LOG.isWarnEnabled()) {
				LOG.warn(method, "Cancelled ejecting card", e);
			}
			notifyCommandCancelled();
			return;
		} catch (final MediaRetainedException e) {
			notifyCardRetained();
			notifyCommandFailed(e);
			return;
		} catch (final Exception e) {
			notifyCommandFailed(e);
			return;
		}
		notifyCardPresented();
		synchronized (this) {
			if (!cardTaken) {
				scheduleCardRetainWarning(15000L);
				scheduleTakeTimeOut(30000L);
			}
			try {
				waitForCardTakenOrTimeout();
			} catch (final InterruptedException e) {
				if (LOG.isErrorEnabled()) {
					LOG.error(method, "Interrupted", e);
				}
				notifyCommandFailed(e);
				return;
			}
			if (cardTaken) {
				notifyCardTaken();
				notifyCommandSuccessful();
				return;
			}
		}
		final RetainCardHelper retainCardHelper = new RetainCardHelper(idcService);
		retainCardHelper.retainCard();
	}

	private void waitForCardTakenOrTimeout() throws InterruptedException {
		synchronized (this) {
			while (!cardTaken && !takeTimedOut) {
				wait();
			}
		}
	}

	private void ejectCard() throws Exception {
		ejectCardCommand = new XfsExecuteCommand<>(idcService, IdcExecuteCommand.EJECT_CARD);
		WFSResult wfsResult = null;
		try {
			wfsResult = ejectCardCommand.call();
			XfsException.throwFor(wfsResult.getResult());
		} finally {
			if (wfsResult != null) {
				serviceManager.free(wfsResult);
			}
		}
	}

	private void scheduleCardRetainWarning(final long delay) {
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				cardRetainWarning();
			}
		}, delay);
	}

	private void cardRetainWarning() {
		synchronized (this) {
			if (cardTaken) {
				return;
			}
			for (final EjectCardListener listener : commandListeners) {
				listener.cardRetainWarning();
			}
		}
	}

	private void scheduleTakeTimeOut(final long delay) {
		final TimerTask retainCardTask = new TimerTask() {

			@Override
			public void run() {
				takeTimedOut();
			}
		};
		timer.schedule(retainCardTask, delay);
	}

	private void takeTimedOut() {
		synchronized (this) {
			if (!cardTaken) {
				takeTimedOut = true;
				notifyAll();
			}
		}
	}

	@Override
	public void cancel() {
		final String method = "cancel()";
		synchronized (this) {
			if (ejectCardCommand != null) {
				try {
					ejectCardCommand.cancel();
				} catch (final XfsException e) {
					LOG.error(method, "Error cancelling eject card", e);
				}
			}
		}
	}

	@Override
	public void cardTaken() {
		final String method = "cardTaken()";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "The card has been taken by the user.");
		}
		synchronized (this) {
			cardTaken = true;
			notifyAll();
		}
	}

	@Override
	public void cardAction(final CardAction3 cardAction) {
		final String method = "cardAction(CardAction3)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "cardAction=" + cardAction);
		}
	}

	@Override
	protected void notifyCommandSuccessful() {
		super.notifyCommandSuccessful();
		idcService.removeIDCServiceListener(this);
	}

	@Override
	protected void notifyCommandCancelled() {
		super.notifyCommandCancelled();
		idcService.removeIDCServiceListener(this);
	}

	@Override
	protected void notifyCommandFailed(final Exception e) {
		super.notifyCommandFailed(e);
		idcService.removeIDCServiceListener(this);
	}
}
