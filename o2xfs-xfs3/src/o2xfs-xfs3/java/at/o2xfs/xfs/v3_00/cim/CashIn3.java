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

package at.o2xfs.xfs.v3_00.cim;

import java.util.Collections;
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

	public static class Builder {
		private final int number;
		private final CashInType type;
		private final Set<CashInItemType> itemType;
		private final char[] unitID;
		private final char[] currencyID;
		private long value;
		private long cashInCount;
		private long count;
		private long maximum;
		private final CashUnitStatus status;
		private boolean appLock;
		private Optional<NoteNumberList3> noteNumberList;
		private final PhysicalCashUnit3[] physicalCashUnits;
		private Map<String, String> extra;

		public Builder(int number, CashInType type, Set<CashInItemType> itemType, char[] unitID, char[] currencyID,
				CashUnitStatus status, PhysicalCashUnit3[] physicalCashUnits) {
			this.number = number;
			this.type = type;
			this.itemType = itemType;
			this.unitID = unitID;
			this.currencyID = currencyID;
			value = 0L;
			cashInCount = 0L;
			count = 0L;
			maximum = 0L;
			this.status = status;
			this.appLock = false;
			this.noteNumberList = Optional.empty();
			this.physicalCashUnits = physicalCashUnits;
			this.extra = Collections.emptyMap();
		}

		public Builder value(long value) {
			this.value = value;
			return this;
		}

		public Builder cashInCount(long cashInCount) {
			this.cashInCount = cashInCount;
			return this;
		}

		public Builder count(long count) {
			this.count = count;
			return this;
		}

		public Builder maximum(long maximum) {
			this.maximum = maximum;
			return this;
		}

		public Builder appLock(boolean appLock) {
			this.appLock = appLock;
			return this;
		}

		public Builder noteNumberList(Optional<NoteNumberList3> noteNumberList) {
			this.noteNumberList = noteNumberList;
			return this;
		}

		public Builder extra(Map<String, String> extra) {
			this.extra = extra;
			return this;
		}

		public CashIn3 build() {
			return new CashIn3(this);
		}
	}

	protected final USHORT number = new USHORT();
	protected final XfsDWord<CashInType> type = new XfsDWord<>(CashInType.class);
	protected final XfsDWordBitmask<CashInItemType> itemType = new XfsDWordBitmask<>(CashInItemType.class);
	protected final XfsCharArray unitID = new XfsCharArray(5);
	protected final XfsCharArray currencyID = new XfsCharArray(3);
	protected final ULONG value = new ULONG();
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
		add(value);
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

	protected CashIn3(Builder builder) {
		this();
		allocate();
		set(builder);
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

	protected void set(Builder builder) {
		number.set(builder.number);
		type.set(builder.type);
		itemType.set(builder.itemType);
		unitID.set(builder.unitID);
		currencyID.set(builder.currencyID);
		value.set(builder.value);
		cashInCount.set(builder.cashInCount);
		count.set(builder.count);
		maximum.set(builder.maximum);
		status.set(builder.status);
		appLock.set(builder.appLock);
		if (builder.noteNumberList.isPresent()) {
			noteNumberList.pointTo(builder.noteNumberList.get());
		}
		numPhysicalCUs.set(builder.physicalCashUnits.length);
		physical.pointTo(new PhysicalCashUnits3(builder.physicalCashUnits));
		extra.set(builder.extra);
	}

	protected void set(CashIn3 copy) {
		number.set(copy.getNumber());
		type.set(copy.getType());
		itemType.set(copy.getItemType());
		unitID.set(copy.getUnitID());
		currencyID.set(copy.getCurrencyID());
		value.set(copy.getValue());
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

	public long getValue() {
		return value.get();
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
		return new HashCodeBuilder()
				.append(getNumber())
				.append(getType())
				.append(getItemType())
				.append(getUnitID())
				.append(getCurrencyID())
				.append(getValue())
				.append(getCashInCount())
				.append(getCount())
				.append(getMaximum())
				.append(getStatus())
				.append(isAppLock())
				.append(getNoteNumberList())
				.append(getNumPhysicalCUs())
				.append(getPhysicalCashUnits())
				.append(getExtra())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CashIn3) {
			CashIn3 cashIn3 = (CashIn3) obj;
			return new EqualsBuilder()
					.append(getNumber(), cashIn3.getNumber())
					.append(getType(), cashIn3.getType())
					.append(getItemType(), cashIn3.getItemType())
					.append(getUnitID(), cashIn3.getUnitID())
					.append(getCurrencyID(), cashIn3.getCurrencyID())
					.append(getValue(), cashIn3.getValue())
					.append(getCashInCount(), cashIn3.getCashInCount())
					.append(getCount(), cashIn3.getCount())
					.append(getMaximum(), cashIn3.getMaximum())
					.append(getStatus(), cashIn3.getStatus())
					.append(isAppLock(), cashIn3.isAppLock())
					.append(getNoteNumberList(), cashIn3.getNoteNumberList())
					.append(getNumPhysicalCUs(), cashIn3.getNumPhysicalCUs())
					.append(getPhysicalCashUnits(), cashIn3.getPhysicalCashUnits())
					.append(getExtra(), cashIn3.getExtra())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("number", getNumber())
				.append("type", getType())
				.append("itemType", getItemType())
				.append("unitID", getUnitID())
				.append("currencyID", getCurrencyID())
				.append("value", getValue())
				.append("cashInCount", getCashInCount())
				.append("count", getCount())
				.append("maximum", getMaximum())
				.append("status", getStatus())
				.append("appLock", isAppLock())
				.append("noteNumberList", getNoteNumberList())
				.append("numPhysicalCUs", getNumPhysicalCUs())
				.append("physical", getPhysicalCashUnits())
				.append("extra", getExtra())
				.toString();
	}
}
