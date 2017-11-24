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

package at.o2xfs.xfs.v3_20.cim;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.cim.CashInLimit;
import at.o2xfs.xfs.cim.CountAction;
import at.o2xfs.xfs.cim.MixedMode;
import at.o2xfs.xfs.v3_10.cim.Capabilities310;
import at.o2xfs.xfs.win32.XfsWord;
import at.o2xfs.xfs.win32.XfsWordBitmask;

public class Capabilities320 extends Capabilities310 {

	protected final BOOL replenish = new BOOL();
	protected final XfsWordBitmask<CashInLimit> cashInLimit = new XfsWordBitmask<>(CashInLimit.class);
	protected final XfsWordBitmask<CountAction> countActions = new XfsWordBitmask<>(CountAction.class);
	protected final BOOL deviceLockControl = new BOOL();
	protected final XfsWord<MixedMode> mixedMode = new XfsWord<>(MixedMode.class);
	protected final BOOL mixedDepositAndRollback = new BOOL();
	protected final BOOL antiFraudModule = new BOOL();

	protected Capabilities320() {
		add(replenish);
		add(cashInLimit);
		add(countActions);
		add(deviceLockControl);
		add(mixedMode);
		add(mixedDepositAndRollback);
		add(antiFraudModule);
	}

	public Capabilities320(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Capabilities320(Capabilities320 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Capabilities320 copy) {
		super.set(copy);
		replenish.set(copy.isReplenish());
		cashInLimit.set(copy.getCashInLimit());
		countActions.set(copy.getCountActions());
		deviceLockControl.set(copy.isDeviceLockControl());
		mixedMode.set(copy.getMixedMode());
		mixedDepositAndRollback.set(copy.isMixedDepositAndRollback());
		antiFraudModule.set(copy.isAntiFraudModule());
	}

	public boolean isReplenish() {
		return replenish.get();
	}

	public Set<CashInLimit> getCashInLimit() {
		return cashInLimit.get();
	}

	public Set<CountAction> getCountActions() {
		return countActions.get();
	}

	public boolean isDeviceLockControl() {
		return deviceLockControl.get();
	}

	public MixedMode getMixedMode() {
		return mixedMode.get();
	}

	public boolean isMixedDepositAndRollback() {
		return mixedDepositAndRollback.get();
	}

	public boolean isAntiFraudModule() {
		return antiFraudModule.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(isReplenish()).append(getCashInLimit()).append(getCountActions()).append(isDeviceLockControl())
				.append(getMixedMode()).append(isMixedDepositAndRollback()).append(isAntiFraudModule()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Capabilities320) {
			Capabilities320 capabilities320 = (Capabilities320) obj;
			return new EqualsBuilder().appendSuper(super.equals(obj)).append(isReplenish(), capabilities320.isReplenish())
					.append(getCashInLimit(), capabilities320.getCashInLimit()).append(getCountActions(), capabilities320.getCountActions())
					.append(isDeviceLockControl(), capabilities320.isDeviceLockControl()).append(getMixedMode(), capabilities320.getMixedMode())
					.append(isMixedDepositAndRollback(), capabilities320.isMixedDepositAndRollback()).append(isAntiFraudModule(), capabilities320.isAntiFraudModule()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("replenish", isReplenish()).append("cashInLimit", getCashInLimit())
				.append("countActions", getCountActions()).append("deviceLockControl", isDeviceLockControl()).append("mixedMode", getMixedMode())
				.append("mixedDepositAndRollback", isMixedDepositAndRollback()).append("antiFraudModule", isAntiFraudModule()).toString();
	}
}
