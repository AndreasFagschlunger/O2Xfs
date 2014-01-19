package at.o2xfs.xfs.service.ptr;

import org.junit.Test;

import at.o2xfs.xfs.service.XfsCommandTest;

public class PTRServiceTest extends XfsCommandTest {

	@Test
	public void queryStatus() throws Exception {
		for (PTRService service : xfsServiceManager
				.getServices(PTRService.class)) {
			new PTRStatusCallable(service).call();
		}
	}

	@Test
	public void queryCapabilities() throws Exception {
		for (PTRService service : xfsServiceManager
				.getServices(PTRService.class)) {
			new PTRCapabilitiesCallable(service).call();
		}
	}
}
