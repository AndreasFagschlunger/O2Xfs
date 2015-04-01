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

import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;

import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class Buffer {

	private final int size;
	private byte[] address;
	private final boolean allocated;

	protected Buffer(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("size must not be negative: " + size);
		}
		this.size = size;
		address = allocate();
		allocated = true;
	}

	protected Buffer(byte[] address, int size) {
		if (size < 0) {
			throw new IllegalArgumentException("size must not be negative: " + size);
		}
		this.size = size;
		this.address = Bytes.copy(address);
		allocated = false;
	}

	protected final int getSize() {
		return size;
	}

	protected final byte[] getAddress() {
		return Bytes.copy(address);
	}

	public final Pointer getPointer() {
		Pointer result = new Pointer();
		result.allocate();
		result.put(address);
		return result;
	}

	protected abstract byte[] allocate();

	public abstract byte[] get();

	public abstract void put(byte[] src);

	public abstract Buffer subBuffer(int index, int aSize);

	public final void free() {
		if (address != null) {
			if (allocated) {
				_free();
			}
			address = null;
		}
	}

	protected abstract void _free();

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("size", size).append("address", Hex.encode(address)).toString();
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			free();
		} finally {
			super.finalize();
		}
	}
}