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

package at.o2xfs.win32;

/**
 * A <code>UINT8</code> 8-bit unsigned integer.
 * 
 * @author Andreas Fagschlunger
 */
public class UINT8 extends Type implements IntegerType {

	public static final int MIN_VALUE = 0;

	public static final int MAX_VALUE = 255;

	public UINT8() {
		super();
	}

	public UINT8(final int value) {
		allocate();
		putInt(value);
	}

	@Override
	protected int getSize() {
		return 1;
	}

	public void putInt(final int value) {
		if (value < UINT8.MIN_VALUE || value > UINT8.MAX_VALUE) {
			throw new IllegalArgumentException("Illegal value: " + value);
		}
		buffer().put(getOffset(), (byte) value);
	}

	@Override
	public int intValue() {
		return buffer().get(getOffset()) & 0xFF;
	}

	@Override
	public long longValue() {
		return intValue();
	}

	@Override
	public String toString() {
		return Integer.toString(intValue());
	}
}
