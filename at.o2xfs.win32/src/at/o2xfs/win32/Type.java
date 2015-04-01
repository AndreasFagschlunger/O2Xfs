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

import java.util.Arrays;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * This class is the base class for all native types.
 *
 * @author Andreas Fagschlunger
 */
public abstract class Type {

	private Buffer buffer;

	protected final void assignBuffer(Pointer p) {
		assignBuffer(p.buffer(getSize()));
	}

	protected void assignBuffer(Buffer aBuffer) {
		buffer = aBuffer;
	}

	protected final byte[] getBytes() {
		if (buffer == null) {
			throw new IllegalStateException("No buffer");
		}
		return buffer.get();
	}

	protected final void put(byte[] src) {
		if (buffer == null) {
			throw new IllegalStateException("No buffer");
		}
		buffer.put(src);
	}

	public final void allocate() {
		if (buffer != null) {
			throw new IllegalStateException("Buffer already exists");
		}
		assignBuffer(BufferFactory.getInstance().createBuffer(getSize()));
	}

	public abstract int getSize();

	protected final Buffer getBuffer() {
		return buffer;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getBytes()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Type) {
			return Arrays.equals(getBytes(), ((Type) obj).getBytes());
		}
		return false;
	}
}