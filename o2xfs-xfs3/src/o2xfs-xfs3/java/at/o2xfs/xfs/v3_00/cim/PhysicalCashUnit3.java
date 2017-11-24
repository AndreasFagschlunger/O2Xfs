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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.xfs.XfsExtra;
import at.o2xfs.xfs.cim.CashUnitStatus;
import at.o2xfs.xfs.win32.XfsCharArray;
import at.o2xfs.xfs.win32.XfsWord;

public class PhysicalCashUnit3 extends Struct {

	public static class Builder {

		private final String physicalPositionName;
		private final char[] unitID;
		protected long cashInCount;
		protected long count;
		protected long maximum;
		private final CashUnitStatus status;
		protected boolean hardwareSensors;
		protected Map<String, String> extra;

		public Builder(String physicalPositionName, char[] unitID, CashUnitStatus status) {
			this.physicalPositionName = physicalPositionName;
			this.unitID = unitID;
			cashInCount = 0L;
			count = 0L;
			maximum = 0L;
			this.status = status;
			hardwareSensors = false;
			extra = Collections.emptyMap();
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

		public Builder hardwareSensors(boolean hardwareSensors) {
			this.hardwareSensors = hardwareSensors;
			return this;
		}

		public Builder extra(Map<String, String> extra) {
			this.extra = extra;
			return this;
		}

		public PhysicalCashUnit3 build() {
			return new PhysicalCashUnit3(this);
		}
	}

	protected final LPSTR physicalPositionName = new LPSTR();
	protected final XfsCharArray unitID = new XfsCharArray(5);
	protected final ULONG cashInCount = new ULONG();
	protected final ULONG count = new ULONG();
	protected final ULONG maximum = new ULONG();
	protected final XfsWord<CashUnitStatus> status = new XfsWord<>(CashUnitStatus.class);
	protected final BOOL hardwareSensors = new BOOL();
	protected final XfsExtra extra = new XfsExtra();

	protected PhysicalCashUnit3() {
		add(physicalPositionName);
		add(unitID);
		add(cashInCount);
		add(count);
		add(maximum);
		add(status);
		add(hardwareSensors);
		add(extra);
	}

	protected PhysicalCashUnit3(Builder builder) {
		this();
		allocate();
		set(builder);
	}

	public PhysicalCashUnit3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public PhysicalCashUnit3(PhysicalCashUnit3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Builder builder) {
		physicalPositionName.set(builder.physicalPositionName);
		unitID.set(builder.unitID);
		cashInCount.set(builder.cashInCount);
		count.set(builder.count);
		maximum.set(builder.maximum);
		status.set(builder.status);
		hardwareSensors.set(builder.hardwareSensors);
		extra.set(builder.extra);
	}

	protected void set(PhysicalCashUnit3 copy) {
		physicalPositionName.set(copy.getPhysicalPositionName());
		unitID.set(copy.getUnitID());
		cashInCount.set(copy.getCashInCount());
		count.set(copy.getCount());
		maximum.set(copy.getMaximum());
		status.set(copy.getStatus());
		hardwareSensors.set(copy.isHardwareSensors());
		extra.set(copy.getExtra());
	}

	public String getPhysicalPositionName() {
		return physicalPositionName.get();
	}

	public char[] getUnitID() {
		return unitID.get();
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

	public boolean isHardwareSensors() {
		return hardwareSensors.get();
	}

	public Map<String, String> getExtra() {
		return extra.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getPhysicalPositionName())
				.append(getUnitID())
				.append(getCashInCount())
				.append(getCount())
				.append(getMaximum())
				.append(getStatus())
				.append(isHardwareSensors())
				.append(getExtra())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PhysicalCashUnit3) {
			PhysicalCashUnit3 physicalCashUnit3 = (PhysicalCashUnit3) obj;
			return new EqualsBuilder()
					.append(getPhysicalPositionName(), physicalCashUnit3.getPhysicalPositionName())
					.append(getUnitID(), physicalCashUnit3.getUnitID())
					.append(getCashInCount(), physicalCashUnit3.getCashInCount())
					.append(getCount(), physicalCashUnit3.getCount())
					.append(getMaximum(), physicalCashUnit3.getMaximum())
					.append(getStatus(), physicalCashUnit3.getStatus())
					.append(isHardwareSensors(), physicalCashUnit3.isHardwareSensors())
					.append(getExtra(), physicalCashUnit3.getExtra())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("physicalPositionName", getPhysicalPositionName())
				.append("unitID", getUnitID())
				.append("cashInCount", getCashInCount())
				.append("count", getCount())
				.append("maximum", getMaximum())
				.append("pStatus", getStatus())
				.append("hardwareSensors", isHardwareSensors())
				.append("extra", getExtra())
				.toString();
	}
}
