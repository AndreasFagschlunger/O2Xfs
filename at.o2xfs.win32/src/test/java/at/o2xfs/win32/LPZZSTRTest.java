package at.o2xfs.win32;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class LPZZSTRTest {

	@Test
	public final void test() {
		String[] expecteds = new String[] { "Value 1", "Value 2" };
		LPZZSTR p = new LPZZSTR(expecteds);
		assertArrayEquals(expecteds, p.get());
	}
}