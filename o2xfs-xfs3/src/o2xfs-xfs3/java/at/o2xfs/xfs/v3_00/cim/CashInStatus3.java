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

import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.XfsExtra;
import at.o2xfs.xfs.cim.CashInStatus;
import at.o2xfs.xfs.win32.XfsWord;

public class CashInStatus3 extends Struct {

	protected final XfsWord<CashInStatus> status = new XfsWord<>(CashInStatus.class);
	protected final USHORT numOfRefused = new USHORT();
	protected final Pointer noteNumberList = new Pointer();
	protected final XfsExtra extra = new XfsExtra();

	protected CashInStatus3() {
		add(status);
		add(numOfRefused);
		add(noteNumberList);
		add(extra);
	}

	public CashInStatus3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CashInStatus3(CashInStatus3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CashInStatus3 copy) {
		status.set(copy.getStatus());
		numOfRefused.set(copy.getNumOfRefused());
		noteNumberList.pointTo(new NoteNumberList3(copy.getNoteNumberList()));
		extra.set(copy.getExtra());
	}

	public CashInStatus getStatus() {
		return status.get();
	}

	public int getNumOfRefused() {
		return numOfRefused.get();
	}

	public NoteNumberList3 getNoteNumberList() {
		return new NoteNumberList3(noteNumberList);
	}

	public Map<String, String> getExtra() {
		return extra.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getStatus()).append(getNumOfRefused()).append(getNoteNumberList()).append(getExtra()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CashInStatus3) {
			CashInStatus3 cashInStatus3 = (CashInStatus3) obj;
			return new EqualsBuilder().append(getStatus(), cashInStatus3.getStatus()).append(getNumOfRefused(), cashInStatus3.getNumOfRefused())
					.append(getNoteNumberList(), cashInStatus3.getNoteNumberList()).append(getExtra(), cashInStatus3.getExtra()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("status", getStatus()).append("numOfRefused", getNumOfRefused()).append("noteNumberList", getNoteNumberList())
				.append("extra", getExtra()).toString();
	}
}
