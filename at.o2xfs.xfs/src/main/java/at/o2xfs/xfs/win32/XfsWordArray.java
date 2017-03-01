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

import at.o2xfs.win32.Array;
import at.o2xfs.win32.ValueType;
import at.o2xfs.xfs.XfsConstant;

public class XfsWordArray<T extends Enum<T> & XfsConstant>
		extends Array<XfsWord<T>>
		implements ValueType<T[]> {

	private final Class<T> type;

	public XfsWordArray(Class<T> type, int length) {
		super(new XfsWord[length]);
		for (int i = 0; i < array.length; i++) {
			array[i] = new XfsWord<>(type);
		}
		this.type = type;
	}

	public void set(XfsWordArray<T> values) {
		set(values.get());
	}

	@Override
	public T[] get() {
		T[] result = (T[]) java.lang.reflect.Array.newInstance(type, length);
		for (int i = 0; i < length; i++) {
			result[i] = get(i).get();
		}
		return result;
	}

	@Override
	public void set(T[] value) {
		for (int i = 0; i < value.length; i++) {
			get(i).set(value[i]);
		}
	}
}