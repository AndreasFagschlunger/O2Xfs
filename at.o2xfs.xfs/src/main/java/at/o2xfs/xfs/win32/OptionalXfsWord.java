/*
 * Copyright (c) 2018, Andreas Fagschlunger. All rights reserved.
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

import java.util.Optional;

import at.o2xfs.win32.BaseType;
import at.o2xfs.win32.Bits;
import at.o2xfs.win32.USHORT;
import at.o2xfs.win32.ValueType;
import at.o2xfs.xfs.XfsConstant;
import at.o2xfs.xfs.util.XfsConstants;

public final class OptionalXfsWord<T extends Enum<T> & XfsConstant> extends BaseType implements ValueType<Optional<T>> {

	private final Class<T> enumType;

	public OptionalXfsWord(Class<T> enumType) {
		super(USHORT.SIZE);
		this.enumType = enumType;
	}

	@Override
	public void set(Optional<T> value) {
		long val = 0L;
		if (value.isPresent()) {
			val = value.get().getValue();
		}
		put(Bits.toByteArray((short) val));
	}

	@Override
	public Optional<T> get() {
		Optional<T> result = Optional.empty();
		long value = longValue();
		if (value != 0) {
			result = Optional.of(XfsConstants.valueOf(value, enumType));
		}
		return result;
	}

	private long longValue() {
		return Bits.getShort(getBytes()) & USHORT.MAX_VALUE;
	}
}
