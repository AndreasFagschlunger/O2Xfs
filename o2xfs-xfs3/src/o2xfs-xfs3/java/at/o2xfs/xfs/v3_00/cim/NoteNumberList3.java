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

public class NoteNumberList3 extends Struct {

	public static class Builder {

		private final NoteNumber3[] noteNumbers;

		public Builder(NoteNumber3[] noteNumbers) {
			this.noteNumbers = noteNumbers;
		}

		public NoteNumberList3 build() {
			return new NoteNumberList3(this);
		}
	}

	protected final USHORT numOfNoteNumbers = new USHORT();
	protected final Pointer noteNumber = new Pointer();

	protected NoteNumberList3() {
		add(numOfNoteNumbers);
		add(noteNumber);
	}

	protected NoteNumberList3(Builder builder) {
		this();
		allocate();
		numOfNoteNumbers.set(builder.noteNumbers.length);
		noteNumber.pointTo(new NoteNumber3Array(builder.noteNumbers));
	}

	public NoteNumberList3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public NoteNumberList3(NoteNumberList3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(NoteNumberList3 copy) {
		numOfNoteNumbers.set(copy.getNumOfNoteNumbers());
		noteNumber.pointTo(new NoteNumber3Array(copy.getNoteNumber()));
	}

	public int getNumOfNoteNumbers() {
		return numOfNoteNumbers.get();
	}

	public NoteNumber3[] getNoteNumber() {
		return new NoteNumber3Array(noteNumber, getNumOfNoteNumbers()).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getNumOfNoteNumbers()).append(getNoteNumber()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NoteNumberList3) {
			NoteNumberList3 noteNumberList3 = (NoteNumberList3) obj;
			return new EqualsBuilder()
					.append(getNumOfNoteNumbers(), noteNumberList3.getNumOfNoteNumbers())
					.append(getNoteNumber(), noteNumberList3.getNoteNumber())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("numOfNoteNumbers", getNumOfNoteNumbers())
				.append("noteNumber", getNoteNumber())
				.toString();
	}
}
