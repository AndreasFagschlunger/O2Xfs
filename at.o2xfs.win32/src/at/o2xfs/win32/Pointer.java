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

import at.o2xfs.common.Hex;

/**
 * A Pointer type.
 * 
 * @author Andreas Fagschlunger
 */
public class Pointer extends BaseType {

	/**
	 * NULL Pointer
	 */
	public final static Pointer NULL = new Pointer(true);

	/**
	 * Reference to the Type this Pointer points to due to garbage collection.
	 */
	private Type reference = null;

	public Pointer() {
		super(1 << 2);
	}

	public Pointer(Buffer buffer) {
		this();
		assignBuffer(buffer);
	}

	public Pointer(final Type type) {
		this(true);
		pointTo(type);
	}

	private Pointer(boolean allocate) {
		this();
		if (allocate) {
			allocate();
		}
	}

	/**
	 * @param size
	 *            The buffer's capacity, in bytes
	 * @return Buffer for the memory this Pointer points to
	 * @throws NullPointerException
	 *             if this Pointer points to NULL
	 */
	public Buffer buffer(final int size) throws NullPointerException {
		if (Pointer.NULL.equals(this)) {
			throw new NullPointerException("Pointer points to NULL");
		}
		return BufferFactory.getInstance().createBuffer(get(), size);
	}

	/**
	 * Lets this Pointer point to the specified {@link Type}.
	 * 
	 * @param reference
	 *            the Type this Pointer should point to
	 */
	public void pointTo(final Type reference) {
		if (this == NULL) {
			throw new NullPointerException("Could not reassign NULL");
		}
		this.reference = reference;
		put(reference.getBuffer().getAddress());
	}

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
		return "Address: " + Hex.encode(getBuffer().getAddress()) + ", Value: "
				+ Hex.encode(get());
	}
}
