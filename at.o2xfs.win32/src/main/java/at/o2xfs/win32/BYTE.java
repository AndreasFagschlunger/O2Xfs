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
 * A byte (8 bits).
 *
 * @see <a href=
 *      "http://msdn.microsoft.com/en-us/library/aa383751%28v=vs.85%29.aspx">http://msdn.microsoft.com/en-us/library/aa383751%28v=vs.85%29.aspx</a>
 *
 * @author Andreas Fagschlunger
 */
public class BYTE extends BaseType implements ValueType<Byte> {

	public BYTE() {
		super(1);
	}

	/**
	 * Set the specified value.
	 *
	 * @param b
	 *            the byte to set
	 */
	public void set(byte b) {
		put(new byte[] { b });
	}

	@Override
	public void set(Byte value) {
		set(value.byteValue());
	}

	@Override
	public Byte get() {
		return Byte.valueOf(byteValue());
	}

	/**
	 * @return the primitive <code>byte</code> value represented by this object.
	 */
	public byte byteValue() {
		return getBytes()[0];
	}
}