package at.o2xfs.win32;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

public class BitsTest {

	@Test
	public void testCharMinValue() {
		char expected = Character.MIN_VALUE;
		byte[] b = new byte[Character.BYTES];
		Bits.putChar(b, 0, expected);
		char actual = Bits.getChar(b);
		assertEquals(expected, actual);
	}

	@Test
	public void testCharMaxValue() {
		char expected = Character.MAX_VALUE;
		byte[] b = new byte[Character.BYTES];
		Bits.putChar(b, 0, expected);
		char actual = Bits.getChar(b);
		assertEquals(expected, actual);
	}

	@Test
	public void testRandomChar() {
		Random random = new Random();
		byte[] b = new byte[Character.BYTES];
		for (int i = 0; i <= 100; i++) {
			char expected = (char) random.nextInt();
			Bits.putChar(b, 0, expected);
			char actual = Bits.getChar(b);
			assertEquals(expected, actual);
		}
	}

	@Test
	public void testShortMinValue() {
		short expected = Short.MIN_VALUE;
		byte[] b = new byte[Short.BYTES];
		Bits.putShort(b, 0, expected);
		short actual = Bits.getShort(b);
		assertEquals(expected, actual);
	}

	@Test
	public void testShortMaxValue() {
		short expected = Short.MAX_VALUE;
		byte[] b = new byte[Short.BYTES];
		Bits.putShort(b, 0, expected);
		short actual = Bits.getShort(b);
		assertEquals(expected, actual);
	}

	@Test
	public void testRandomShort() {
		Random random = new Random();
		byte[] b = new byte[Short.BYTES];
		for (int i = 0; i <= 100; i++) {
			short expected = (short) random.nextInt();
			Bits.putShort(b, 0, expected);
			short actual = Bits.getShort(b);
			assertEquals(expected, actual);
		}
	}

	@Test
	public void testIntMinValue() {
		int expected = Integer.MIN_VALUE;
		byte[] b = new byte[Integer.BYTES];
		Bits.putInt(b, 0, expected);
		int actual = Bits.getInt(b);
		assertEquals(expected, actual);
	}

	@Test
	public void testIntMaxValue() {
		int expected = Integer.MAX_VALUE;
		byte[] b = new byte[Integer.BYTES];
		Bits.putInt(b, 0, expected);
		int actual = Bits.getInt(b);
		assertEquals(expected, actual);
	}

	@Test
	public void testRandomInt() {
		Random random = new Random();
		byte[] b = new byte[Integer.BYTES];
		for (int i = 0; i <= 100; i++) {
			int expected = random.nextInt();
			Bits.putInt(b, 0, expected);
			int actual = Bits.getInt(b);
			assertEquals(expected, actual);
		}
	}

	@Test
	public final void testLongMinValue() {
		long expected = Long.MIN_VALUE;
		byte[] b = new byte[Long.BYTES];
		Bits.putLong(b, 0, expected);
		long actual = Bits.getLong(b);
		assertEquals(expected, actual);
	}

	@Test
	public final void testLongMaxValue() {
		long expected = Long.MAX_VALUE;
		byte[] b = new byte[Long.BYTES];
		Bits.putLong(b, 0, expected);
		long actual = Bits.getLong(b);
		assertEquals(expected, actual);
	}

	@Test
	public void testRandomLong() {
		Random random = new Random();
		byte[] b = new byte[Long.BYTES];
		for (int i = 0; i <= 100; i++) {
			long expected = random.nextLong();
			Bits.putLong(b, 0, expected);
			long actual = Bits.getLong(b);
			assertEquals(expected, actual);
		}
	}
}
