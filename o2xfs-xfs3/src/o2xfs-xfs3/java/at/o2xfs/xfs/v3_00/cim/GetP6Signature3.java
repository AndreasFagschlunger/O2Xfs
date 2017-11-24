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

package at.o2xfs.xfs.v3_00.cim;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cim.Level;
import at.o2xfs.xfs.win32.XfsWord;

public class GetP6Signature3 extends Struct {

	public static class Builder {

		private final Level level;
		private final int index;

		public Builder(Level level, int index) {
			this.level = level;
			this.index = index;
		}

		public GetP6Signature3 build() {
			return new GetP6Signature3(this);
		}
	}

	protected final XfsWord<Level> level = new XfsWord<>(Level.class);
	protected final USHORT index = new USHORT();

	protected GetP6Signature3() {
		add(level);
		add(index);
	}

	protected GetP6Signature3(Builder builder) {
		this();
		allocate();
		level.set(builder.level);
		index.set(builder.index);
		;
	}

	public GetP6Signature3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public GetP6Signature3(GetP6Signature3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(GetP6Signature3 copy) {
		level.set(copy.getLevel());
		index.set(copy.getIndex());
	}

	public Level getLevel() {
		return level.get();
	}

	public int getIndex() {
		return index.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getLevel()).append(getIndex()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GetP6Signature3) {
			GetP6Signature3 getP6Signature3 = (GetP6Signature3) obj;
			return new EqualsBuilder()
					.append(getLevel(), getP6Signature3.getLevel())
					.append(getIndex(), getP6Signature3.getIndex())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("level", getLevel()).append("index", getIndex()).toString();
	}
}
