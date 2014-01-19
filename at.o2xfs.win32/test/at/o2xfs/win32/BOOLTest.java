package at.o2xfs.win32;

import junit.framework.Assert;

import org.junit.Test;

public class BOOLTest {

	@Test
	public final void testTrue() {
		BOOL bType = new BOOL(true);
		Assert.assertTrue(bType.booleanValue());
	}

	@Test
	public final void testFalse() {
		BOOL bType = new BOOL(false);
		Assert.assertFalse(bType.booleanValue());
	}
}