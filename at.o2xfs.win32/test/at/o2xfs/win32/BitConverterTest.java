package at.o2xfs.win32;

import org.junit.Assert;
import org.junit.Test;

import at.o2xfs.common.Hex;

public class BitConverterTest {

	@Test
	public void int0x0B020003() {
		byte[] expecteds = Hex.decode("0300020B");
		long expected = 0x0B020003;
		byte[] actuals = BitConverter.getBytes(expecteds.length, expected);
		Assert.assertArrayEquals(expecteds, actuals);
		Assert.assertEquals(expected, BitConverter.toLong(actuals));
	}

	@Test
	public final void from0xFFToInt() {
		int expected = 0xFF;
		byte[] src = new byte[] { (byte) expected };
		int actual = BitConverter.toInt(src);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public final void longToBytes() {
		byte[] expecteds = Hex.decode("FFFFFFFFFFFFFF7F");
		byte[] actuals = BitConverter
				.getBytes(expecteds.length, Long.MAX_VALUE);
		Assert.assertArrayEquals(expecteds, actuals);
	}
}