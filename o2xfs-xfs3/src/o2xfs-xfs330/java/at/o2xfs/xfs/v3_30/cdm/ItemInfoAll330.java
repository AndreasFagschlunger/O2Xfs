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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.LPWSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cdm.ItemLocation;
import at.o2xfs.xfs.cdm.OnBlacklist;
import at.o2xfs.xfs.win32.XfsCharArray;
import at.o2xfs.xfs.win32.XfsWord;

public class ItemInfoAll330 extends Struct {

	protected final USHORT level = new USHORT();
	protected final XfsCharArray currencyID = new XfsCharArray(3);
	protected final ULONG value = new ULONG();
	protected final USHORT release = new USHORT();
	protected final LPWSTR serialNumber = new LPWSTR();
	protected final LPSTR imageFileName = new LPSTR();
	protected final XfsWord<OnBlacklist> onBlacklist = new XfsWord<>(OnBlacklist.class);
	protected final XfsWord<ItemLocation> itemLocation = new XfsWord<>(ItemLocation.class);
	protected final USHORT number = new USHORT();

	protected ItemInfoAll330() {
		add(level);
		add(currencyID);
		add(value);
		add(release);
		add(serialNumber);
		add(imageFileName);
		add(onBlacklist);
		add(itemLocation);
		add(number);
	}

	public ItemInfoAll330(Pointer p) {
		this();
		assignBuffer(p);
	}

	public ItemInfoAll330(ItemInfoAll330 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(ItemInfoAll330 copy) {
		level.set(copy.getLevel());
		currencyID.set(copy.getCurrencyID());
		value.set(copy.getValue());
		release.set(copy.getRelease());
		serialNumber.set(copy.getSerialNumber());
		imageFileName.set(copy.getImageFileName());
		onBlacklist.set(copy.getOnBlacklist());
		itemLocation.set(copy.getItemLocation());
		number.set(copy.getNumber());
	}

	public int getLevel() {
		return level.get();
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

	public String getImageFileName() {
		return imageFileName.get();
	}

	public OnBlacklist getOnBlacklist() {
		return onBlacklist.get();
	}

	public ItemLocation getItemLocation() {
		return itemLocation.get();
	}

	public int getNumber() {
		return number.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getLevel()).append(getCurrencyID()).append(getValue()).append(getRelease()).append(getSerialNumber()).append(getImageFileName())
				.append(getOnBlacklist()).append(getItemLocation()).append(getNumber()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemInfoAll330) {
			ItemInfoAll330 itemInfoAll = (ItemInfoAll330) obj;
			return new EqualsBuilder().append(getLevel(), itemInfoAll.getLevel()).append(getCurrencyID(), itemInfoAll.getCurrencyID()).append(getValue(), itemInfoAll.getValue())
					.append(getRelease(), itemInfoAll.getRelease()).append(getSerialNumber(), itemInfoAll.getSerialNumber())
					.append(getImageFileName(), itemInfoAll.getImageFileName()).append(getOnBlacklist(), itemInfoAll.getOnBlacklist())
					.append(getItemLocation(), itemInfoAll.getItemLocation()).append(getNumber(), itemInfoAll.getNumber()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("level", getLevel()).append("currencyID", getCurrencyID()).append("value", getValue()).append("release", getRelease())
				.append("serialNumber", getSerialNumber()).append("imageFileName", getImageFileName()).append("onBlacklist", getOnBlacklist())
				.append("itemLocation", getItemLocation()).append("number", getNumber()).toString();
	}
}
