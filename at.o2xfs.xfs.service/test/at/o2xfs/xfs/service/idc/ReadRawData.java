package at.o2xfs.xfs.service.idc;

import org.junit.Test;

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

public class ReadRawData extends XfsCommandTest {

	private static final Logger LOG = LoggerFactory
			.getLogger(ReadRawData.class);

	@Test
	public void readRawData() throws InterruptedException, XfsException {
		final String method = "readRawData()";
		for (final IDCService idcService : xfsServiceManager
				.getServices(IDCService.class)) {
			DWORD readData = Bitmask.of(IDCTrack.WFS_IDC_CHIP,
					IDCTrack.WFS_IDC_TRACK1, IDCTrack.WFS_IDC_TRACK2,
					IDCTrack.WFS_IDC_TRACK3);
			WFSResult wfsResult = null;
			try {
				XfsCommand xfsCommand = new XfsExecuteCommand(idcService,
						IDCExecuteCommand.READ_RAW_DATA, readData);
				wfsResult = xfsCommand.call();
				LOG.debug(method, "wfsResult=" + wfsResult);
				ZList cardData = new ZList(wfsResult.getResults());
				for (Pointer pCardData : cardData) {
					WFSIDCCARDDATA data = new WFSIDCCARDDATA(
							idcService.getXfsVersion(), pCardData);
					System.out.println("data=" + data);
				}
			} finally {
				if (wfsResult != null) {
					xfsServiceManager.free(wfsResult);
				}
			}
		}

	}
}
