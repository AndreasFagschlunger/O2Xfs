package at.o2xfs.win32.impl;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import at.o2xfs.common.Hex;
import at.o2xfs.win32.Buffer;
import at.o2xfs.win32.BufferOverflowException;

public class Win32BufferTest {

	@Test
	public final void putAndGet() {
		byte[] expecteds = getBytes(10);
		Win32Buffer buffer = new Win32Buffer(expecteds.length);
		buffer.put(expecteds);
		Assert.assertArrayEquals(expecteds, buffer.get());
	}

	@Test(expected = BufferOverflowException.class)
	public final void bufferOverflow() {
		byte[] src = getBytes(4);
		Buffer buffer = new Win32Buffer(src.length);
		buffer.put(Hex.decode("1234567890"));
	}

	private byte[] getBytes(int length) {
		byte[] bytes = new byte[length];
		new Random().nextBytes(bytes);
		return bytes;
	}

	@Test
	public final void subBuffer() {
		byte[] src = Hex.decode("12345678");
		Buffer buffer = new Win32Buffer(src.length);
		buffer.put(src);
		Assert.assertArrayEquals(Hex.decode("3456"), buffer.subBuffer(1, 2)
				.get());
	}
}