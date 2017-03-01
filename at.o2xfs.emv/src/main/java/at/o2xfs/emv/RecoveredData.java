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

package at.o2xfs.emv;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.o2xfs.common.Bytes;

public class RecoveredData<E extends Enum<E>> {

	private static class Field<E extends Enum<E>> {

		private final E key;

		private final int length;

		public Field(E key, int length) {
			this.key = key;
			this.length = length;
		}
	}

	public static class Builder<E extends Enum<E>> {

		private final List<Field<E>> fields;

		public Builder() {
			fields = new ArrayList<RecoveredData.Field<E>>();
		}

		public void addField(E name, int length) {
			final Field<E> field = new Field<E>(name, length);
			fields.add(field);
		}
	}

	private final Map<E, byte[]> dataMap;

	public RecoveredData(Builder<E> builder, byte[] data) {
		dataMap = new HashMap<E, byte[]>(builder.fields.size());
		parse(builder.fields, data);
	}

	private void parse(List<Field<E>> fields, byte[] data) {
		int offset = 0;
		for (Field<E> field : fields) {
			byte[] value = new byte[field.length];
			System.arraycopy(data, offset, value, 0, value.length);
			offset += value.length;
			dataMap.put(field.key, value);
		}
	}

	public byte[] concatenate(E from, E to) {
		EnumSet<E> elements = EnumSet.range(from, to);
		int length = calcLength(elements);
		byte[] result = new byte[length];
		int offset = 0;
		for (E element : elements) {
			byte[] data = get(element);
			System.arraycopy(data, 0, result, offset, data.length);
			offset += data.length;
		}
		return result;
	}

	private int calcLength(EnumSet<E> elements) {
		int length = 0;
		for (E element : elements) {
			length += get(element).length;
		}
		return length;
	}

	public byte[] get(E key) {
		return Bytes.copy(dataMap.get(key));
	}
}