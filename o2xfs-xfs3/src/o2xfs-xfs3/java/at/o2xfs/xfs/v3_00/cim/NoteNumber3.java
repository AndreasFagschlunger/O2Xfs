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
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.USHORT;

public class NoteNumber3 extends Struct {

	public static class Builder {

		private final int noteId;
		private long count;

		public Builder(int noteId) {
			this.noteId = noteId;
		}

		public Builder count(long count) {
			this.count = count;
			return this;
		}

		public NoteNumber3 build() {
			return new NoteNumber3(this);
		}
	}

	protected final USHORT noteId = new USHORT();
	protected final ULONG count = new ULONG();

	protected NoteNumber3() {
		add(noteId);
		add(count);
	}

	protected NoteNumber3(Builder builder) {
		this();
		allocate();
		noteId.set(builder.noteId);
		count.set(builder.count);
	}

	public NoteNumber3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public NoteNumber3(NoteNumber3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(NoteNumber3 copy) {
		noteId.set(copy.getNoteId());
		count.set(copy.getCount());
	}

	public int getNoteId() {
		return noteId.get();
	}

	public long getCount() {
		return count.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getNoteId()).append(getCount()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NoteNumber3) {
			NoteNumber3 noteNumber3 = (NoteNumber3) obj;
			return new EqualsBuilder()
					.append(getNoteId(), noteNumber3.getNoteId())
					.append(getCount(), noteNumber3.getCount())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("noteId", getNoteId()).append("count", getCount()).toString();
	}
}
