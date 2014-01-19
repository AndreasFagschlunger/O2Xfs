package at.o2xfs.xfs.service.cmd.pin;

import org.junit.Test;

import at.o2xfs.xfs.pin.WFSPINSTATUS;
import at.o2xfs.xfs.service.XfsCommandTest;
import at.o2xfs.xfs.service.pin.PINService;

public class PINStatusCommandTest extends XfsCommandTest {

	@Test
	public void execute() throws Exception {
		for (final PINService pinService : xfsServiceManager
				.getServices(PINService.class)) {
			final WFSPINSTATUS pinStatus = new PINStatusCommand(pinService)
					.execute();
			System.out.println(pinStatus);
		}
	}
}
