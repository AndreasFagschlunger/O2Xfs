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

package at.o2xfs.win32.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import at.o2xfs.win32.Buffer;
import at.o2xfs.win32.BufferOverflowException;

final class Win32Buffer extends Buffer {

	static {
		System.loadLibrary("at.o2xfs.win32");
	}

	private static final byte[] NULL = new byte[4];

	private final Set<Buffer> subBuffers = new HashSet<>();

	public Win32Buffer(int size) {
		super(size);
	}

	public Win32Buffer(byte[] address, int size) {
		super(address, size);
	}

	@Override
	protected byte[] allocate() {
		return allocate0(getSize());
	}

	private native byte[] allocate0(int size);

	@Override
	public byte[] get() {
		byte[] address = getAddress();
		if (Arrays.equals(NULL, address)) {
			return null;
		}
		return get0(getAddress(), getSize());
	}

	private native byte[] get0(byte[] address, int length);

	@Override
	public void put(byte[] src) {
		if (src.length > getSize()) {
			throw new BufferOverflowException();
		} else if (getAddress() == NULL) {
			throw new NullPointerException("address is 0");
		}
		put0(getAddress(), src);
	}

	private native void put0(byte[] address, byte[] src);

	@Override
	public Buffer subBuffer(int index, int size) {
		if (index < 0) {
			throw new IllegalArgumentException("index must not be negative: " + index);
		} else if (size < 0) {
			throw new IllegalArgumentException("size must not be negative: " + size);
		} else if (index + size > getSize()) {
			throw new BufferOverflowException();
		}
		Buffer result = subBuffer0(getAddress(), index, size);
		subBuffers.add(result);
		return result;
	}

	private native Buffer subBuffer0(byte[] address, int index, int size);

	@Override
	protected void _free() {
		free0(getAddress());
	}

	private native void free0(byte[] address);
}