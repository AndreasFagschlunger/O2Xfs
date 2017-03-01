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

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Base class for <code>union</code> types.
 *
 * @author Andreas Fagschlunger
 */
public class Union extends Type {

	public static class Field {

		private String name = null;

		private Type type = null;

		public Field(final String name, final Type type) {
			if (name == null) {
				throw new IllegalArgumentException("name must not be null");
			} else if (type == null) {
				throw new IllegalArgumentException("type must not be null");
			}
			this.name = name;
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public Type getType() {
			return type;
		}

		@Override
		public String toString() {
			return name + "=" + type;
		}
	}

	private Field[] fields = null;

	private int size = 0;

	public Union(final Field[] fields) {
		if (fields == null) {
			throw new IllegalArgumentException("fields must not be null");
		} else if (fields.length < 1) {
			throw new IllegalArgumentException("fields must not be empty");
		}
		for (Field field : fields) {
			size = Math.max(size, field.getType().getSize());
		}
		this.fields = fields;
	}

	public <T extends Type> T get(final String name, final Class<T> type) {
		for (Field field : fields) {
			if (field.getName().equals(name)) {
				return type.cast(field.getType());
			}
		}
		return null;
	}

	@Override
	protected void assignBuffer(Buffer buffer) {
		super.assignBuffer(buffer);
		for (Field each : fields) {
			each.type.assignBuffer(buffer);
		}
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("size", size)
				.append("fields", fields).toString();
	}
}