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
import java.nio.ByteOrder;
import java.util.Arrays;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * This class is the base class for all native types.
 * 
 * @author Andreas Fagschlunger
 */
public abstract class Type {

	static {
		System.loadLibrary("O2XfsWIN32");
	}

	private long address = 0L;

	private ByteBuffer buffer = null;

	private int offset = 0;

	protected Type() {

	}

	protected Type(final ByteBuffer buffer) {
		this(buffer, 0);
	}

	protected Type(final ByteBuffer buffer, final int offset) {
		useBuffer(buffer, offset);
	}

	public void allocate() {
		if (buffer != null) {
			throw new IllegalStateException("buffer is not null");
		}
		buffer = ByteBuffer.allocateDirect(getSize());
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		address = address(buffer);
	}

	private native long address(ByteBuffer buffer);

	protected int getOffset() {
		return offset;
	}

	/**
	 * @return this Types size in bytes
	 */
	abstract protected int getSize();

	protected void useBuffer(final Pointer p) {
		useBuffer(p.get(getSize()));
	}

	protected final void useBuffer(final ByteBuffer buffer) {
		useBuffer(buffer, 0);
	}

	protected void useBuffer(final ByteBuffer buffer, int offset) {
		if (this.buffer != null) {
			throw new IllegalStateException("buffer is not null");
		} else if (buffer == null) {
			throw new IllegalArgumentException("buffer cannot be null");
		} else if (offset + getSize() > buffer.capacity()) {
			throw new IllegalArgumentException("buffer's capacity is to small");
		}
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		this.buffer = buffer;
		this.offset = offset;
		address = address(buffer);
	}

	public ByteBuffer buffer() {
		if (buffer == null) {
			throw new NullPointerException("buffer is null");
		}
		return buffer;
	}

	protected long address() {
		if (buffer == null || !buffer.isDirect()) {
			throw new IllegalStateException("buffer is not allocated");
		}
		return address;
	}

	private byte[] get() {
		final byte[] data = new byte[getSize()];
		for (int i = 0, j = data.length - 1; j >= 0; j--) {
			data[i++] = buffer().get(offset + j);
		}
		return data;
	}

	public void free() {
		buffer = null;
		address = 0L;
		offset = 0;
	}

	public String dump() {
		return Long.toHexString(address) + ",0x"
				+ new String(Hex.encodeHex(get()));
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(get()).toHashCode();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj != null) {
			if (obj == this) {
				return true;
			}
			if (obj instanceof Type) {
				final Type type = (Type) obj;
				if (buffer != null && type.buffer != null) {
					return Arrays.equals(get(), type.get());
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("address",
				Long.toHexString(address())).toString();
	}
}
