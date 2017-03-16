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

package at.o2xfs.xfs.cim.v3_30;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.cim.Level;
import at.o2xfs.xfs.win32.XfsWord;

public class GetAllItemsInfo3_30 extends Struct {

	public static class Builder {

		private final Level level;

		public Builder(Level level) {
			this.level = level;
		}

		public GetAllItemsInfo3_30 build() {
			return new GetAllItemsInfo3_30(this);
		}
	}

	protected final XfsWord<Level> level = new XfsWord<>(Level.class);

	protected GetAllItemsInfo3_30() {
		add(level);
	}

	protected GetAllItemsInfo3_30(Builder builder) {
		this();
		allocate();
		level.set(builder.level);
	}

	public GetAllItemsInfo3_30(Pointer p) {
		this();
		assignBuffer(p);
	}

	public GetAllItemsInfo3_30(GetAllItemsInfo3_30 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(GetAllItemsInfo3_30 copy) {
		level.set(copy.getLevel());
	}

	public Level getLevel() {
		return level.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getLevel()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GetAllItemsInfo3_30) {
			GetAllItemsInfo3_30 getAllItemsInfo3_30 = (GetAllItemsInfo3_30) obj;
			return new EqualsBuilder().append(getLevel(), getAllItemsInfo3_30.getLevel()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("level", getLevel()).toString();
	}
}