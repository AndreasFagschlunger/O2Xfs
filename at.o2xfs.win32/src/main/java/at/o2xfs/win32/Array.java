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

import java.util.Arrays;
import java.util.Iterator;

/**
 * Base class for all array types.
 *
 * @param <E>
 *            the type of elements in this array
 *
 * @author Andreas Fagschlunger
 */
public abstract class Array<E extends Type>
		extends Type
		implements Iterable<E> {

	protected final E[] array;

	/**
	 * The length of the array
	 */
	public final int length;

	protected Array(final E[] array) {
		this.array = array;
		this.length = array.length;
	}

	@Override
	protected final void assignBuffer(Buffer buffer) {
		super.assignBuffer(buffer);
		assignBufferToElements(buffer);
	}

	private void assignBufferToElements(Buffer buffer) {
		int index = 0;
		for (E each : array) {
			each.assignBuffer(buffer.subBuffer(index, each.getSize()));
			index += each.getSize();
		}
	}

	@Override
	public final int getSize() {
		int result = 0;
		for (E e : array) {
			result += e.getSize();
		}
		return result;
	}

	/**
	 * @param index
	 *            index of the element to return
	 * @return the element at the specified position in this array
	 */
	public E get(final int index) {
		return array[index];
	}

	@Override
	public Iterator<E> iterator() {
		return Arrays.asList(array).iterator();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Array<?>) {
			final Array<?> a = (Array<?>) obj;
			if (length == a.length) {
				for (int i = 0; i < array.length; i++) {
					if (!array[i].equals(a.array[i])) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return Arrays.toString(array);
	}
}