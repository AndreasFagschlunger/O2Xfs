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

package at.o2xfs.xfs.cim.v3_00;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.XfsExtra;
import at.o2xfs.xfs.cim.CashInItemType;
import at.o2xfs.xfs.cim.CashInType;
import at.o2xfs.xfs.cim.CashUnitStatus;
import at.o2xfs.xfs.win32.XfsCharArray;
import at.o2xfs.xfs.win32.XfsDWord;
import at.o2xfs.xfs.win32.XfsDWordBitmask;
import at.o2xfs.xfs.win32.XfsWord;

public class CashIn3 extends Struct {

	protected final USHORT number = new USHORT();
	protected final XfsDWord<CashInType> type = new XfsDWord<>(CashInType.class);
	protected final XfsDWordBitmask<CashInItemType> itemType = new XfsDWordBitmask<>(CashInItemType.class);
	protected final XfsCharArray unitID = new XfsCharArray(5);
	protected final XfsCharArray currencyID = new XfsCharArray(3);
	protected final ULONG values = new ULONG();
	protected final ULONG cashInCount = new ULONG();
	protected final ULONG count = new ULONG();
	protected final ULONG maximum = new ULONG();
	protected final XfsWord<CashUnitStatus> status = new XfsWord<>(CashUnitStatus.class);
	protected final BOOL appLock = new BOOL();
	protected final Pointer noteNumberList = new Pointer();
	protected final USHORT numPhysicalCUs = new USHORT();
	protected final Pointer physical = new Pointer();
	protected final XfsExtra extra = new XfsExtra();

	protected CashIn3() {
		add(number);
		add(type);
		add(itemType);
		add(unitID);
		add(currencyID);
		add(values);
		add(cashInCount);
		add(count);
		add(maximum);
		add(status);
		add(appLock);
		add(noteNumberList);
		add(numPhysicalCUs);
		add(physical);
		add(extra);
	}

	public CashIn3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CashIn3(CashIn3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CashIn3 copy) {
		number.set(copy.getNumber());
		type.set(copy.getType());
		itemType.set(copy.getItemType());
		unitID.set(copy.getUnitID());
		currencyID.set(copy.getCurrencyID());
		values.set(copy.getValues());
		cashInCount.set(copy.getCashInCount());
		count.set(copy.getCount());
		maximum.set(copy.getMaximum());
		status.set(copy.getStatus());
		appLock.set(copy.isAppLock());
		Optional<NoteNumberList3> noteNumberList = copy.getNoteNumberList();
		if (noteNumberList.isPresent()) {
			this.noteNumberList.pointTo(new NoteNumberList3(noteNumberList.get()));
		}
		numPhysicalCUs.set(copy.getNumPhysicalCUs());
		physical.pointTo(new PhysicalCashUnits3(copy.getPhysicalCashUnits()));
		extra.set(copy.getExtra());
	}

	public int getNumber() {
		return number.get();
	}

	public CashInType getType() {
		return type.get();
	}

	public Set<CashInItemType> getItemType() {
		return itemType.get();
	}

	public char[] getUnitID() {
		return unitID.get();
	}

	public char[] getCurrencyID() {
		return currencyID.get();
	}

	public long getValues() {
		return values.get();
	}

	public long getCashInCount() {
		return cashInCount.get();
	}

	public long getCount() {
		return count.get();
	}

	public long getMaximum() {
		return maximum.get();
	}

	public CashUnitStatus getStatus() {
		return status.get();
	}

	public boolean isAppLock() {
		return appLock.get();
	}

	public Optional<NoteNumberList3> getNoteNumberList() {
		Optional<NoteNumberList3> result = Optional.empty();
		if (!Pointer.NULL.equals(noteNumberList)) {
			result = Optional.of(new NoteNumberList3(noteNumberList));
		}
		return result;
	}

	public int getNumPhysicalCUs() {
		return numPhysicalCUs.get();
	}

	public PhysicalCashUnit3[] getPhysicalCashUnits() {
		return new PhysicalCashUnits3(physical, getNumPhysicalCUs()).get();
	}

	public Map<String, String> getExtra() {
		return extra.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getNumber()).append(getType()).append(getItemType()).append(getUnitID()).append(getCurrencyID()).append(getValues())
				.append(getCashInCount()).append(getCount()).append(getMaximum()).append(getStatus()).append(isAppLock()).append(getNoteNumberList()).append(getNumPhysicalCUs())
				.append(getPhysicalCashUnits()).append(getExtra()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CashIn3) {
			CashIn3 cashIn3 = (CashIn3) obj;
			return new EqualsBuilder().append(getNumber(), cashIn3.getNumber()).append(getType(), cashIn3.getType()).append(getItemType(), cashIn3.getItemType())
					.append(getUnitID(), cashIn3.getUnitID()).append(getCurrencyID(), cashIn3.getCurrencyID()).append(getValues(), cashIn3.getValues())
					.append(getCashInCount(), cashIn3.getCashInCount()).append(getCount(), cashIn3.getCount()).append(getMaximum(), cashIn3.getMaximum())
					.append(getStatus(), cashIn3.getStatus()).append(isAppLock(), cashIn3.isAppLock()).append(getNoteNumberList(), cashIn3.getNoteNumberList())
					.append(getNumPhysicalCUs(), cashIn3.getNumPhysicalCUs()).append(getPhysicalCashUnits(), cashIn3.getPhysicalCashUnits()).append(getExtra(), cashIn3.getExtra())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("number", getNumber()).append("type", getType()).append("itemType", getItemType()).append("unitID", getUnitID())
				.append("currencyID", getCurrencyID()).append("values", getValues()).append("cashInCount", getCashInCount()).append("count", getCount())
				.append("maximum", getMaximum()).append("status", getStatus()).append("appLock", isAppLock()).append("noteNumberList", getNoteNumberList())
				.append("numPhysicalCUs", getNumPhysicalCUs()).append("physical", getPhysicalCashUnits()).append("extra", getExtra()).toString();
	}
}