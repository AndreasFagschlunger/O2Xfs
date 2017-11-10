package at.o2xfs.win32;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

public class ShortTest {

	@Test
	public void testMinValue() {
		short expected = Short.MIN_VALUE;
		short actual = new SHORT(expected).shortValue();
		assertEquals(expected, actual);
	}

	@Test
	public void testMaxValue() {
		short expected = Short.MAX_VALUE;
		short actual = new SHORT(expected).shortValue();
		assertEquals(expected, actual);
	}

	@Test
	public void testRandom() {
		Random random = new Random();
		byte[] b = new byte[Short.BYTES];
		SHORT actual = new SHORT((short) 0);
		for (int i = 0; i <= 100; i++) {
			short expected = (short) random.nextInt();
			actual.set(expected);
			assertEquals(expected, actual.shortValue());
		}
	}

}
