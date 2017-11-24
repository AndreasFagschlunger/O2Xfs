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
import at.o2xfs.xfs.v3_00.cdm.CashUnit3;

public class CashUnit310 extends CashUnit3 {

	protected final ULONG dispensedCount = new ULONG();
	protected final ULONG presentedCount = new ULONG();
	protected final ULONG retractedCount = new ULONG();

	protected CashUnit310() {
		add(dispensedCount);
		add(presentedCount);
		add(retractedCount);
	}

	public CashUnit310(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CashUnit310(CashUnit310 copy) {
		this();
		allocate();
		number.set(copy.getNumber());
		type.set(copy.getType());
		cashUnitName.set(copy.getCashUnitName());
		unitID.set(copy.getUnitID());
		currencyID.set(copy.getCurrencyID());
		value.set(copy.getValue());
		initialCount.set(copy.getInitialCount());
		count.set(copy.getCount());
		rejectCount.set(copy.getRejectCount());
		minimum.set(copy.getMinimum());
		maximum.set(copy.getMaximum());
		appLock.set(copy.isAppLock());
		status.set(copy.getStatus());
		numPhysicalCUs.set(copy.getNumPhysicalCUs());
		physical.pointTo(new PhysicalCashUnits310(copy.getPhysical()));
		dispensedCount.set(copy.getDispensedCount());
		presentedCount.set(copy.getPresentedCount());
		retractedCount.set(copy.getRetractedCount());
	}

	@Override
	public PhysicalCashUnit310[] getPhysical() {
		return new PhysicalCashUnits310(physical, getNumPhysicalCUs()).get();
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
		if (obj instanceof CashUnit310) {
			CashUnit310 cashUnit310 = (CashUnit310) obj;
			return new EqualsBuilder().appendSuper(super.equals(obj)).append(getDispensedCount(), cashUnit310.getDispensedCount())
					.append(getPresentedCount(), cashUnit310.getPresentedCount()).append(getRetractedCount(), cashUnit310.getRetractedCount()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("dispensedCount", getDispensedCount()).append("presentedCount", getPresentedCount())
				.append("retractedCount", getRetractedCount()).toString();
	}
}
