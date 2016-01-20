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

import at.o2xfs.win32.ZList;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.idc.IDCExecuteCommand;
import at.o2xfs.xfs.idc.IDCTrack;
import at.o2xfs.xfs.idc.WFSIDCCARDDATA;
import at.o2xfs.xfs.service.XfsCommandTest;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class WriteRawData
		extends XfsCommandTest {

	private final String TRACK_1 = "11111=11111111=111111";
	private final String TRACK_2 = "2567123498765678=01042344=222222";
	private final String TRACK_3 = "33333=33333333=333333";

	@Test
	public void writeRawData() throws XfsException {
		for (final IDCService idcService : serviceManager.getServices(IDCService.class)) {
			final List<WFSIDCCARDDATA> cardDataList = new ArrayList<WFSIDCCARDDATA>();
			cardDataList.add(createCardData(idcService.getXfsVersion(), IDCTrack.TRACK1, TRACK_1));
			cardDataList.add(createCardData(idcService.getXfsVersion(), IDCTrack.TRACK2, TRACK_2));
			cardDataList.add(createCardData(idcService.getXfsVersion(), IDCTrack.TRACK3, TRACK_3));
			System.out.println("cardDataList=" + cardDataList);
			WFSResult wfsResult = null;
			try {
				final XfsCommand xfsCommand = new XfsExecuteCommand<IDCExecuteCommand>(	idcService,
																						IDCExecuteCommand.WRITE_RAW_DATA,
																						new ZList(cardDataList));
				wfsResult = xfsCommand.call();
			} finally {
				if (wfsResult != null) {
					serviceManager.free(wfsResult);
				}
			}
		}

	}

	private WFSIDCCARDDATA createCardData(final XfsVersion xfsVersion, IDCTrack dataSource, final String data) {
		WFSIDCCARDDATA cardData = new WFSIDCCARDDATA(xfsVersion);
		cardData.allocate();
		cardData.setDataSource(dataSource);
		cardData.setData(data.getBytes());
		return cardData;
	}
}