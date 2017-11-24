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

public class NoteTypeList3 extends Struct {

	protected final USHORT numOfNoteTypes = new USHORT();
	protected final Pointer noteTypes = new Pointer();

	protected NoteTypeList3() {
		add(numOfNoteTypes);
		add(noteTypes);
	}

	public NoteTypeList3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public NoteTypeList3(NoteTypeList3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(NoteTypeList3 copy) {
		numOfNoteTypes.set(copy.getNumOfNoteTypes());
		noteTypes.pointTo(new NoteType3Array(copy.getNoteTypes()));
	}

	public int getNumOfNoteTypes() {
		return numOfNoteTypes.get();
	}

	public NoteType3[] getNoteTypes() {
		return new NoteType3Array(noteTypes, getNumOfNoteTypes()).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getNumOfNoteTypes()).append(getNoteTypes()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NoteTypeList3) {
			NoteTypeList3 noteTypeList3 = (NoteTypeList3) obj;
			return new EqualsBuilder().append(getNumOfNoteTypes(), noteTypeList3.getNumOfNoteTypes()).append(getNoteTypes(), noteTypeList3.getNoteTypes()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("numOfNoteTypes", getNumOfNoteTypes()).append("noteTypes", getNoteTypes()).toString();
	}
}
