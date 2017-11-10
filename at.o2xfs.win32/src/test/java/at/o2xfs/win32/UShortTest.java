package at.o2xfs.win32;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

public class UShortTest {

	@Test
	public void testMaxValue() {
		USHORT uShort = new USHORT(USHORT.MAX_VALUE);
		assertEquals(USHORT.MAX_VALUE, uShort.intValue());
	}

	@Test
	public final void test() {
		Random random = new Random();
		USHORT ushort = new USHORT(0);
		for (int i = 0; i < 100; i++) {
			int value = random.nextInt(USHORT.MAX_VALUE + 1);
			ushort.set(value);
			assertEquals(value, ushort.intValue());
		}
	}
}