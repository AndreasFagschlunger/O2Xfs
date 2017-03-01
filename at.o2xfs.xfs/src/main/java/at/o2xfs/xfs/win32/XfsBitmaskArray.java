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
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import at.o2xfs.win32.DWORD;
import at.o2xfs.win32.DWORDArray;
import at.o2xfs.win32.ValueType;
import at.o2xfs.xfs.XfsConstant;

public class XfsBitmaskArray<T extends Enum<T> & XfsConstant> extends DWORDArray implements ValueType<List<Set<T>>> {

	private final Class<T> type;

	public XfsBitmaskArray(int length, Class<T> type) {
		super(length);
		this.type = type;
	}

	@Override
	public void set(List<Set<T>> value) {
		for (int i = 0; i < array.length; i++) {
			long l = 0L;
			for (T each : value.get(i)) {
				l |= each.getValue();
			}
			array[i].set(l);
		}
	}

	@Override
	public List<Set<T>> get() {
		List<Set<T>> result = new ArrayList<>();
		for (DWORD each : array) {
			result.add(get(each.longValue()));
		}
		return result;
	}

	private Set<T> get(long value) {
		EnumSet<T> result = EnumSet.noneOf(type);
		for (T each : type.getEnumConstants()) {
			if (each.getValue() == 0L) {
				continue;
			}
			if ((value & each.getValue()) == each.getValue()) {
				result.add(each);
			}
		}
		return result;
	}
}