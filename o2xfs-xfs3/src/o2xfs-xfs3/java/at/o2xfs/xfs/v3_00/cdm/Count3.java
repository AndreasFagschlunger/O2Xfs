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

public class Count3 extends Struct {

	protected final USHORT numPhysicalCUs = new USHORT();
	protected final Pointer countedPhysCUs = new Pointer();

	protected Count3() {
		add(numPhysicalCUs);
		add(countedPhysCUs);
	}

	public Count3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Count3(Count3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Count3 copy) {
		numPhysicalCUs.set(copy.getNumPhysicalCUs());
		countedPhysCUs.pointTo(new CountedPhysicalCashUnits3(copy.getCountedPhysCUs()));
	}

	public int getNumPhysicalCUs() {
		return numPhysicalCUs.get();
	}

	public CountedPhysicalCashUnit3[] getCountedPhysCUs() {
		return new CountedPhysicalCashUnits3(countedPhysCUs, getNumPhysicalCUs()).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getNumPhysicalCUs()).append(getCountedPhysCUs()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Count3) {
			Count3 count3 = (Count3) obj;
			return new EqualsBuilder().append(getNumPhysicalCUs(), count3.getNumPhysicalCUs()).append(getCountedPhysCUs(), count3.getCountedPhysCUs()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("numPhysicalCUs", getNumPhysicalCUs()).append("countedPhysCUs", getCountedPhysCUs()).toString();
	}
}
