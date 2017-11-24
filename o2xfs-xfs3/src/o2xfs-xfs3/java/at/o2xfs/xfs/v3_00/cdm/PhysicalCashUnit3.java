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

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.xfs.cdm.CashUnitStatus;
import at.o2xfs.xfs.win32.XfsCharArray;
import at.o2xfs.xfs.win32.XfsWord;

public class PhysicalCashUnit3 extends Struct {

	protected final LPSTR physicalPositionName = new LPSTR();
	protected final XfsCharArray unitID = new XfsCharArray(5);
	protected final ULONG initialCount = new ULONG();
	protected final ULONG count = new ULONG();
	protected final ULONG rejectCount = new ULONG();
	protected final ULONG maximum = new ULONG();
	protected final XfsWord<CashUnitStatus> pStatus = new XfsWord<>(CashUnitStatus.class);
	protected final BOOL hardwareSensor = new BOOL();

	protected PhysicalCashUnit3() {
		add(physicalPositionName);
		add(unitID);
		add(initialCount);
		add(count);
		add(rejectCount);
		add(maximum);
		add(pStatus);
		add(hardwareSensor);
	}

	public PhysicalCashUnit3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public PhysicalCashUnit3(PhysicalCashUnit3 copy) {
		this();
		allocate();
		physicalPositionName.set(copy.getPhysicalPositionName());
		unitID.set(copy.getUnitID());
		initialCount.set(copy.getInitialCount());
		count.set(copy.getCount());
		rejectCount.set(copy.getRejectCount());
		maximum.set(copy.getMaximum());
		pStatus.set(copy.getPStatus());
		hardwareSensor.set(copy.isHardwareSensor());
	}

	public String getPhysicalPositionName() {
		return physicalPositionName.get();
	}

	public char[] getUnitID() {
		return unitID.get();
	}

	public long getInitialCount() {
		return initialCount.get();
	}

	public long getCount() {
		return count.get();
	}

	public long getRejectCount() {
		return rejectCount.get();
	}

	public long getMaximum() {
		return maximum.get();
	}

	public CashUnitStatus getPStatus() {
		return pStatus.get();
	}

	public boolean isHardwareSensor() {
		return hardwareSensor.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getPhysicalPositionName()).append(getUnitID()).append(getInitialCount()).append(getCount()).append(getRejectCount())
				.append(getMaximum()).append(getPStatus()).append(isHardwareSensor()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PhysicalCashUnit3) {
			PhysicalCashUnit3 physicalCashUnit3 = (PhysicalCashUnit3) obj;
			return new EqualsBuilder().append(getPhysicalPositionName(), physicalCashUnit3.getPhysicalPositionName()).append(getUnitID(), physicalCashUnit3.getUnitID())
					.append(getInitialCount(), physicalCashUnit3.getInitialCount()).append(getCount(), physicalCashUnit3.getCount())
					.append(getRejectCount(), physicalCashUnit3.getRejectCount()).append(getMaximum(), physicalCashUnit3.getMaximum())
					.append(getPStatus(), physicalCashUnit3.getPStatus()).append(isHardwareSensor(), physicalCashUnit3.isHardwareSensor()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("physicalPositionName", getPhysicalPositionName()).append("unitID", getUnitID()).append("initialCount", getInitialCount())
				.append("count", getCount()).append("rejectCount", getRejectCount()).append("maximum", getMaximum()).append("pStatus", getPStatus())
				.append("hardwareSensor", isHardwareSensor()).toString();
	}
}
