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

package at.o2xfs.xfs.cim.v3_10;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.cim.GuidLight;
import at.o2xfs.xfs.cim.ItemInfoType;
import at.o2xfs.xfs.cim.v3_00.Capabilities3;
import at.o2xfs.xfs.win32.XfsBitmaskArray;
import at.o2xfs.xfs.win32.XfsDWordBitmask;

public class Capabilities3_10 extends Capabilities3 {

	protected final XfsBitmaskArray<GuidLight> guidLights = new XfsBitmaskArray<>(32, GuidLight.class);
	protected final XfsDWordBitmask<ItemInfoType> itemInfoTypes = new XfsDWordBitmask<>(ItemInfoType.class);
	protected final BOOL compareSignatures = new BOOL();
	protected final BOOL powerSaveControl = new BOOL();

	protected Capabilities3_10() {
		add(guidLights);
		add(itemInfoTypes);
		add(compareSignatures);
		add(powerSaveControl);
	}

	public Capabilities3_10(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Capabilities3_10(Capabilities3_10 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Capabilities3_10 copy) {
		super.set(copy);
		guidLights.set(copy.getGuidLights());
		itemInfoTypes.set(copy.getItemInfoTypes());
		compareSignatures.set(copy.isCompareSignatures());
		powerSaveControl.set(copy.isPowerSaveControl());
	}

	public List<Set<GuidLight>> getGuidLights() {
		return guidLights.get();
	}

	public Set<ItemInfoType> getItemInfoTypes() {
		return itemInfoTypes.get();
	}

	public boolean isCompareSignatures() {
		return compareSignatures.get();
	}

	public boolean isPowerSaveControl() {
		return powerSaveControl.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(getGuidLights()).append(getItemInfoTypes()).append(isCompareSignatures()).append(isPowerSaveControl())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Capabilities3_10) {
			Capabilities3_10 capabilities3_10 = (Capabilities3_10) obj;
			return new EqualsBuilder().appendSuper(super.equals(obj)).append(getGuidLights(), capabilities3_10.getGuidLights())
					.append(getItemInfoTypes(), capabilities3_10.getItemInfoTypes()).append(isCompareSignatures(), capabilities3_10.isCompareSignatures())
					.append(isPowerSaveControl(), capabilities3_10.isPowerSaveControl()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("guidLights", getGuidLights()).append("itemInfoTypes", getItemInfoTypes())
				.append("compareSignatures", isCompareSignatures()).append("powerSaveControl", isPowerSaveControl()).toString();
	}
}