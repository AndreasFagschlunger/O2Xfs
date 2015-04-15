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

package at.o2xfs.xfs.service.idc;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.DWORD;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.ZList;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.idc.IDCExecuteCommand;
import at.o2xfs.xfs.idc.IDCTrack;
import at.o2xfs.xfs.idc.WFSIDCCARDDATA;
import at.o2xfs.xfs.service.XfsCommandTest;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.util.Bitmask;

import org.junit.Test;

public class ReadRawData
		extends XfsCommandTest {

	private static final Logger LOG = LoggerFactory.getLogger(ReadRawData.class);

	@Test
	public void readRawData() throws XfsException {
		final String method = "readRawData()";
		for (final IDCService idcService : serviceManager.getServices(IDCService.class)) {
			DWORD readData = Bitmask.of(IDCTrack.CHIP, IDCTrack.TRACK1, IDCTrack.TRACK2, IDCTrack.TRACK3);
			WFSResult wfsResult = null;
			try {
				XfsCommand xfsCommand = new XfsExecuteCommand<IDCExecuteCommand>(	idcService,
																					IDCExecuteCommand.READ_RAW_DATA,
																					readData);
				wfsResult = xfsCommand.call();
				LOG.debug(method, "wfsResult=" + wfsResult);
				ZList cardData = new ZList(wfsResult.getResults());
				for (Pointer pCardData : cardData) {
					WFSIDCCARDDATA data = new WFSIDCCARDDATA(idcService.getXfsVersion(), pCardData);
					System.out.println("data=" + data);
				}
			} finally {
				if (wfsResult != null) {
					serviceManager.free(wfsResult);
				}
			}
		}
	}
}