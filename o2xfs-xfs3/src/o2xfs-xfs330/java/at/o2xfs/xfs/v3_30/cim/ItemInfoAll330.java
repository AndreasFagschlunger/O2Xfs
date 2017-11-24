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

package at.o2xfs.xfs.v3_30.cim;

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cim.ItemLocation;
import at.o2xfs.xfs.cim.Level;
import at.o2xfs.xfs.cim.OnBlacklist;
import at.o2xfs.xfs.cim.Orientation;
import at.o2xfs.xfs.win32.XfsDWord;
import at.o2xfs.xfs.win32.XfsWord;

public class ItemInfoAll330 extends Struct {

	protected final XfsWord<Level> level = new XfsWord<>(Level.class);
	protected final USHORT noteID = new USHORT();
	protected final LPSTR serialNumber = new LPSTR();
	protected final XfsDWord<Orientation> orientation = new XfsDWord<>(Orientation.class);
	protected final LPSTR p6SignatureFileName = new LPSTR();
	protected final LPSTR imageFileName = new LPSTR();
	protected final XfsWord<OnBlacklist> onBlacklist = new XfsWord<>(OnBlacklist.class);
	protected final XfsWord<ItemLocation> itemLocation = new XfsWord<>(ItemLocation.class);
	protected final USHORT number = new USHORT();

	protected ItemInfoAll330() {
		add(level);
		add(noteID);
		add(serialNumber);
		add(orientation);
		add(p6SignatureFileName);
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
		noteID.set(copy.getNoteID());
		Optional<String> serialNumber = copy.getSerialNumber();
		if (serialNumber.isPresent()) {
			this.serialNumber.set(serialNumber.get());
		}
		orientation.set(copy.getOrientation());
		Optional<String> p6SignatureFileName = copy.getP6SignatureFileName();
		if (p6SignatureFileName.isPresent()) {
			this.p6SignatureFileName.set(copy.getP6SignatureFileName().get());
		}
		Optional<String> imageFileName = copy.getImageFileName();
		if (imageFileName.isPresent()) {
			this.imageFileName.set(copy.getImageFileName().get());
		}
		onBlacklist.set(copy.getOnBlacklist());
		itemLocation.set(copy.getItemLocation());
		number.set(copy.getNumber());
	}

	public Level getLevel() {
		return level.get();
	}

	public int getNoteID() {
		return noteID.get();
	}

	public Optional<String> getSerialNumber() {
		return Optional.ofNullable(serialNumber.get());
	}

	public Orientation getOrientation() {
		return orientation.get();
	}

	public Optional<String> getP6SignatureFileName() {
		return Optional.ofNullable(p6SignatureFileName.get());
	}

	public Optional<String> getImageFileName() {
		return Optional.ofNullable(imageFileName.get());
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
		return new HashCodeBuilder().append(getLevel()).append(getNoteID()).append(getSerialNumber()).append(getOrientation()).append(getP6SignatureFileName())
				.append(getImageFileName()).append(getOnBlacklist()).append(getItemLocation()).append(getNumber()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemInfoAll330) {
			ItemInfoAll330 itemInfoAll330 = (ItemInfoAll330) obj;
			return new EqualsBuilder().append(getLevel(), itemInfoAll330.getLevel()).append(getNoteID(), itemInfoAll330.getNoteID())
					.append(getSerialNumber(), itemInfoAll330.getSerialNumber()).append(getOrientation(), itemInfoAll330.getOrientation())
					.append(getP6SignatureFileName(), itemInfoAll330.getP6SignatureFileName()).append(getImageFileName(), itemInfoAll330.getImageFileName())
					.append(getOnBlacklist(), itemInfoAll330.getOnBlacklist()).append(getItemLocation(), itemInfoAll330.getItemLocation())
					.append(getNumber(), itemInfoAll330.getNumber()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("level", getLevel()).append("noteID", getNoteID()).append("serialNumber", getSerialNumber()).append("orientation", getOrientation())
				.append("p6SignatureFileName", getP6SignatureFileName()).append("imageFileName", getImageFileName()).append("onBlacklist", getOnBlacklist())
				.append("itemLocation", getItemLocation()).append("number", getNumber()).toString();
	}
}
