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

package at.o2xfs.xfs.service.cmd.pin;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsCancelledException;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.pin.PINExecuteCommand;
import at.o2xfs.xfs.pin.PINMessage;
import at.o2xfs.xfs.pin.WFSPINDATA;
import at.o2xfs.xfs.pin.WFSPINGETDATA;
import at.o2xfs.xfs.pin.WFSPINKEY;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.AbstractAsyncCommand;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.events.XfsEventNotification;
import at.o2xfs.xfs.service.pin.PINService;

public class PINGetDataCommand extends AbstractAsyncCommand<PINDataListener>
		implements XfsEventNotification {

	private final static Logger LOG = LoggerFactory
			.getLogger(PINGetDataCommand.class);

	private PINService pinService = null;

	private XfsCommand xfsCommand = null;

	private WFSPINGETDATA pinGetData = null;

	public PINGetDataCommand(final PINService pinService) {
		this.pinService = pinService;
		pinGetData = new WFSPINGETDATA();
		pinGetData.allocate();
	}

	private void notifyKeyPressed(final WFSPINKEY pinKey) {
		for (final PINDataListener listener : commandListeners) {
			listener.keyPressed(new WFSPINKEY(pinKey));
		}
	}

	@Override
	protected void executeInternal() {
		final String method = "executeInternal()";
		try {
			synchronized (this) {
				xfsCommand = new XfsExecuteCommand(pinService,
						PINExecuteCommand.WFS_CMD_PIN_GET_DATA, pinGetData);
				xfsCommand.execute(this);
			}
		} catch (XfsException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error executing XfsCommand: " + xfsCommand,
						e);
			}
			notifyCommandFailed(e);
		}
	}

	@Override
	public void cancel() {
		final String method = "cancel()";
		synchronized (this) {
			if (xfsCommand != null) {
				try {
					xfsCommand.cancel();
				} catch (XfsException e) {
					if (LOG.isErrorEnabled()) {
						LOG.error(method, "Error cancelling XfsCommand: "
								+ xfsCommand, e);
					}
				}
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
			final PINMessage pinMessage = wfsResult
					.getEventID(PINMessage.class);
			switch (pinMessage) {
				case WFS_EXEE_PIN_KEY:
					final WFSPINKEY pinKey = new WFSPINKEY(
							wfsResult.getBuffer());
					if (LOG.isInfoEnabled()) {
						LOG.info(method, "pinKey=" + pinKey);
					}
					notifyKeyPressed(pinKey);
					break;
			}
		} finally {
			XfsServiceManager.getInstance().free(wfsResult);
		}
	}

	@Override
	public void fireOperationCompleteEvent(final WFSResult wfsResult) {
		final String method = "fireOperationCompleteEvent(WFSResult)";
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "wfsResult=" + wfsResult);
			}
			try {
				XfsException.throwFor(wfsResult.getResult());
				final WFSPINDATA pinData = new WFSPINDATA(
						pinService.getXfsVersion(), wfsResult.getBuffer());
				if (LOG.isInfoEnabled()) {
					LOG.info(method, "pinData=" + pinData);
				}
				notifyCommandSuccessful();
			} catch (XfsCancelledException e) {
				if (LOG.isInfoEnabled()) {
					LOG.info(method, "XfsCommand was cancelled", e);
				}
				notifyCommandCancelled();
			} catch (XfsException e) {
				if (LOG.isErrorEnabled()) {
					LOG.error(method, "Command failed", e);
				}
				notifyCommandFailed(e);
			}
		} finally {
			XfsServiceManager.getInstance().free(wfsResult);
		}
	}

	public WFSPINGETDATA getPinGetData() {
		return pinGetData;
	}

}
