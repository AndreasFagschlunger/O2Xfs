package at.o2xfs.win32;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UINTTest {

	@Test
	public final void test() {
		UINT uInt = new UINT(UINT.MAX_VALUE);
		assertEquals(UINT.MAX_VALUE, uInt.longValue());
	}
}
