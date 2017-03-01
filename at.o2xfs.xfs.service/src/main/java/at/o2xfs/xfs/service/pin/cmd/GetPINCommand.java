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

package at.o2xfs.xfs.service.pin.cmd;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.pin.PINExecuteCommand;
import at.o2xfs.xfs.pin.PINMessage;
import at.o2xfs.xfs.pin.WfsPINEntry;
import at.o2xfs.xfs.pin.WfsPINGetPIN;
import at.o2xfs.xfs.pin.WfsPINKey;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.AbstractAsyncXfsCommand;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.pin.PINService;

public class GetPINCommand extends
		AbstractAsyncXfsCommand<GetPINListener, GetPINCompleteEvent> {

	private static final Logger LOG = LoggerFactory
			.getLogger(GetPINCommand.class);

	private final PINService pinService;

	private final WfsPINGetPIN pinGetPIN;

	public GetPINCommand(final PINService pinService,
			final WfsPINGetPIN pinGetPIN) {
		if (pinService == null) {
			throw new NullPointerException("pinService must not be null");
		}
		this.pinService = pinService;
		if (pinGetPIN == null) {
			throw new NullPointerException("WfsPINGetPIN must not be null");
		}
		this.pinGetPIN = pinGetPIN;
	}

	@Override
	protected XfsCommand createCommand() {
		return new XfsExecuteCommand(pinService, PINExecuteCommand.GET_PIN,
				pinGetPIN);
	}

	@Override
	protected void doExecute() throws XfsException {
		super.doExecute();
		if (pinService.getXfsVersion().isLT(XfsVersion.V3_10)) {
			fireEnterData();
		}
	}

	@Override
	public void fireIntermediateEvent(WFSResult wfsResult) {
		final String method = "fireIntermediateEvent(WFSResult)";
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "wfsResult=" + wfsResult);
			}
			PINMessage message = wfsResult.getEventID(PINMessage.class);
			switch (message) {
				case WFS_EXEE_PIN_KEY:
					WfsPINKey pinKey = new WfsPINKey(wfsResult.getResults());
					fireKeyPress(pinKey);
					break;
				case WFS_EXEE_PIN_ENTERDATA:
					fireEnterData();
					break;
				default:
					break;
			}
		} finally {
			XfsServiceManager.getInstance().free(wfsResult);
		}
	}

	private void fireKeyPress(WfsPINKey pinKey) {
		final String method = "fireKeyPress(WfsPINKey)";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, "Listeners: " + listeners.size() + ", WfsPINKey: "
					+ pinKey);
		}
		for (GetPINListener l : listeners) {
			l.onKeyPress(new WfsPINKey(pinKey));
		}
	}

	private void fireEnterData() {
		for (GetPINListener l : listeners) {
			l.onEnterData();
		}
	}

	@Override
	protected GetPINCompleteEvent createCompleteEvent(Pointer results) {
		final String method = "createCompleteEvent(Pointer)";
		WfsPINEntry pinEntry = new WfsPINEntry(results);
		if (LOG.isInfoEnabled()) {
			LOG.info(method, "WfsPINEntry: " + pinEntry);
		}
		return GetPINCompleteEvent.create(pinEntry);
	}
}