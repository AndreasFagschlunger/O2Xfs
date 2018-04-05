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

package at.o2xfs.xfs.v3_10.ptr;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.ptr.DefinitionType;
import at.o2xfs.xfs.win32.XfsDWord;

public class DefinitionLoaded310 extends Struct {

	protected final LPSTR definitionName = new LPSTR();
	protected final XfsDWord<DefinitionType> definitionType = new XfsDWord<>(DefinitionType.class);

	protected DefinitionLoaded310() {
		add(definitionName);
		add(definitionType);
	}

	public DefinitionLoaded310(Pointer p) {
		this();
		assignBuffer(p);
	}

	public DefinitionLoaded310(DefinitionLoaded310 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(DefinitionLoaded310 copy) {
		definitionName.set(copy.getDefinitionName());
		definitionType.set(copy.getDefinitionType());
	}

	public String getDefinitionName() {
		return definitionName.get();
	}

	public DefinitionType getDefinitionType() {
		return definitionType.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getDefinitionName()).append(getDefinitionType()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DefinitionLoaded310) {
			DefinitionLoaded310 definitionLoaded310 = (DefinitionLoaded310) obj;
			return new EqualsBuilder()
					.append(getDefinitionName(), definitionLoaded310.getDefinitionName())
					.append(getDefinitionType(), definitionLoaded310.getDefinitionType())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("definitionName", getDefinitionName())
				.append("definitionType", getDefinitionType())
				.toString();
	}
}
