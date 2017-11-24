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

package at.o2xfs.xfs.v3_10.cdm;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.cdm.CdmGuidLights;
import at.o2xfs.xfs.v3_00.cdm.CdmCaps3;
import at.o2xfs.xfs.win32.XfsBitmaskArray;

public class CdmCaps310 extends CdmCaps3 {

	private static final int GUIDLIGHTS_SIZE = 32;

	protected final XfsBitmaskArray<CdmGuidLights> guidLights = new XfsBitmaskArray<>(GUIDLIGHTS_SIZE, CdmGuidLights.class);
	protected final BOOL powerSaveControl = new BOOL();
	protected final BOOL prepareDispense = new BOOL();

	protected CdmCaps310() {
		add(guidLights);
		add(powerSaveControl);
		add(prepareDispense);
	}

	public CdmCaps310(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CdmCaps310(CdmCaps310 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CdmCaps310 copy) {
		super.set(copy);
		guidLights.set(copy.getGuidLights());
		powerSaveControl.set(copy.isPowerSaveControl());
		prepareDispense.set(copy.isPrepareDispense());
	}

	public List<Set<CdmGuidLights>> getGuidLights() {
		return guidLights.get();
	}

	public boolean isPowerSaveControl() {
		return powerSaveControl.get();
	}

	public boolean isPrepareDispense() {
		return prepareDispense.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(getGuidLights()).append(isPowerSaveControl()).append(isPrepareDispense()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CdmCaps310) {
			CdmCaps310 cdmCaps310 = (CdmCaps310) obj;
			return new EqualsBuilder().appendSuper(super.equals(obj)).append(getGuidLights(), cdmCaps310.getGuidLights())
					.append(isPowerSaveControl(), cdmCaps310.isPowerSaveControl()).append(isPrepareDispense(), cdmCaps310.isPrepareDispense()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("guidLights", getGuidLights()).append("powerSaveControl", isPowerSaveControl())
				.append("prepareDispense", isPrepareDispense()).toString();
	}
}
