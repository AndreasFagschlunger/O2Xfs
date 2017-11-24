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
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cdm.CashUnitStatus;
import at.o2xfs.xfs.cdm.CashUnitType;
import at.o2xfs.xfs.win32.XfsCharArray;
import at.o2xfs.xfs.win32.XfsWord;

public class CashUnit3 extends Struct {

	protected final USHORT number = new USHORT();
	protected final XfsWord<CashUnitType> type = new XfsWord<>(CashUnitType.class);
	protected final LPSTR cashUnitName = new LPSTR();
	protected final XfsCharArray unitID = new XfsCharArray(5);
	protected final XfsCharArray currencyID = new XfsCharArray(3);
	protected final ULONG value = new ULONG();
	protected final ULONG initialCount = new ULONG();
	protected final ULONG count = new ULONG();
	protected final ULONG rejectCount = new ULONG();
	protected final ULONG minimum = new ULONG();
	protected final ULONG maximum = new ULONG();
	protected final BOOL appLock = new BOOL();
	protected final XfsWord<CashUnitStatus> status = new XfsWord<>(CashUnitStatus.class);
	protected final USHORT numPhysicalCUs = new USHORT();
	protected final Pointer physical = new Pointer();

	protected CashUnit3() {
		add(number);
		add(type);
		add(cashUnitName);
		add(unitID);
		add(currencyID);
		add(value);
		add(initialCount);
		add(count);
		add(rejectCount);
		add(minimum);
		add(maximum);
		add(appLock);
		add(status);
		add(numPhysicalCUs);
		add(physical);
	}

	public CashUnit3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CashUnit3(CashUnit3 copy) {
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
		physical.pointTo(new PhysicalCashUnits3(copy.getPhysical()));
	}

	public int getNumber() {
		return number.get();
	}

	public CashUnitType getType() {
		return type.get();
	}

	public String getCashUnitName() {
		return cashUnitName.get();
	}

	public char[] getUnitID() {
		return unitID.get();
	}

	public char[] getCurrencyID() {
		return currencyID.get();
	}

	public long getValue() {
		return value.get();
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

	public long getMinimum() {
		return minimum.get();
	}

	public long getMaximum() {
		return maximum.get();
	}

	public boolean isAppLock() {
		return appLock.get();
	}

	public CashUnitStatus getStatus() {
		return status.get();
	}

	public int getNumPhysicalCUs() {
		return numPhysicalCUs.get();
	}

	public PhysicalCashUnit3[] getPhysical() {
		return new PhysicalCashUnits3(physical, getNumPhysicalCUs()).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getNumber()).append(getType()).append(getCashUnitName()).append(getUnitID()).append(getCurrencyID()).append(getValue())
				.append(getInitialCount()).append(getCount()).append(getRejectCount()).append(getMinimum()).append(getMaximum()).append(isAppLock()).append(getStatus())
				.append(getNumPhysicalCUs()).append(getPhysical()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CashUnit3) {
			CashUnit3 cashUnit3 = (CashUnit3) obj;
			return new EqualsBuilder().append(getNumber(), cashUnit3.getNumber()).append(getType(), cashUnit3.getType()).append(getCashUnitName(), cashUnit3.getCashUnitName())
					.append(getUnitID(), cashUnit3.getUnitID()).append(getCurrencyID(), cashUnit3.getCurrencyID()).append(getValue(), cashUnit3.getValue())
					.append(getInitialCount(), cashUnit3.getInitialCount()).append(getCount(), cashUnit3.getCount()).append(getRejectCount(), cashUnit3.getRejectCount())
					.append(getMinimum(), cashUnit3.getMinimum()).append(getMaximum(), cashUnit3.getMaximum()).append(isAppLock(), cashUnit3.isAppLock())
					.append(getStatus(), cashUnit3.getStatus()).append(getNumPhysicalCUs(), cashUnit3.getNumPhysicalCUs()).append(getPhysical(), cashUnit3.getPhysical())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("number", getNumber()).append("type", getType()).append("cashUnitName", getCashUnitName()).append("unitID[5]", getUnitID())
				.append("currencyID[3]", getCurrencyID()).append("value", getValue()).append("initialCount", getInitialCount()).append("count", getCount())
				.append("rejectCount", getRejectCount()).append("minimum", getMinimum()).append("maximum", getMaximum()).append("appLock", isAppLock())
				.append("status", getStatus()).append("numPhysicalCUs", getNumPhysicalCUs()).append("physical", getPhysical()).toString();
	}
}
