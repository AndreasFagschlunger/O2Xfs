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

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cim.Level;
import at.o2xfs.xfs.win32.XfsWord;

public class P6Info3 extends Struct {

	protected final XfsWord<Level> level = new XfsWord<>(Level.class);
	protected final Pointer noteNumberList = new Pointer();
	protected final USHORT numOfSignatures = new USHORT();

	protected P6Info3() {
		add(level);
		add(noteNumberList);
		add(numOfSignatures);
	}

	public P6Info3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public P6Info3(P6Info3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(P6Info3 copy) {
		level.set(copy.getLevel());
		Optional<NoteNumberList3> noteNumberList = copy.getNoteNumberList();
		if (noteNumberList.isPresent()) {
			this.noteNumberList.pointTo(new NoteNumberList3(copy.getNoteNumberList().get()));
		}
		numOfSignatures.set(copy.getNumOfSignatures());
	}

	public Level getLevel() {
		return level.get();
	}

	public Optional<NoteNumberList3> getNoteNumberList() {
		Optional<NoteNumberList3> result = Optional.empty();
		if (!Pointer.NULL.equals(noteNumberList)) {
			result = Optional.of(new NoteNumberList3(noteNumberList));
		}
		return result;
	}

	public int getNumOfSignatures() {
		return numOfSignatures.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getLevel()).append(getNoteNumberList()).append(getNumOfSignatures()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof P6Info3) {
			P6Info3 p6Info3 = (P6Info3) obj;
			return new EqualsBuilder().append(getLevel(), p6Info3.getLevel()).append(getNoteNumberList(), p6Info3.getNoteNumberList())
					.append(getNumOfSignatures(), p6Info3.getNumOfSignatures()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("level", getLevel()).append("noteNumberList", getNoteNumberList()).append("numOfSignatures", getNumOfSignatures()).toString();
	}
}
