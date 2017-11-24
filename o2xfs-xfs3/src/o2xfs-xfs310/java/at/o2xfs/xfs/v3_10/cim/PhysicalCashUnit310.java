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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.ULONG;
import at.o2xfs.xfs.cim.CashUnitStatus;
import at.o2xfs.xfs.v3_00.cim.PhysicalCashUnit3;

public class PhysicalCashUnit310 extends PhysicalCashUnit3 {

	public static class Builder extends PhysicalCashUnit3.Builder {

		private long initialCount;
		private long dispensedCount;
		private long presentedCount;
		private long retractedCount;
		private long rejectCount;

		public Builder(String physicalPositionName, char[] unitID, CashUnitStatus status) {
			super(physicalPositionName, unitID, status);
		}

		@Override
		public Builder cashInCount(long cashInCount) {
			this.cashInCount = cashInCount;
			return this;
		}

		@Override
		public Builder count(long count) {
			this.count = count;
			return this;
		}

		@Override
		public Builder maximum(long maximum) {
			this.maximum = maximum;
			return this;
		}

		@Override
		public Builder hardwareSensors(boolean hardwareSensors) {
			this.hardwareSensors = hardwareSensors;
			return this;
		}

		@Override
		public Builder extra(Map<String, String> extra) {
			this.extra = extra;
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

		@Override
		public PhysicalCashUnit310 build() {
			return new PhysicalCashUnit310(this);
		}
	}

	protected final ULONG initialCount = new ULONG();
	protected final ULONG dispensedCount = new ULONG();
	protected final ULONG presentedCount = new ULONG();
	protected final ULONG retractedCount = new ULONG();
	protected final ULONG rejectCount = new ULONG();

	protected PhysicalCashUnit310() {
		add(initialCount);
		add(dispensedCount);
		add(presentedCount);
		add(retractedCount);
		add(rejectCount);
	}

	protected PhysicalCashUnit310(Builder builder) {
		this();
		allocate();
		super.set(builder);
		initialCount.set(builder.initialCount);
		dispensedCount.set(builder.dispensedCount);
		presentedCount.set(builder.presentedCount);
		retractedCount.set(builder.retractedCount);
		rejectCount.set(builder.rejectCount);
	}

	public PhysicalCashUnit310(Pointer p) {
		this();
		assignBuffer(p);
	}

	public PhysicalCashUnit310(PhysicalCashUnit310 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(PhysicalCashUnit310 copy) {
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
		return new HashCodeBuilder()
				.appendSuper(super.hashCode())
				.append(getInitialCount())
				.append(getDispensedCount())
				.append(getPresentedCount())
				.append(getRetractedCount())
				.append(getRejectCount())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PhysicalCashUnit310) {
			PhysicalCashUnit310 physicalCashUnit310 = (PhysicalCashUnit310) obj;
			return new EqualsBuilder()
					.appendSuper(super.equals(obj))
					.append(getInitialCount(), physicalCashUnit310.getInitialCount())
					.append(getDispensedCount(), physicalCashUnit310.getDispensedCount())
					.append(getPresentedCount(), physicalCashUnit310.getPresentedCount())
					.append(getRetractedCount(), physicalCashUnit310.getRetractedCount())
					.append(getRejectCount(), physicalCashUnit310.getRejectCount())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.appendSuper(super.toString())
				.append("initialCount", getInitialCount())
				.append("dispensedCount", getDispensedCount())
				.append("presentedCount", getPresentedCount())
				.append("retractedCount", getRetractedCount())
				.append("rejectCount", getRejectCount())
				.toString();
	}
}
