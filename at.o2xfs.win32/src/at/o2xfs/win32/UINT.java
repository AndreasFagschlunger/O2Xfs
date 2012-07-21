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

import java.nio.ByteBuffer;

/**
 * An unsigned INT. The range is 0 through 4294967295 decimal.
 * 
 * {@link http://msdn.microsoft.com/en-us/library/aa383751%28v=vs.85%29.aspx}
 * 
 * @author Andreas Fagschlunger
 */
public class UINT extends Type implements IntegerType {

	public static final long MIN_VALUE = 0;

	public static final long MAX_VALUE = 4294967295L;

	private static final int SIZE = 4;

	public UINT() {
		super();
	}

	public UINT(final ByteBuffer buffer, final int offset) {
		super(buffer, offset);
	}

	public UINT(final ByteBuffer buffer) {
		super(buffer);
	}

	@Override
	public int getSize() {
		return SIZE;
	}

	public void put(final UINT value) {
		put(value.longValue());
	}

	public void put(final long value) throws IllegalArgumentException {
		if (value < MIN_VALUE || value > MAX_VALUE) {
			throw new IllegalArgumentException("Illegal value: " + value);
		}
		buffer().putInt(getOffset(), (int) value);
	}

	@Override
	public int intValue() {
		return (int) longValue();
	}

	/**
	 * @return the primitive <code>long</code> value represented by this object.
	 */
	@Override
	public long longValue() {
		return buffer().getInt(getOffset()) & 0xFFFFFFFFL;
	}

	@Override
	public String toString() {
		return Long.toString(longValue());
	}

}
