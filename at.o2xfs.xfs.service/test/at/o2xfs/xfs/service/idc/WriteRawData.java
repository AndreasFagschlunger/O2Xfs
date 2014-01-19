package at.o2xfs.xfs.service.idc;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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

public class WriteRawData extends XfsCommandTest {

	private final String TRACK_1 = "11111=11111111=111111";
	private final String TRACK_2 = "2567123498765678=01042344=222222";
	private final String TRACK_3 = "33333=33333333=333333";

	@Test
	public void writeRawData() throws InterruptedException, XfsException {
		for (final IDCService idcService : xfsServiceManager
				.getServices(IDCService.class)) {
			final List<WFSIDCCARDDATA> cardDataList = new ArrayList<WFSIDCCARDDATA>();
			cardDataList.add(createCardData(idcService.getXfsVersion(),
					IDCTrack.WFS_IDC_TRACK1, TRACK_1));
			cardDataList.add(createCardData(idcService.getXfsVersion(),
					IDCTrack.WFS_IDC_TRACK2, TRACK_2));
			cardDataList.add(createCardData(idcService.getXfsVersion(),
					IDCTrack.WFS_IDC_TRACK3, TRACK_3));
			System.out.println("cardDataList=" + cardDataList);
			WFSResult wfsResult = null;
			try {
				final XfsCommand xfsCommand = new XfsExecuteCommand(idcService,
						IDCExecuteCommand.WRITE_RAW_DATA, new ZList(
								cardDataList));
				wfsResult = xfsCommand.call();
			} finally {
				if (wfsResult != null) {
					xfsServiceManager.free(wfsResult);
				}
			}
		}

	}

	private WFSIDCCARDDATA createCardData(final XfsVersion xfsVersion,
			IDCTrack dataSource, final String data) {
		WFSIDCCARDDATA cardData = new WFSIDCCARDDATA(xfsVersion);
		cardData.allocate();
		cardData.setDataSource(dataSource);
		cardData.setData(data.getBytes());
		return cardData;
	}
}
