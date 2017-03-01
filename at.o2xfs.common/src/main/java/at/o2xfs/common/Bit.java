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

public final class Bit {

	/**
	 * Bitmask for the first bit
	 */
	public static final int B1 = 1;

	/**
	 * Bitmask for the second bit
	 */
	public static final int B2 = 2;

	/**
	 * Bitmask for the third bit
	 */
	public static final int B3 = 4;

	/**
	 * Bitmask for the forth bit
	 */
	public static final int B4 = 8;

	/**
	 * Bitmask for the fifth bit
	 */
	public static final int B5 = 16;

	/**
	 * Bitmask for the sixth bit
	 */
	public static final int B6 = 32;

	/**
	 * Bitmask for the seventh bit
	 */
	public static final int B7 = 64;

	/**
	 * Bitmask for the eighth bit
	 */
	public static final int B8 = 128;

	private Bit() {
		throw new AssertionError();
	}

	/**
	 * Checks if the specified byte matches the specified mask.
	 *
	 * @param b
	 *            the <code>byte</code> to check
	 * @param mask
	 *            the mask to which <code>b</code> is matched
	 * @return true if <code>b</code> matches the given mask
	 */
	public static final boolean isSet(byte b, int mask) {
		return (b & mask) == mask;
	}
}