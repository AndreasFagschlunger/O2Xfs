package at.o2xfs.xfs;

import org.junit.Assert;
import org.junit.Test;

public class XfsVersionRangeTest {

	@Test
	public void fromV3_00ToV3_10() {
		XfsVersionRange range = new XfsVersionRange(XfsVersion.V3_00, XfsVersion.V3_10);
		Assert.assertFalse(range.contains(XfsVersion.V2_00));
		Assert.assertTrue(range.contains(XfsVersion.V3_00));
		Assert.assertTrue(range.contains(XfsVersion.V3_10));
		Assert.assertFalse(range.contains(XfsVersion.V3_20));
	}

	@Test
	public void fromV3_00ToNull() {
		XfsVersionRange range = new XfsVersionRange(XfsVersion.V3_00, null);
		Assert.assertFalse(range.contains(XfsVersion.V2_00));
		Assert.assertTrue(range.contains(XfsVersion.V3_00));
		Assert.assertTrue(range.contains(XfsVersion.V3_10));
		Assert.assertTrue(range.contains(XfsVersion.V3_20));
	}

	@Test
	public void fromNullToV3_10() {
		XfsVersionRange range = new XfsVersionRange(null, XfsVersion.V3_10);
		Assert.assertTrue(range.contains(XfsVersion.V2_00));
		Assert.assertTrue(range.contains(XfsVersion.V3_00));
		Assert.assertTrue(range.contains(XfsVersion.V3_10));
		Assert.assertFalse(range.contains(XfsVersion.V3_20));
	}
}