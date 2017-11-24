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

package at.o2xfs.xfs.v3_00.cdm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;

public class Denominate3 extends Struct {

	protected final USHORT tellerID = new USHORT();
	protected final USHORT mixNumber = new USHORT();
	protected final Pointer denomination = new Pointer();

	protected Denominate3() {
		add(tellerID);
		add(mixNumber);
		add(denomination);
	}

	public Denominate3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Denominate3(Denominate3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Denominate3 copy) {
		tellerID.set(copy.getTellerID());
		mixNumber.set(copy.getMixNumber());
		denomination.pointTo(new Denomination3(copy.getDenomination()));
	}

	public int getTellerID() {
		return tellerID.get();
	}

	public int getMixNumber() {
		return mixNumber.get();
	}

	public Denomination3 getDenomination() {
		return new Denomination3(denomination);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getTellerID()).append(getMixNumber()).append(getDenomination()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Denominate3) {
			Denominate3 denominate = (Denominate3) obj;
			return new EqualsBuilder().append(getTellerID(), denominate.getTellerID()).append(getMixNumber(), denominate.getMixNumber())
					.append(getDenomination(), denominate.getDenomination()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("tellerID", getTellerID()).append("mixNumber", getMixNumber()).append("denomination", getDenomination()).toString();
	}
}
