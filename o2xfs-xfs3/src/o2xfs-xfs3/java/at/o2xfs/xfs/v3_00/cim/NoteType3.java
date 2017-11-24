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

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.win32.XfsCharArray;

public class NoteType3 extends Struct {

	protected final USHORT noteID = new USHORT();
	protected final XfsCharArray currencyID = new XfsCharArray(3);
	protected final ULONG values = new ULONG();
	protected final USHORT release = new USHORT();
	protected final BOOL configured = new BOOL();

	protected NoteType3() {
		add(noteID);
		add(currencyID);
		add(values);
		add(release);
		add(configured);
	}

	public NoteType3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public NoteType3(NoteType3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(NoteType3 copy) {
		noteID.set(copy.getNoteID());
		currencyID.set(copy.getCurrencyID());
		values.set(copy.getValues());
		release.set(copy.getRelease());
		configured.set(copy.isConfigured());
	}

	public int getNoteID() {
		return noteID.get();
	}

	public char[] getCurrencyID() {
		return currencyID.get();
	}

	public long getValues() {
		return values.get();
	}

	public int getRelease() {
		return release.get();
	}

	public boolean isConfigured() {
		return configured.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getNoteID()).append(getCurrencyID()).append(getValues()).append(getRelease()).append(isConfigured()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NoteType3) {
			NoteType3 noteType3 = (NoteType3) obj;
			return new EqualsBuilder().append(getNoteID(), noteType3.getNoteID()).append(getCurrencyID(), noteType3.getCurrencyID()).append(getValues(), noteType3.getValues())
					.append(getRelease(), noteType3.getRelease()).append(isConfigured(), noteType3.isConfigured()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("noteID", getNoteID()).append("currencyID", getCurrencyID()).append("values", getValues()).append("release", getRelease())
				.append("configured", isConfigured()).toString();
	}
}
