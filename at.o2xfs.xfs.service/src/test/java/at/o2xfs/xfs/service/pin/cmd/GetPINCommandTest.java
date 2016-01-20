/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
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
import at.o2xfs.xfs.pin.PINFK;
import at.o2xfs.xfs.pin.WfsPINGetPIN;
import at.o2xfs.xfs.pin.WfsPINKey;
import at.o2xfs.xfs.service.XfsCommandTest;
import at.o2xfs.xfs.service.cmd.event.CancelEvent;
import at.o2xfs.xfs.service.cmd.event.ErrorEvent;
import at.o2xfs.xfs.service.pin.PINService;

import java.util.EnumSet;
import java.util.Set;

import org.junit.Test;

public class GetPINCommandTest
		extends XfsCommandTest
		implements GetPINListener {

	private static final Logger LOG = LoggerFactory.getLogger(GetPINCommandTest.class);

	private boolean running = false;

	@Test
	public final void test() throws InterruptedException {
		for (PINService pinService : serviceManager.getServices(PINService.class)) {
			WfsPINGetPIN pinGetPIN = new WfsPINGetPIN();
			pinGetPIN.allocate();
			pinGetPIN.setMinLen(4);
			pinGetPIN.setMaxLen(12);
			pinGetPIN.setEcho('*');
			Set<PINFK> activeKeys = EnumSet.range(PINFK.FK_0, PINFK.FK_CANCEL);
			pinGetPIN.setActiveKeys(activeKeys);
			pinGetPIN.setTerminateKeys(EnumSet.range(PINFK.FK_ENTER, PINFK.FK_CANCEL));
			GetPINCommand pinCommand = new GetPINCommand(pinService, pinGetPIN);
			pinCommand.addCommandListener(this);
			synchronized (this) {
				pinCommand.execute();
				running = true;
				while (running) {
					wait();
				}
			}
		}
	}

	@Override
	public void onCancel(CancelEvent event) {
		LOG.info("onCancel(CancelEvent)", event);
		stop();
	}

	@Override
	public void onError(ErrorEvent event) {
		LOG.info("onError(ErrorEvent)", event);
		stop();
	}

	@Override
	public void onComplete(GetPINCompleteEvent event) {
		LOG.info("onComplete(GetPINCompleteEvent)", event);
		stop();
	}

	private void stop() {
		synchronized (this) {
			running = false;
			notifyAll();
		}
	}

	@Override
	public void onKeyPress(WfsPINKey pinKey) {
		LOG.info("onKeyPress(WfsPINKey)", pinKey);
	}

	@Override
	public void onEnterData() {
		LOG.info("", "");
	}
}