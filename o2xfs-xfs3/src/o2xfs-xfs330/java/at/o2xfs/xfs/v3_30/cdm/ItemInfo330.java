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

package at.o2xfs.xfs.v3_30.cdm;

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.LPWSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.win32.XfsCharArray;

public class ItemInfo330 extends Struct {

	protected final XfsCharArray currencyID = new XfsCharArray(3);
	protected final ULONG value = new ULONG();
	protected final USHORT release = new USHORT();
	protected final LPWSTR serialNumber = new LPWSTR();
	protected final Pointer signature = new Pointer();
	protected final LPSTR imageFileName = new LPSTR();

	protected ItemInfo330() {
		add(currencyID);
		add(value);
		add(release);
		add(serialNumber);
		add(signature);
		add(imageFileName);
	}

	public ItemInfo330(Pointer p) {
		this();
		assignBuffer(p);
	}

	public ItemInfo330(ItemInfo330 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(ItemInfo330 copy) {
		currencyID.set(copy.getCurrencyID());
		value.set(copy.getValue());
		release.set(copy.getRelease());
		serialNumber.set(copy.getSerialNumber());
		Optional<Signature330> signature = copy.getSignature();
		if (signature.isPresent()) {
			this.signature.pointTo(new Signature330(signature.get()));
		}
		imageFileName.set(copy.getImageFileName());
	}

	public char[] getCurrencyID() {
		return currencyID.get();
	}

	public long getValue() {
		return value.get();
	}

	public int getRelease() {
		return release.get();
	}

	public String getSerialNumber() {
		return serialNumber.get();
	}

	public Optional<Signature330> getSignature() {
		Optional<Signature330> result = Optional.empty();
		if (!Pointer.NULL.equals(signature)) {
			result = Optional.of(new Signature330(signature));
		}
		return result;
	}

	public String getImageFileName() {
		return imageFileName.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getCurrencyID()).append(getValue()).append(getRelease()).append(getSerialNumber()).append(getSignature()).append(getImageFileName())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemInfo330) {
			ItemInfo330 itemInfo = (ItemInfo330) obj;
			return new EqualsBuilder().append(getCurrencyID(), itemInfo.getCurrencyID()).append(getValue(), itemInfo.getValue()).append(getRelease(), itemInfo.getRelease())
					.append(getSerialNumber(), itemInfo.getSerialNumber()).append(getSignature(), itemInfo.getSignature()).append(getImageFileName(), itemInfo.getImageFileName())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("currencyID", getCurrencyID()).append("value", getValue()).append("release", getRelease()).append("serialNumber", getSerialNumber())
				.append("signature", getSignature()).append("imageFileName", getImageFileName()).toString();
	}
}
