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
 * A Boolean type.
 *
 * @see <a href=
 *      "http://msdn.microsoft.com/en-us/library/aa383751%28v=vs.85%29.aspx">http://msdn.microsoft.com/en-us/library/aa383751%28v=vs.85%29.aspx</a>
 *
 * @author Andreas Fagschlunger
 */
public class BOOL extends NumberType<Boolean> {

	private final static int TRUE = 1;

	private final static int FALSE = 0;

	public BOOL() {
		super(4);
	}

	public BOOL(boolean value) {
		this();
		allocate();
		set(value);
	}

	public void set(BOOL value) {
		set(value.booleanValue());
	}

	public void set(boolean b) {
		put(Bits.toByteArray(b ? TRUE : FALSE));
	}

	@Override
	public int intValue() {
		return Bits.getInt(getBytes());
	}

	@Override
	public long longValue() {
		return intValue();
	}

	@Override
	public void set(Boolean value) {
		set(value.booleanValue());
	}

	@Override
	public Boolean get() {
		return Boolean.valueOf(booleanValue());
	}

	public boolean booleanValue() {
		return intValue() == TRUE;
	}

	@Override
	public String toString() {
		return Boolean.toString(booleanValue());
	}
}