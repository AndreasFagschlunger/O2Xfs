/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.win32;

/**
 * Converts base data types to an array of bytes, and an array of bytes to base
 * data types.
 */
public final class BitConverter {

	private BitConverter() {
		throw new AssertionError();
	}

	public static final byte[] getBytes(NumberType<?> value) {
		return getBytes(value.getSize(), value.longValue());
	}

	public static final byte[] getBytes(int size, long aValue) {
		long value = aValue;
		byte[] result = new byte[size];
		for (int i = 0; i < result.length; i++) {
			result[i] |= value;
			value >>>= 8;
		}
		return result;
	}

	public static final short toShort(byte[] src) {
		return (short) toLong(src);
	}

	public static final int toInt(byte[] src) {
		return (int) toLong(src);
	}

	public static final long toLong(byte[] src) {
		long result = 0L;
		for (int i = src.length - 1; i >= 0; i--) {
			result = (result << 8) | (src[i] & 0xFF);
		}
		return result;
	}
}