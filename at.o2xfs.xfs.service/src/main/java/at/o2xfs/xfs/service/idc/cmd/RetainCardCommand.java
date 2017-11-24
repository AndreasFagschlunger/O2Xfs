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

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.idc.IdcExecuteCommand;
import at.o2xfs.xfs.idc.IdcMessage;
import at.o2xfs.xfs.v3_00.idc.Status3;
import at.o2xfs.xfs.v3_00.idc.RetainCard3;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.AbstractAsyncCommand;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.events.XfsEventNotification;
import at.o2xfs.xfs.service.idc.IDCService;
import at.o2xfs.xfs.service.idc.IdcFactory;

public class RetainCardCommand extends AbstractAsyncCommand<RetainCardCommandListener> implements XfsEventNotification {

	private static final Logger LOG = LoggerFactory.getLogger(RetainCardCommand.class);

	private final IDCService idcService;

	private final XfsCommand retainCardCommand;

	private boolean cardRetained = false;

	private int cardsRetained = 0;

	public RetainCardCommand(final IDCService idcService) {
		this.idcService = idcService;
		retainCardCommand = new XfsExecuteCommand<>(idcService, IdcExecuteCommand.RETAIN_CARD);
	}

	private void notifyCardRetained() {
		for (final RetainCardCommandListener l : commandListeners) {
			l.cardRetained();
		}
	}

	@Override
	protected void doExecute() {
		final String method = "doExecute()";
		try {
			final Status3 status = new IDCStatusCommand(idcService).call();
			cardsRetained = status.getCards();
			retainCardCommand.execute(this);
		} catch (final Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error executing XfsCommand: " + retainCardCommand, e);
			}
			notifyCommandFailed(e);
		}
	}

	@Override
	public void cancel() {
		final String method = "cancel()";
		try {
			retainCardCommand.cancel();
		} catch (final XfsException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error cancelling retain card", e);
			}
		}
	}

	@Override
	public void fireIntermediateEvent(final WFSResult wfsResult) {
		final String method = "fireIntermediateEvent(WFSResult)";
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "wfsResult=" + wfsResult);
			}
			final IdcMessage eventID = wfsResult.getEventID(IdcMessage.class);
			switch (eventID) {
			case EXEE_MEDIARETAINED:
				synchronized (this) {
					if (!cardRetained) {
						cardRetained = true;
						notifyCardRetained();
					}
				}
				break;
			default:
				if (LOG.isWarnEnabled()) {
					LOG.warn(method, "Unknown intermediate event: " + wfsResult);
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
	public void fireOperationCompleteEvent(final WFSResult wfsResult) {
		final String method = "fireOperationCompleteEvent(WFSResult)";
		try {
			XfsException.throwFor(wfsResult.getResult());
			final RetainCard3 retainCard = IdcFactory.create(idcService.getXfsVersion(), wfsResult.getResults(),
					RetainCard3.class);
			if (LOG.isInfoEnabled()) {
				LOG.info(method, retainCard);
			}
			if (retainCard.getCount() <= cardsRetained) {
				notifyCommandFailed(
						new Exception("Card retained: before=" + cardsRetained + ",after=" + retainCard.getCount()));
			} else {
				synchronized (this) {
					if (!cardRetained) {
						notifyCardRetained();
					}
				}
				notifyCommandSuccessful();
			}
		} catch (final XfsException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Retaining card failed", e);
			}
			notifyCommandFailed(e);
		} finally {
			if (wfsResult != null) {
				XfsServiceManager.getInstance().free(wfsResult);
			}
		}

	}

}
