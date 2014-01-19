package at.o2xfs.xfs.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class XfsCommandTest {

	protected static XfsServiceManager xfsServiceManager = null;

	@BeforeClass
	public static void setUp() throws Exception {
		xfsServiceManager = XfsServiceManager.getInstance();
		xfsServiceManager.initialize();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		if (xfsServiceManager != null) {
			xfsServiceManager.shutdown();
		}
	}

}
