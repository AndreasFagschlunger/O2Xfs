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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Base class for all <code>struct</code>-Types.
 * 
 * @author Andreas Fagschlunger
 */
public class Struct extends Type {

	public class Appender {

		private final Struct struct;

		private Appender(Struct struct) {
			this.struct = struct;
		}

		public Appender add(Type type) {
			struct.add(type);
			return this;
		}
	}

	private final List<Type> types;

	protected Struct() {
		types = new ArrayList<Type>();
	}

	protected Appender add(Type type) {
		types.add(type);
		return new Appender(this);
	}

	@Override
	protected void assignBuffer(Buffer buffer) {
		super.assignBuffer(buffer);
		int index = 0;
		for (Type type : types) {
			type.assignBuffer(buffer.subBuffer(index, type.getSize()));
			index += type.getSize();
		}
	}

	@Override
	public int getSize() {
		int result = 0;
		for (Type type : types) {
			result += type.getSize();
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Struct) {
			final Struct s = (Struct) obj;
			return types.equals(s.types);
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString())
				.append("types", types).toString();
	}
}
