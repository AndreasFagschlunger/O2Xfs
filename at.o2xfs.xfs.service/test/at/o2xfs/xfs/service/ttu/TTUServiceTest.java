package at.o2xfs.xfs.service.ttu;

import org.junit.Test;

import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.service.XfsCommandTest;
import at.o2xfs.xfs.service.XfsService;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsInfoCommand;
import at.o2xfs.xfs.ttu.TTUInfoCommand;
import at.o2xfs.xfs.ttu.WFSTTUSTATUS;

public class TTUServiceTest extends XfsCommandTest {

	private XfsService ttuService = null; // FIXME

	@Test
	public void status() throws Exception {
		XfsCommand xfsCommand = new XfsInfoCommand(ttuService,
				TTUInfoCommand.WFS_INF_TTU_STATUS);
		WFSResult wfsResult = null;
		try {
			wfsResult = xfsCommand.call();
			WFSTTUSTATUS ttuStatus = new WFSTTUSTATUS(wfsResult.getResults());
			System.out.println(ttuStatus);
		} finally {
			if (wfsResult != null) {
				xfsServiceManager.free(wfsResult);
			}
		}
	}
}
