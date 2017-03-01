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

package at.o2xfs.xfs.win32;

import java.util.ArrayList;
import java.util.List;

import at.o2xfs.win32.Buffer;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Type;
import at.o2xfs.win32.ValueType;

public abstract class XfsZPointerArray<T extends Type> extends Type implements ValueType<T[]> {

	protected final Pointer[] pointers;

	public XfsZPointerArray(T[] array) {
		pointers = new Pointer[array.length];
		allocate();
		int size = Pointer.NULL.getSize();
		for (int i = 0; i < pointers.length; i++) {
			pointers[i] = new Pointer(getBuffer().subBuffer(i * size, size));
			pointers[i].pointTo(array[i]);
		}
	}

	public XfsZPointerArray(Pointer aPointer) {
		int offset = 0;
		Buffer buffer = null;
		List<Pointer> pointers = new ArrayList<>();
		while (true) {
			buffer = aPointer.buffer(offset + aPointer.getSize());
			Pointer p = new Pointer(buffer.subBuffer(offset, aPointer.getSize()));
			if (Pointer.NULL.equals(p)) {
				break;
			}
			pointers.add(p);
			offset += p.getSize();
		}
		this.pointers = pointers.toArray(new Pointer[pointers.size()]);
		assignBuffer(buffer);
	}

	@Override
	public int getSize() {
		return Pointer.NULL.getSize() + (Pointer.NULL.getSize() * pointers.length);
	}

	public abstract T copy(T copy);

	@Override
	public void set(T[] value) {
		if (pointers.length != value.length) {
			throw new IllegalArgumentException("Illegal length: " + value.length);
		}
		for (int i = 0; i < pointers.length; i++) {
			pointers[i].pointTo(copy(value[i]));
		}
	}
}