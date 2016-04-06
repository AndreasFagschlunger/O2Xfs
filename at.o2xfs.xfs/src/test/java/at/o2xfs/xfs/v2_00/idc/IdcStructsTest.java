package at.o2xfs.xfs.v2_00.idc;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.idc.WFSIDCCAPS;
import at.o2xfs.xfs.idc.WfsIDCStatus;

@Ignore
public class IdcStructsTest {

	@Test
	public void testStatus() {
		WfsIDCStatus expected = createStatus();
		WfsIDCStatus actual = new WfsIDCStatus(XfsVersion.V2_00, expected);
		Assert.assertEquals(expected, actual);
	}

	private native WfsIDCStatus createStatus();

	@Test
	public void testCaps() {

	}

	private native WFSIDCCAPS createCaps();
}
