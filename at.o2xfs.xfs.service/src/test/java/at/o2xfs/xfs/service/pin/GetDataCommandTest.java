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

package at.o2xfs.xfs.service.pin;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.pin.PINFDK;
import at.o2xfs.xfs.pin.PINFK;
import at.o2xfs.xfs.pin.WFSPINFDK;
import at.o2xfs.xfs.pin.WFSPINFUNCKEYDETAIL;
import at.o2xfs.xfs.pin.WfsPINGetData;
import at.o2xfs.xfs.pin.WfsPINKey;
import at.o2xfs.xfs.service.XfsCommandTest;
import at.o2xfs.xfs.service.pin.cmd.PINDataListener;
import at.o2xfs.xfs.service.pin.cmd.PINFunctionKeysCommand;
import at.o2xfs.xfs.service.pin.cmd.PINGetDataCommand;

import java.util.EnumSet;
import java.util.Set;

import org.junit.Test;

public class GetDataCommandTest
		extends XfsCommandTest
		implements PINDataListener {

	private static final Logger LOG = LoggerFactory.getLogger(GetDataCommandTest.class);

	private boolean running = false;

	@Test
	public void test() throws InterruptedException, XfsException {
		for (PINService pinService : serviceManager.getServices(PINService.class)) {
			PINGetDataCommand command = new PINGetDataCommand(pinService, createGetData(pinService));
			command.addCommandListener(this);
			synchronized (this) {
				command.execute();
				running = true;
				while (running) {
					wait();
				}
			}
		}
	}

	private WfsPINGetData createGetData(PINService pinService) throws XfsException {
		final String method = "createGetData(PINService)";
		final WFSPINFUNCKEYDETAIL funcKeyDetail = new PINFunctionKeysCommand(pinService).call();
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "funcKeyDetail=" + funcKeyDetail);
		}
		final WfsPINGetData result = new WfsPINGetData();
		result.allocate();
		result.setActiveKeys(funcKeyDetail.getFunctionKeys());
		final Set<PINFDK> activeFDKs = EnumSet.noneOf(PINFDK.class);
		for (final WFSPINFDK pinFDK : funcKeyDetail.getFDKs()) {
			activeFDKs.add(pinFDK.getFDK());
		}
		result.setActiveFDKs(activeFDKs);
		result.setTerminateKeys(EnumSet.of(PINFK.FK_CANCEL, PINFK.FK_ENTER));
		result.setMaxLen(0);
		result.setAutoEnd(false);
		return result;
	}

	@Override
	public void commandCancelled() {
		stop();
	}

	@Override
	public void commandFailed(Exception e) {
		e.printStackTrace();
		stop();
	}

	@Override
	public void commandSuccessful() {
		stop();
	}

	private void stop() {
		synchronized (this) {
			running = false;
			notifyAll();
		}
	}

	@Override
	public void keyPressed(WfsPINKey pinKey) {
		LOG.info("keyPressed(WfsPINKey)", pinKey);
	}
}