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
 * A 32-bit signed integer. The range is -2147483648 through 2147483647 decimal.
 *
 * {@link "http://msdn.microsoft.com/en-us/library/aa383751%28v=vs.85%29.aspx"}
 *
 * @author Andreas Fagschlunger
 */
public class INT
		extends NumberType<Integer> {

	public INT() {
		super(4);
	}

	public INT(int value) {
		this();
		allocate();
		set(value);
	}

	public void set(INT value) {
		set(value.intValue());
	}

	public void set(int value) {
		put(BitConverter.getBytes(getSize(), value));
	}

	@Override
	public void set(Integer value) {
		set(value.intValue());
	}

	@Override
	public Integer get() {
		return Integer.valueOf(intValue());
	}

	@Override
	public String toString() {
		return Integer.toString(intValue());
	}
}