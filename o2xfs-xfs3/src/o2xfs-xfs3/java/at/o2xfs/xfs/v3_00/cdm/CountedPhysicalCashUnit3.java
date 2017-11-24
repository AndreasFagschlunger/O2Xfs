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

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.xfs.cdm.CashUnitStatus;
import at.o2xfs.xfs.win32.XfsCharArray;
import at.o2xfs.xfs.win32.XfsWord;

public class CountedPhysicalCashUnit3 extends Struct {

	protected final LPSTR physicalPositionName = new LPSTR();
	protected final XfsCharArray unitId = new XfsCharArray(5);
	protected final ULONG dispensed = new ULONG();
	protected final ULONG counted = new ULONG();
	protected final XfsWord<CashUnitStatus> pStatus = new XfsWord<>(CashUnitStatus.class);

	protected CountedPhysicalCashUnit3() {
		add(physicalPositionName);
		add(unitId);
		add(dispensed);
		add(counted);
		add(pStatus);
	}

	public CountedPhysicalCashUnit3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CountedPhysicalCashUnit3(CountedPhysicalCashUnit3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CountedPhysicalCashUnit3 copy) {
		physicalPositionName.set(copy.getPhysicalPositionName());
		unitId.set(copy.getUnitId());
		dispensed.set(copy.getDispensed());
		counted.set(copy.getCounted());
		pStatus.set(copy.getPStatus());
	}

	public String getPhysicalPositionName() {
		return physicalPositionName.get();
	}

	public char[] getUnitId() {
		return unitId.get();
	}

	public long getDispensed() {
		return dispensed.get();
	}

	public long getCounted() {
		return counted.get();
	}

	public CashUnitStatus getPStatus() {
		return pStatus.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getPhysicalPositionName()).append(getUnitId()).append(getDispensed()).append(getCounted()).append(getPStatus()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CountedPhysicalCashUnit3) {
			CountedPhysicalCashUnit3 countedPhysicalCashUnit3 = (CountedPhysicalCashUnit3) obj;
			return new EqualsBuilder().append(getPhysicalPositionName(), countedPhysicalCashUnit3.getPhysicalPositionName())
					.append(getUnitId(), countedPhysicalCashUnit3.getUnitId()).append(getDispensed(), countedPhysicalCashUnit3.getDispensed())
					.append(getCounted(), countedPhysicalCashUnit3.getCounted()).append(getPStatus(), countedPhysicalCashUnit3.getPStatus()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("physicalPositionName", getPhysicalPositionName()).append("unitId", getUnitId()).append("dispensed", getDispensed())
				.append("counted", getCounted()).append("pStatus", getPStatus()).toString();
	}
}
