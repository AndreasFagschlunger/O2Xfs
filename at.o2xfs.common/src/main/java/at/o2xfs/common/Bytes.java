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
import java.util.List;

public final class Bytes {

	private Bytes() {
		throw new AssertionError();
	}

	/**
	 * A empty byte array.
	 */
	public static final byte[] EMPTY = new byte[0];

	/**
	 * Concatenates the specified arrays.
	 *
	 * @param arrays
	 *            the arrays to concatenate
	 * @return the concatenation of the specified arrays
	 */
	public static final byte[] concat(byte[]... arrays) {
		int length = 0;
		for (byte[] array : arrays) {
			length += array.length;
		}
		byte[] concat = new byte[length];
		int offset = 0;
		for (byte[] bytes : arrays) {
			System.arraycopy(bytes, 0, concat, offset, bytes.length);
			offset += bytes.length;
		}
		return concat;
	}

	/**
	 * Concatenates the arrays in the specified list.
	 *
	 * @param list
	 *            the list containing the arrays to concatenate
	 * @return the concatenation of the arrays in the specified list
	 */
	public static final byte[] concat(List<byte[]> list) {
		int length = 0;
		for (byte[] bytes : list) {
			length += bytes.length;
		}
		int offset = 0;
		byte[] result = new byte[length];
		for (byte[] bytes : list) {
			System.arraycopy(bytes, 0, result, offset, bytes.length);
			offset += bytes.length;
		}
		return result;
	}

	/**
	 * Copies the specified array.
	 *
	 * @param bytes
	 *            the array to copy
	 * @return the copy of the specified array
	 *
	 * @throws NullPointerException
	 *             if <code>bytes</code> is null
	 */
	public static final byte[] copy(final byte[] bytes) {
		final byte[] copy = new byte[bytes.length];
		System.arraycopy(bytes, 0, copy, 0, bytes.length);
		return copy;
	}

	/**
	 * Converts the specified <code>byte</code> to a <code>unsigned int</code>.
	 *
	 * e.g.
	 *
	 * <code>
	 * <br>byte b = -1; // 0xFF
	 * <br>int i = Bytes.toInt(b); // i = 255
	 * </code>
	 *
	 * @param b
	 *            the <code>byte</code> to convert
	 * @return the specified <code>byte</code> converted to a
	 *         <code>unsigned int</code>
	 */
	public static final int toInt(final byte b) {
		return b & 0xFF;
	}

	/**
	 * Gets the leftmost <code>length</code> bytes
	 *
	 * @param value
	 *            the value to get the leftmost bytes from
	 * @param length
	 *            the length of the required bytes
	 * @return the leftmost bytes
	 */
	public static final byte[] left(byte[] value, int length) {
		byte[] newValue = new byte[length];
		System.arraycopy(value, 0, newValue, 0, length);
		return newValue;
	}

	/**
	 * Gets the rightmost <code>length</code> bytes
	 *
	 * @param value
	 *            the value to get the rightmost bytes from
	 * @param length
	 *            the length of the required bytes
	 * @return the rightmost bytes
	 */
	public static final byte[] right(byte[] value, int length) {
		byte[] rightmost = new byte[length];
		System.arraycopy(value, value.length - length, rightmost, 0, length);
		return rightmost;
	}

	/**
	 * Left pad a byte array with zeros. The byte array is padded to the length
	 * of <code>length</code>.
	 *
	 * @param value
	 *            the byte array to pad out
	 * @param length
	 *            the length to pad to
	 * @return the left padded byte array
	 */
	public static final byte[] leftPad(byte[] value, int length) {
		return leftPad(value, length, 0);
	}

	/**
	 * Left pad a byte array with a specified byte. The byte array is padded to
	 * the length of <code>length</code>.
	 *
	 * @param value
	 *            the byte array to pad out
	 * @param length
	 *            the length to pad to
	 * @param padByte
	 *            the byte to pad with
	 * @return the left padded byte array
	 */
	public static final byte[] leftPad(byte[] value, int length, int padByte) {
		byte[] left = new byte[length];
		Arrays.fill(left, (byte) padByte);
		System.arraycopy(value, 0, left, length - value.length, value.length);
		return left;
	}

	/**
	 * Right pad a byte array with zeros. The byte array is padded to the length
	 * of <code>length</code>.
	 *
	 * @param value
	 *            the byte array to pad out
	 * @param length
	 *            the length to pad to
	 * @return the right padded byte array
	 */
	public static final byte[] rightPad(byte[] value, int length) {
		return rightPad(value, length, 0);
	}

	/**
	 * Right pad a byte array with a specified byte. The byte array is padded to
	 * the length of <code>length</code>.
	 *
	 * @param value
	 *            the byte array to pad out
	 * @param length
	 *            the length to pad to
	 * @param padByte
	 *            the byte to pad with
	 * @return the right padded byte array
	 */
	public static final byte[] rightPad(byte[] value, int length, int padByte) {
		byte[] right = new byte[length];
		Arrays.fill(right, (byte) padByte);
		System.arraycopy(value, 0, right, 0, value.length);
		return right;
	}

	/**
	 * Gets <code>len</code> bytes from the middle of a byte array.
	 *
	 * @param value
	 *            the source array
	 * @param pos
	 *            the position to start from
	 * @param len
	 *            the length of the required bytes
	 * @return
	 */
	public static final byte[] mid(byte[] value, int pos, int len) {
		byte[] result = new byte[len];
		System.arraycopy(value, pos, result, 0, len);
		return result;
	}

	public static final String toBinaryString(byte[] b) {
		return toBinaryString(b, new char[0]);
	}

	public static final String toBinaryString(byte[] b, char separator) {
		return toBinaryString(b, new char[] { separator });
	}

	private static final String toBinaryString(byte[] b, char[] separator) {
		int sepSpace = separator.length * (b.length - 1);
		char[] str = new char[(b.length * 8) + sepSpace];
		int cpos = 0;
		int iMax = b.length - 1;
		for (int i = 0;; i++) {
			for (int mask = Bit.B8; mask >= 1; mask >>>= 1) {
				str[cpos++] = Bit.isSet(b[i], mask) ? '1' : '0';
			}
			if (i == iMax) {
				break;
			}
			System.arraycopy(separator, 0, str, cpos, separator.length);
			cpos += separator.length;
		}
		return new String(str);
	}
}