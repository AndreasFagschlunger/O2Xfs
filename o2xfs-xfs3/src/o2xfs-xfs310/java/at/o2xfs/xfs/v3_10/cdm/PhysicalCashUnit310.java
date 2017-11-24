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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.ULONG;
import at.o2xfs.xfs.v3_00.cdm.PhysicalCashUnit3;

public class PhysicalCashUnit310 extends PhysicalCashUnit3 {

	protected final ULONG dispensedCount = new ULONG();
	protected final ULONG presentedCount = new ULONG();
	protected final ULONG retractedCount = new ULONG();

	protected PhysicalCashUnit310() {
		add(dispensedCount);
		add(presentedCount);
		add(retractedCount);
	}

	public PhysicalCashUnit310(Pointer p) {
		this();
		assignBuffer(p);
	}

	public PhysicalCashUnit310(PhysicalCashUnit310 copy) {
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
		dispensedCount.set(copy.getDispensedCount());
		presentedCount.set(copy.getPresentedCount());
		retractedCount.set(copy.getRetractedCount());
	}

	public long getDispensedCount() {
		return dispensedCount.get();
	}

	public long getPresentedCount() {
		return presentedCount.get();
	}

	public long getRetractedCount() {
		return retractedCount.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(getDispensedCount()).append(getPresentedCount()).append(getRetractedCount()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PhysicalCashUnit310) {
			PhysicalCashUnit310 physicalCashUnit310 = (PhysicalCashUnit310) obj;
			return new EqualsBuilder().appendSuper(super.equals(obj)).append(getDispensedCount(), physicalCashUnit310.getDispensedCount())
					.append(getPresentedCount(), physicalCashUnit310.getPresentedCount()).append(getRetractedCount(), physicalCashUnit310.getRetractedCount()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("dispensedCount", getDispensedCount()).append("presentedCount", getPresentedCount())
				.append("retractedCount", getRetractedCount()).toString();
	}
}
