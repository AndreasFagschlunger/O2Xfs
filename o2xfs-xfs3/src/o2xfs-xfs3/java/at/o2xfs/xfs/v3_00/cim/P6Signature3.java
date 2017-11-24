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

import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cim.Orientation;
import at.o2xfs.xfs.win32.XfsDWord;

public class P6Signature3 extends Struct {

	protected final USHORT noteId = new USHORT();
	protected final ULONG length = new ULONG();
	protected final XfsDWord<Orientation> orientation = new XfsDWord<>(Orientation.class);
	protected final Pointer signature = new Pointer();

	protected P6Signature3() {
		add(noteId);
		add(length);
		add(orientation);
		add(signature);
	}

	public P6Signature3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public P6Signature3(P6Signature3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(P6Signature3 copy) {
		noteId.set(copy.getNoteId());
		length.set(copy.getLength());
		orientation.set(copy.getOrientation());
		signature.pointTo(new ByteArray(copy.getSignature()));
	}

	public int getNoteId() {
		return noteId.get();
	}

	private long getLength() {
		return length.get();
	}

	public Orientation getOrientation() {
		return orientation.get();
	}

	public byte[] getSignature() {
		return signature.buffer(length.intValue()).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getNoteId()).append(getLength()).append(getOrientation()).append(getSignature()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof P6Signature3) {
			P6Signature3 p6Signature3 = (P6Signature3) obj;
			return new EqualsBuilder().append(getNoteId(), p6Signature3.getNoteId()).append(getLength(), p6Signature3.getLength())
					.append(getOrientation(), p6Signature3.getOrientation()).append(getSignature(), p6Signature3.getSignature()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("noteId", getNoteId()).append("length", getLength()).append("orientation", getOrientation()).append("signature", getSignature())
				.toString();
	}
}
