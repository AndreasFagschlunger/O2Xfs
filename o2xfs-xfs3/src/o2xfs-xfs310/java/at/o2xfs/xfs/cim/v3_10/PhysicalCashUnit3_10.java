/*
 * Copyright (c) 2016, Andreas Fagschlunger. All rights reserved.
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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.ULONG;
import at.o2xfs.xfs.cim.v3_00.PhysicalCashUnit3;

public class PhysicalCashUnit3_10 extends PhysicalCashUnit3 {

	protected final ULONG initialCount = new ULONG();
	protected final ULONG dispensedCount = new ULONG();
	protected final ULONG presentedCount = new ULONG();
	protected final ULONG retractedCount = new ULONG();
	protected final ULONG rejectCount = new ULONG();

	protected PhysicalCashUnit3_10() {
		add(initialCount);
		add(dispensedCount);
		add(presentedCount);
		add(retractedCount);
		add(rejectCount);
	}

	public PhysicalCashUnit3_10(Pointer p) {
		this();
		assignBuffer(p);
	}

	public PhysicalCashUnit3_10(PhysicalCashUnit3_10 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(PhysicalCashUnit3_10 copy) {
		super.set(copy);
		initialCount.set(copy.getInitialCount());
		dispensedCount.set(copy.getDispensedCount());
		presentedCount.set(copy.getPresentedCount());
		retractedCount.set(copy.getRetractedCount());
		rejectCount.set(copy.getRejectCount());
	}

	public long getInitialCount() {
		return initialCount.get();
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

	public long getRejectCount() {
		return rejectCount.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(getInitialCount()).append(getDispensedCount()).append(getPresentedCount()).append(getRetractedCount())
				.append(getRejectCount()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PhysicalCashUnit3_10) {
			PhysicalCashUnit3_10 physicalCashUnit3_10 = (PhysicalCashUnit3_10) obj;
			return new EqualsBuilder().appendSuper(super.equals(obj)).append(getInitialCount(), physicalCashUnit3_10.getInitialCount())
					.append(getDispensedCount(), physicalCashUnit3_10.getDispensedCount()).append(getPresentedCount(), physicalCashUnit3_10.getPresentedCount())
					.append(getRetractedCount(), physicalCashUnit3_10.getRetractedCount()).append(getRejectCount(), physicalCashUnit3_10.getRejectCount()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("initialCount", getInitialCount()).append("dispensedCount", getDispensedCount())
				.append("presentedCount", getPresentedCount()).append("retractedCount", getRetractedCount()).append("rejectCount", getRejectCount()).toString();
	}
}