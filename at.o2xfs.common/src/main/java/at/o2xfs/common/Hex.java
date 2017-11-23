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

public final class Hex {

	private static final char[] DIGITS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
			'D', 'E', 'F' };

	private Hex() {
		throw new AssertionError();
	}

	public static final String encode(int i) {
		char[] cbuf = new char[8];
		int charPos = cbuf.length - 1;
		do {
			cbuf[charPos--] = DIGITS[i & 0xF];
			i >>>= 4;
			cbuf[charPos--] = DIGITS[i & 0xF];
			i >>>= 4;
		} while (i != 0);
		return new String(cbuf, charPos + 1, cbuf.length - charPos - 1);
	}

	public static final String encode(final byte b) {
		return encode(new byte[] { b });
	}

	public static final String encode(final byte[] b) {
		final char[] cbuf = new char[b.length << 1];
		for (int i = 0, j = 0; i < b.length; i++) {
			cbuf[j++] = DIGITS[(b[i] & 0xF0) >>> 4];
			cbuf[j++] = DIGITS[(b[i] & 0xF)];
		}
		return new String(cbuf);
	}

	public static final byte[] decode(final String hex) {
		if (hex.length() > 0 && hex.length() % 2 != 0) {
			throw new IllegalArgumentException("Odd number of characters: " + hex);
		}
		final byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0, j = 0; i < bytes.length; i++) {
			int digit = toDigit(hex.charAt(j++));
			bytes[i] = (byte) (digit << 4);
			digit = toDigit(hex.charAt(j++));
			bytes[i] |= digit;
		}
		return bytes;
	}

	private static final int toDigit(char c) {
		if (c >= '0' && c <= '9') {
			return c - '0';
		} else if (c >= 'A' && c <= 'F') {
			return c - 55;
		} else if (c >= 'a' && c <= 'f') {
			return c - 87;
		}
		throw new IllegalArgumentException("Illegal character: " + c);
	}
}