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

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Base class for all <code>struct</code>-Types.
 * 
 * @author Andreas Fagschlunger
 */
public abstract class Structure extends Type {

	private List<Type> fields = new ArrayList<Type>();

	protected void add(final Type field) {
		fields.add(field);
	}

	@Override
	public int getSize() {
		int size = 0;
		for (Type field : fields) {
			size += field.getSize();
		}
		return size;
	}

	@Override
	public void allocate() {
		super.allocate();
		int offset = 0;
		for (Type field : fields) {
			field.useBuffer(buffer(), offset);
			offset += field.getSize();
		}
	}

	@Override
	public void free() {
		super.free();
		for (final Type field : fields) {
			field.free();
		}
	}

	@Override
	public void useBuffer(ByteBuffer buffer, int offset) {
		super.useBuffer(buffer, offset);
		for (Type field : fields) {
			field.useBuffer(buffer, offset);
			offset += field.getSize();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Structure) {
			final Structure s = (Structure) obj;
			return fields.equals(s.fields);
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString())
				.append("fields", fields).toString();
	}
}
