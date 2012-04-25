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

import org.apache.commons.codec.binary.Hex;

/**
 * A Pointer type.
 * 
 * @author Andreas Fagschlunger
 */
public class Pointer extends Type {

	private final static int SIZE = 4;

	/**
	 * NULL Pointer
	 */
	public final static Pointer NULL = new Pointer(true);

	/**
	 * Reference to the Type this Pointer points to due to garbage collection.
	 */
	private Type reference = null;

	public Pointer() {
		super();
	}

	public Pointer(final Type type) {
		this(true);
		pointTo(type);
	}

	private Pointer(boolean allocate) {
		super();
		allocate();
	}

	@Override
	public int getSize() {
		return SIZE;
	}

	/**
	 * @param capacity
	 *            The buffer's capacity, in bytes
	 * @return ByteBuffer for the memory this Pointer points to
	 * @throws NullPointerException
	 *             if this Pointer points to NULL
	 */
	public ByteBuffer get(final int capacity) throws NullPointerException {
		if (NULL.equals(this)) {
			throw new NullPointerException("Pointer points to NULL");
		}
		return get(buffer().getInt(getOffset()), capacity);
	}

	/**
	 * Lets this Pointer point to the specified {@link Type}.
	 * 
	 * @param type
	 *            the Type this Pointer should point to
	 */
	public void pointTo(final Type type) {
		this.reference = type;
		long address = type.address();
		for (int i = 0; i < SIZE; i++) {
			buffer().put(getOffset() + i, (byte) (address & 0xFF));
			address >>>= 8;
		}
	}

	private native ByteBuffer get(int address, int capacity);

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Pointer) {
			final Pointer p = (Pointer) obj;
			if (reference != null && p.reference != null) {
				return reference.equals(p.reference);
			} else {
				return super.equals(obj);
			}
		}
		return false;
	}

	@Override
	public String toString() {
		byte[] value = new byte[SIZE];
		for (int i = 0; i < value.length; i++) {
			value[i] = buffer().get(getOffset() + i);
		}
		return Long.toHexString(address()) + ","
				+ new String(Hex.encodeHex(value));
	}
}
