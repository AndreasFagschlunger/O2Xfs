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

package at.o2xfs.xfs.v3_10.cim;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.ULONG;
import at.o2xfs.xfs.cdm.CashUnitType;
import at.o2xfs.xfs.cim.CashInItemType;
import at.o2xfs.xfs.cim.CashInType;
import at.o2xfs.xfs.cim.CashUnitStatus;
import at.o2xfs.xfs.v3_00.cim.CashIn3;
import at.o2xfs.xfs.v3_00.cim.NoteIDs;
import at.o2xfs.xfs.v3_00.cim.NoteNumberList3;
import at.o2xfs.xfs.win32.XfsWord;

public class CashIn310 extends CashIn3 {

	public static class Builder extends CashIn3.Builder {

		private int[] noteIds;
		private final CashUnitType cdmType;
		private Optional<String> cashUnitName;
		private long initialCount;
		private long dispensedCount;
		private long presentedCount;
		private long retractedCount;
		private long rejectCount;
		private long minimum;

		public Builder(int number, CashInType type, Set<CashInItemType> itemType, char[] unitID, char[] currencyID,
				CashUnitStatus status, PhysicalCashUnit310[] physicalCashUnits, CashUnitType cdmType) {
			super(number, type, itemType, unitID, currencyID, status, physicalCashUnits);
			noteIds = new int[0];
			this.cdmType = cdmType;
			cashUnitName = Optional.empty();
		}

		@Override
		public Builder value(long value) {
			super.value(value);
			return this;
		}

		@Override
		public Builder cashInCount(long cashInCount) {
			super.cashInCount(cashInCount);
			return this;
		}

		@Override
		public Builder count(long count) {
			super.count(count);
			return this;
		}

		@Override
		public Builder maximum(long maximum) {
			super.maximum(maximum);
			return this;
		}

		@Override
		public Builder appLock(boolean appLock) {
			super.appLock(appLock);
			return this;
		}

		@Override
		public Builder noteNumberList(Optional<NoteNumberList3> noteNumberList) {
			super.noteNumberList(noteNumberList);
			return this;
		}

		@Override
		public Builder extra(Map<String, String> extra) {
			super.extra(extra);
			return this;
		}

		public Builder noteIds(int[] noteIds) {
			this.noteIds = noteIds;
			return this;
		}

		public Builder cashUnitName(Optional<String> cashUnitName) {
			this.cashUnitName = cashUnitName;
			return this;
		}

		public Builder initialCount(long initialCount) {
			this.initialCount = initialCount;
			return this;
		}

		public Builder dispensedCount(long dispensedCount) {
			this.dispensedCount = dispensedCount;
			return this;
		}

		public Builder presentedCount(long presentedCount) {
			this.presentedCount = presentedCount;
			return this;
		}

		public Builder retractedCount(long retractedCount) {
			this.retractedCount = retractedCount;
			return this;
		}

		public Builder rejectCount(long rejectCount) {
			this.rejectCount = rejectCount;
			return this;
		}

		public Builder minimum(long minimum) {
			this.minimum = minimum;
			return this;
		}

		@Override
		public CashIn310 build() {
			return new CashIn310(this);
		}
	}

	protected final Pointer noteIds = new Pointer();
	protected final XfsWord<CashUnitType> cdmType = new XfsWord<>(CashUnitType.class);
	protected final LPSTR cashUnitName = new LPSTR();
	protected final ULONG initialCount = new ULONG();
	protected final ULONG dispensedCount = new ULONG();
	protected final ULONG presentedCount = new ULONG();
	protected final ULONG retractedCount = new ULONG();
	protected final ULONG rejectCount = new ULONG();
	protected final ULONG minimum = new ULONG();

	protected CashIn310() {
		add(noteIds);
		add(cdmType);
		add(cashUnitName);
		add(initialCount);
		add(dispensedCount);
		add(presentedCount);
		add(retractedCount);
		add(rejectCount);
		add(minimum);
	}

	protected CashIn310(Builder builder) {
		this();
		allocate();
		super.set(builder);
		noteIds.pointTo(new NoteIDs(builder.noteIds));
		cdmType.set(builder.cdmType);
		cashUnitName.set(builder.cashUnitName.orElse(null));
		initialCount.set(builder.initialCount);
		dispensedCount.set(builder.dispensedCount);
		presentedCount.set(builder.presentedCount);
		retractedCount.set(builder.retractedCount);
		rejectCount.set(builder.rejectCount);
		minimum.set(builder.minimum);
	}

	public CashIn310(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CashIn310(CashIn310 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CashIn310 copy) {
		super.set(copy);
		noteIds.pointTo(new NoteIDs(copy.getNoteIds()));
		cdmType.set(copy.getCashUnitType());
		cashUnitName.set(copy.getCashUnitName().orElse(null));
		initialCount.set(copy.getInitialCount());
		dispensedCount.set(copy.getDispensedCount());
		presentedCount.set(copy.getPresentedCount());
		retractedCount.set(copy.getRetractedCount());
		rejectCount.set(copy.getRejectCount());
		minimum.set(copy.getMinimum());
	}

	@Override
	public PhysicalCashUnit310[] getPhysicalCashUnits() {
		return new PhysicalCashUnits310(physical, getNumPhysicalCUs()).get();
	}

	public int[] getNoteIds() {
		return new NoteIDs(noteIds).get();
	}

	public CashUnitType getCashUnitType() {
		return cdmType.get();
	}

	public Optional<String> getCashUnitName() {
		return Optional.ofNullable(cashUnitName.get());
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

	public long getMinimum() {
		return minimum.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.appendSuper(super.hashCode())
				.append(getNoteIds())
				.append(getCashUnitType())
				.append(getCashUnitName())
				.append(getInitialCount())
				.append(getDispensedCount())
				.append(getPresentedCount())
				.append(getRetractedCount())
				.append(getRejectCount())
				.append(getMinimum())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CashIn310) {
			CashIn310 cashIn310 = (CashIn310) obj;
			return new EqualsBuilder()
					.appendSuper(super.equals(obj))
					.append(getNoteIds(), cashIn310.getNoteIds())
					.append(getCashUnitType(), cashIn310.getCashUnitType())
					.append(getCashUnitName(), cashIn310.getCashUnitName())
					.append(getInitialCount(), cashIn310.getInitialCount())
					.append(getDispensedCount(), cashIn310.getDispensedCount())
					.append(getPresentedCount(), cashIn310.getPresentedCount())
					.append(getRetractedCount(), cashIn310.getRetractedCount())
					.append(getRejectCount(), cashIn310.getRejectCount())
					.append(getMinimum(), cashIn310.getMinimum())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.appendSuper(super.toString())
				.append("noteIds", getNoteIds())
				.append("cdmType", getCashUnitType())
				.append("cashUnitName", getCashUnitName())
				.append("initialCount", getInitialCount())
				.append("dispensedCount", getDispensedCount())
				.append("presentedCount", getPresentedCount())
				.append("retractedCount", getRetractedCount())
				.append("rejectCount", getRejectCount())
				.append("minimum", getMinimum())
				.toString();
	}
}
