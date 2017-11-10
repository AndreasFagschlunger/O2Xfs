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

package at.o2xfs.win32;

/**
 * A 16-bit integer. The range is –32768 through 32767 decimal.
 *
 * @see <a href=
 *      "http://msdn.microsoft.com/en-us/library/aa383751%28v=vs.85%29.aspx">http://msdn.microsoft.com/en-us/library/aa383751%28v=vs.85%29.aspx</a>
 *
 * @author Andreas Fagschlunger
 */
public class SHORT extends NumberType<Short> {

	public SHORT() {
		super(2);
	}

	public SHORT(short value) {
		this();
		allocate();
		set(value);
	}

	public void set(short value) {
		put(Bits.toByteArray(value));
	}

	@Override
	public int intValue() {
		return shortValue();
	}

	@Override
	public long longValue() {
		return shortValue();
	}

	@Override
	public byte byteValue() {
		return (byte) shortValue();
	}

	@Override
	public short shortValue() {
		return Bits.getShort(getBytes());
	}

	@Override
	public void set(Short value) {
		set(value.shortValue());
	}

	@Override
	public Short get() {
		return Short.valueOf(shortValue());
	}
}