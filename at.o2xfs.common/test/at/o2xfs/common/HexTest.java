/*
 * Copyright (c) 2012, Andreas Fagschlunger. All rights reserved.
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

import org.junit.Assert;
import org.junit.Test;

public class HexTest {

	@Test
	public final void testEncode() {
		for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++) {
			final String expected = String.format("%02X",
					Integer.valueOf(i & 0xFF));
			final String actual = Hex.encode(new byte[] { (byte) i });
			Assert.assertEquals(expected, actual);
		}
	}

	@Test
	public final void testDecodeOneByte() {
		for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++) {
			final byte[] expecteds = new byte[] { (byte) i };
			final String hexString = String.format("%02x",
					Integer.valueOf(i & 0xFF));
			Assert.assertArrayEquals(expecteds,
					Hex.decode(hexString.toLowerCase()));
			Assert.assertArrayEquals(expecteds,
					Hex.decode(hexString.toUpperCase()));
		}
	}

	@Test
	public final void testDecodeMoreBytes() {
		for (int i = Byte.MIN_VALUE, j = Byte.MAX_VALUE; i <= Byte.MAX_VALUE; i++, j--) {
			final byte[] expecteds = new byte[] { (byte) i, (byte) j };
			String hexString = String.format("%02x", i & 0xFF);
			hexString += String.format("%02x", j & 0xFF);
			Assert.assertArrayEquals(expecteds,
					Hex.decode(hexString.toLowerCase()));
			Assert.assertArrayEquals(expecteds,
					Hex.decode(hexString.toUpperCase()));
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testDecodeOddString() {
		Hex.decode("123");
	}

	@Test
	public final void testDecodeEmptyString() {
		final byte[] expecteds = new byte[0];
		final byte[] actuals = Hex.decode("");
		Assert.assertArrayEquals(expecteds, actuals);
	}

	@Test(expected = NullPointerException.class)
	public final void testDecodeNull() {
		Hex.decode(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testDecodeWithIllegalCharacter() {
		Hex.decode("12G3");
	}
}
