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

package at.o2xfs.xfs.v3_10.cim;

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.v3_00.cim.P6Signature3;

public class ItemInfo310 extends Struct {

	protected final USHORT noteID = new USHORT();
	protected final LPSTR serialNumber = new LPSTR();
	protected final Pointer p6Signature = new Pointer();

	protected ItemInfo310() {
		add(noteID);
		add(serialNumber);
		add(p6Signature);
	}

	public ItemInfo310(Pointer p) {
		this();
		assignBuffer(p);
	}

	public ItemInfo310(ItemInfo310 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(ItemInfo310 copy) {
		noteID.set(copy.getNoteID());
		Optional<String> serialNumber = copy.getSerialNumber();
		if (serialNumber.isPresent()) {
			this.serialNumber.set(serialNumber.get());
		}
		Optional<P6Signature3> p6Signature = copy.getP6Signature();
		if (p6Signature.isPresent()) {
			this.p6Signature.pointTo(new P6Signature3(p6Signature.get()));
		}
	}

	public int getNoteID() {
		return noteID.get();
	}

	public Optional<String> getSerialNumber() {
		Optional<String> result = Optional.empty();
		if (!Pointer.NULL.equals(serialNumber)) {
			result = Optional.of(serialNumber.get());
		}
		return result;
	}

	public Optional<P6Signature3> getP6Signature() {
		Optional<P6Signature3> result = Optional.empty();
		if (!Pointer.NULL.equals(p6Signature)) {
			result = Optional.of(new P6Signature3(p6Signature));
		}
		return result;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getNoteID()).append(getSerialNumber()).append(getP6Signature()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemInfo310) {
			ItemInfo310 itemInfo310 = (ItemInfo310) obj;
			return new EqualsBuilder().append(getNoteID(), itemInfo310.getNoteID()).append(getSerialNumber(), itemInfo310.getSerialNumber())
					.append(getP6Signature(), itemInfo310.getP6Signature()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("noteID", getNoteID()).append("serialNumber", getSerialNumber()).append("p6Signature", getP6Signature()).toString();
	}
}
