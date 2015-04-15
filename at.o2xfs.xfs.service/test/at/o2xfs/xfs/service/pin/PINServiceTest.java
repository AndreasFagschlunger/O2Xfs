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

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.ZList;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.pin.PINInfoCommand;
import at.o2xfs.xfs.pin.WFSPINCAPS;
import at.o2xfs.xfs.pin.WFSPINFUNCKEYDETAIL;
import at.o2xfs.xfs.pin.WFSPINKEYDETAIL;
import at.o2xfs.xfs.pin.WFSPINSTATUS;
import at.o2xfs.xfs.service.XfsCommandTest;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsInfoCommand;

import org.junit.Test;

public class PINServiceTest
		extends XfsCommandTest {

	@Test
	public void pinStatus() throws Exception {
		for (final PINService pinService : serviceManager.getServices(PINService.class)) {
			XfsCommand xfsCommand = new XfsInfoCommand<PINInfoCommand>(pinService, PINInfoCommand.STATUS);
			WFSResult wfsResult = null;
			try {
				wfsResult = xfsCommand.call();
				WFSPINSTATUS pinStatus = new WFSPINSTATUS(pinService.getXfsVersion(), wfsResult.getResults());
				System.out.println(pinStatus);
			} finally {
				if (wfsResult != null) {
					serviceManager.free(wfsResult);
				}
			}
		}
	}

	@Test
	public void pinCapabilities() throws Exception {
		for (final PINService pinService : serviceManager.getServices(PINService.class)) {
			XfsCommand xfsCommand = new XfsInfoCommand<PINInfoCommand>(pinService, PINInfoCommand.CAPABILITIES);
			WFSResult wfsResult = null;
			try {
				wfsResult = xfsCommand.call();
				WFSPINCAPS wfspincaps = new WFSPINCAPS(pinService.getXfsVersion(), wfsResult.getResults());
				System.out.println(wfspincaps);
			} finally {
				if (wfsResult != null) {
					serviceManager.free(wfsResult);
				}
			}
		}
	}

	@Test
	public void keyDetail() throws Exception {
		for (final PINService pinService : serviceManager.getServices(PINService.class)) {
			final XfsCommand xfsCommand = new XfsInfoCommand<PINInfoCommand>(pinService, PINInfoCommand.KEY_DETAIL);
			WFSResult wfsResult = null;
			try {
				wfsResult = xfsCommand.call();
				ZList pKeyDetails = new ZList(wfsResult.getResults());
				for (Pointer pKeyDetail : pKeyDetails) {
					WFSPINKEYDETAIL keyDetail = new WFSPINKEYDETAIL(pinService.getXfsVersion(), pKeyDetail);
					System.out.println(keyDetail);
				}
			} finally {
				if (wfsResult != null) {
					serviceManager.free(wfsResult);
				}
			}
		}
	}

	@Test
	public void funcKeyDetail() throws Exception {
		for (final PINService pinService : serviceManager.getServices(PINService.class)) {
			final ULONG fdkMask = new ULONG();
			fdkMask.set(0xFFFFFFFFL);
			XfsCommand xfsCommand = new XfsInfoCommand<PINInfoCommand>(	pinService,
																		PINInfoCommand.FUNCKEY_DETAIL,
																		fdkMask);
			WFSResult wfsResult = null;
			try {
				wfsResult = xfsCommand.call();
				WFSPINFUNCKEYDETAIL funcKeyDetail = new WFSPINFUNCKEYDETAIL(wfsResult.getResults());
				System.out.println(funcKeyDetail);
			} finally {
				if (wfsResult != null) {
					serviceManager.free(wfsResult);
				}
			}
		}
	}
}