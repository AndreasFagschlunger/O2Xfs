/*
 * Copyright (c) 2017, Andreas Fagschlunger. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.common;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class BytesTest {

	@Test
	public final void toInt() {
		for (int expected = 0; expected <= 255; expected++) {
			int actual = Bytes.toInt((byte) expected);
			Assert.assertEquals(expected, actual);
		}
	}

	@Test
	public final void left() {
		byte[] input = Hex.decode("123456");
		byte[] expecteds = Hex.decode("1234");
		byte[] actuals = Bytes.left(input, 2);
		Assert.assertArrayEquals(expecteds, actuals);
	}

	@Test
	public final void right() {
		byte[] input = Hex.decode("123456");
		byte[] expecteds = Hex.decode("3456");
		byte[] actuals = Bytes.right(input, 2);
		Assert.assertArrayEquals(expecteds, actuals);
	}

	@Test
	public final void simpleLeftPad() {
		byte[] expecteds = Hex.decode("001234");
		byte[] actuals = Bytes.leftPad(Hex.decode("1234"), 3);
		Assert.assertArrayEquals(expecteds, actuals);
	}

	@Test
	public final void simpleLeftPadWithPadByte() {
		byte[] expecteds = Hex.decode("FF1234");
		byte[] actuals = Bytes.leftPad(Hex.decode("1234"), 3, 0xFF);
		Assert.assertArrayEquals(expecteds, actuals);
	}

	@Test
	public final void leftPadEmptyBytesWith0xFF() {
		byte[] expecteds = Hex.decode("FFFFFFFFFF");
		byte[] actuals = Bytes.leftPad(Bytes.EMPTY, 5, 0xFF);
		Assert.assertArrayEquals(expecteds, actuals);
	}

	@Test
	public final void simpleRightPad() {
		byte[] expecteds = Hex.decode("123400");
		byte[] actuals = Bytes.rightPad(Hex.decode("1234"), 3);
		Assert.assertArrayEquals(expecteds, actuals);
	}

	@Test
	public final void simpleRightPadWithPadByte() {
		byte[] expecteds = Hex.decode("1234FF");
		byte[] actuals = Bytes.rightPad(Hex.decode("1234"), 3, 0xFF);
		Assert.assertArrayEquals(expecteds, actuals);
	}

	@Test
	public final void testMid() {
		byte[] expecteds = Hex.decode("34");
		byte[] actuals = Bytes.mid(Hex.decode("123456"), 1, 1);
		Assert.assertArrayEquals(expecteds, actuals);
	}

	@Test
	public final void midCopy() {
		byte[] expecteds = Hex.decode("123456");
		byte[] actuals = Bytes.mid(expecteds, 0, expecteds.length);
		Assert.assertArrayEquals(expecteds, actuals);
	}

	@Test
	public final void fromZeroToFFBinaryString() {
		for (int i = 0; i <= 255; i++) {
			String expected = leftPad(Integer.toBinaryString(i), 8, '0');
			String actual = Bytes.toBinaryString(new byte[] { (byte) i });
			Assert.assertEquals(expected, actual);
		}
	}

	@Test
	public final void binaryStringWithSeparator() {
		byte[] bytes = Hex.decode("1234");
		String expected = "00010010 00110100";
		String actual = Bytes.toBinaryString(bytes, ' ');
		Assert.assertEquals(expected, actual);
	}

	private String leftPad(String value, int length, char padChar) {
		char[] oldChars = value.toCharArray();
		if (oldChars.length >= length) {
			return value;
		}
		char[] newChars = new char[length];
		Arrays.fill(newChars, padChar);
		System.arraycopy(oldChars, 0, newChars, newChars.length
				- oldChars.length, oldChars.length);
		return new String(newChars);
	}
}