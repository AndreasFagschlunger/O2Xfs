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

package at.o2xfs.emv.pinpad.impl.xfs;

import java.util.EnumSet;
import java.util.Set;

import at.o2xfs.emv.pinpad.PINEntryBypassedException;
import at.o2xfs.emv.pinpad.PINPadException;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.pin.PINCompletion;
import at.o2xfs.xfs.pin.PINFK;
import at.o2xfs.xfs.pin.WfsPINEntry;
import at.o2xfs.xfs.pin.WfsPINGetPIN;
import at.o2xfs.xfs.pin.WfsPINKey;
import at.o2xfs.xfs.service.cmd.event.CancelEvent;
import at.o2xfs.xfs.service.cmd.event.ErrorEvent;
import at.o2xfs.xfs.service.pin.PINService;
import at.o2xfs.xfs.service.pin.cmd.GetPINCommand;
import at.o2xfs.xfs.service.pin.cmd.GetPINCompleteEvent;
import at.o2xfs.xfs.service.pin.cmd.GetPINListener;

final class PINCommand implements GetPINListener {

	private final static Logger LOG = LoggerFactory.getLogger(PINCommand.class);

	private final PINService pinService;

	private WfsPINEntry result = null;

	private PINPadException exception = null;

	public PINCommand(PINService pinService) {
		this.pinService = pinService;
	}

	public void execute() throws PINPadException {
		final String method = "execute()";
		WfsPINGetPIN getPIN = new WfsPINGetPIN();
		getPIN.allocate();
		getPIN.setMinLen(4);
		getPIN.setMaxLen(12);
		getPIN.setAutoEnd(false);
		getPIN.setActiveKeys(getActiveKeys());
		getPIN.setTerminateKeys(getTerminateKeys());
		GetPINCommand getPINCommand = new GetPINCommand(pinService, getPIN);
		getPINCommand.addCommandListener(this);
		try {
			synchronized (this) {
				getPINCommand.execute();
				while (exception == null && result == null) {
					if (LOG.isDebugEnabled()) {
						LOG.debug(method, "Waiting ...");
					}
					wait();
				}
			}
			if (exception != null) {
				throw new PINPadException(exception);
			}
			if (PINCompletion.CANCEL.equals(result.getCompletion())) {
				throw new PINEntryBypassedException("WfsPINEntry: " + result);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private Set<PINFK> getActiveKeys() {
		Set<PINFK> activeKeys = EnumSet.noneOf(PINFK.class);
		activeKeys.add(PINFK.FK_0);
		activeKeys.add(PINFK.FK_1);
		activeKeys.add(PINFK.FK_2);
		activeKeys.add(PINFK.FK_3);
		activeKeys.add(PINFK.FK_4);
		activeKeys.add(PINFK.FK_5);
		activeKeys.add(PINFK.FK_6);
		activeKeys.add(PINFK.FK_7);
		activeKeys.add(PINFK.FK_8);
		activeKeys.add(PINFK.FK_9);
		activeKeys.add(PINFK.FK_ENTER);
		activeKeys.add(PINFK.FK_CANCEL);
		activeKeys.add(PINFK.FK_CLEAR);
		return activeKeys;
	}

	private Set<PINFK> getTerminateKeys() {
		Set<PINFK> terminateKeys = EnumSet.noneOf(PINFK.class);
		terminateKeys.add(PINFK.FK_ENTER);
		terminateKeys.add(PINFK.FK_CANCEL);
		return terminateKeys;
	}

	@Override
	public void onKeyPress(WfsPINKey pinKey) {
		final String method = "onKeyPress(WfsPINKey)";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, "pinKey=" + pinKey);
		}
	}

	@Override
	public void onEnterData() {

	}

	@Override
	public void onCancel(CancelEvent event) {
		synchronized (this) {
			exception = new PINPadException("Cancelled: event=" + event);
			notifyAll();
		}
	}

	@Override
	public void onError(ErrorEvent event) {
		synchronized (this) {
			exception = new PINPadException(event.getException());
			notifyAll();
		}
	}

	@Override
	public void onComplete(GetPINCompleteEvent event) {
		synchronized (this) {
			result = event.getPINEntry();
			notifyAll();
		}
	}
}