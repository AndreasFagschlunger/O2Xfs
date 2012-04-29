/*
 * Copyright (c) 2012, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.xfs.service.cmd.idc.ejectcard;

import java.util.List;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsCancelledException;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.idc.IDCExecuteCommand;
import at.o2xfs.xfs.idc.IDCMessage;
import at.o2xfs.xfs.idc.WFSIDCCARDACT;
import at.o2xfs.xfs.idc.WFSIDCRETAINCARD;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.AbstractAsyncCommand;
import at.o2xfs.xfs.service.events.XfsEventNotification;
import at.o2xfs.xfs.service.idc.IDCService;
import at.o2xfs.xfs.service.idc.IDCServiceListener;

public class EjectCardCommand extends AbstractAsyncCommand<EjectCardListener>
		implements XfsEventNotification, IDCServiceListener {

	private final static Logger LOG = LoggerFactory
			.getLogger(EjectCardCommand.class);

	private IDCService idcService = null;

	private Object stateLock = null;

	private EjectCardState state = null;

	public EjectCardCommand(final IDCService idcService) {
		this.idcService = idcService;
		stateLock = new Object();
		state = new CardPresentState(this);
	}

	protected List<EjectCardListener> getEjectCardListeners() {
		return commandListeners;
	}

	protected IDCService getIDCService() {
		return idcService;
	}

	protected void setState(final EjectCardState state) {
		synchronized (stateLock) {
			this.state = state;
		}
	}

	@Override
	protected void executeInternal() {
		synchronized (stateLock) {
			state.ejectCard();
		}
	}

	protected void cardRetainWarning() {
		synchronized (stateLock) {
			state.cardRetainWarning();
		}
	}

	protected void retainCard() {
		synchronized (stateLock) {
			state.retainCard();
		}
	}

	@Override
	public void cancel() {
		synchronized (stateLock) {
			state.cancel();
		}
	}

	@Override
	public void fireIntermediateEvent(final WFSResult wfsResult) {
		try {
			final String method = "fireIntermediateEvent(WFSResult)";
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "wfsResult=" + wfsResult);
			}
			switch (wfsResult.getEventID(IDCMessage.class)) {
				case WFS_EXEE_IDC_MEDIARETAINED:
					synchronized (stateLock) {
						state.cardRetained();
					}
					break;
				default:
					if (LOG.isWarnEnabled()) {
						LOG.warn(method, "Unknown intermediate event: "
								+ wfsResult);
					}
			}
		} finally {
			if (wfsResult != null) {
				XfsServiceManager.getInstance().free(wfsResult);
			}
		}
	}

	@Override
	synchronized public void fireOperationCompleteEvent(
			final WFSResult wfsResult) {
		try {
			final String method = "fireOperationCompleteEvent(WFSResult)";
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "wfsResult=" + wfsResult);
			}
			switch (wfsResult.getCommandCode(IDCExecuteCommand.class)) {
				case WFS_CMD_IDC_EJECT_CARD:
					synchronized (stateLock) {
						try {
							XfsException.throwFor(wfsResult.getResult());
							state.cardPresented();
						} catch (final XfsCancelledException e) {
							state.cancelled();
						} catch (final XfsException e) {
							if (LOG.isErrorEnabled()) {
								LOG.error(method, "Ejecting card failed", e);
							}
							state.failed(e);
						}
					}
					break;
				case WFS_CMD_IDC_RETAIN_CARD:
					synchronized (stateLock) {
						try {
							XfsException.throwFor(wfsResult.getResult());
							final WFSIDCRETAINCARD retainCard = new WFSIDCRETAINCARD(
									wfsResult.getBuffer());
							if (LOG.isDebugEnabled()) {
								LOG.debug(method, "retainCard=" + retainCard);
							}
							state.cardRetained();
						} catch (final XfsException e) {
							if (LOG.isErrorEnabled()) {
								LOG.error(method, "Retaining card failed", e);
							}
							state.failed(e);
						}
					}
					break;
			}
		} finally {
			if (wfsResult != null) {
				XfsServiceManager.getInstance().free(wfsResult);
			}
		}
	}

	@Override
	public void cardTaken() {
		final String method = "cardTaken()";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "The card has been taken by the user.");
		}
		synchronized (stateLock) {
			state.cardTaken();
		}
	}

	@Override
	public void cardAction(final WFSIDCCARDACT cardAction) {
		final String method = "cardAction(WFSIDCCARDACT)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "cardAction=" + cardAction);
		}
	}
}
