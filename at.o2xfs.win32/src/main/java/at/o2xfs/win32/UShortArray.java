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

/**
 * A array of {@link USHORT}s.
 *
 * @author Andreas Fagschlunger
 */
public class UShortArray extends Array<USHORT> implements ValueType<int[]> {

	public UShortArray(int length) {
		super(new USHORT[length]);
		for (int i = 0; i < length; i++) {
			array[i] = new USHORT();
		}
	}

	public UShortArray(int[] array) {
		this(array.length);
		allocate();
		set(array);
	}

	public UShortArray(final Pointer p, final int length) {
		this(length);
		assignBuffer(p);
	}

	public void set(UShortArray values) {
		put(values.getBytes());
	}

	@Override
	public void set(int[] value) {
		for (int i = 0; i < value.length; i++) {
			get(i).set(value[i]);
		}
	}

	@Override
	public int[] get() {
		int[] result = new int[array.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = get(i).intValue();
		}
		return result;
	}
}