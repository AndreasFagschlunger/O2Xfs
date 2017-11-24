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

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.cdm.Failure;
import at.o2xfs.xfs.v3_00.cdm.CashUnit3;
import at.o2xfs.xfs.v3_00.cdm.CashUnitError3;

public class CashUnitError310 extends CashUnitError3 {

	public CashUnitError310(Pointer p) {
		super(p);
	}

	public CashUnitError310(CashUnitError310 copy) {
		super();
		allocate();
		set(copy);
	}

	protected void set(CashUnitError310 copy) {
		failure.set(copy.getFailure());
		Optional<CashUnit3> cashUnit = copy.getCashUnit();
		if (cashUnit.isPresent()) {
			this.cashUnit.pointTo(cashUnit.get());
		}
	}

	@Override
	public Failure getFailure() {
		return failure.get();
	}

	@Override
	public Optional<CashUnit3> getCashUnit() {
		Optional<CashUnit3> result = Optional.empty();
		if (!Pointer.NULL.equals(cashUnit)) {
			result = Optional.of(new CashUnit310(new CashUnit310(cashUnit)));
		}
		return result;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getFailure()).append(getCashUnit()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CashUnitError310) {
			CashUnitError310 cashUnitError = (CashUnitError310) obj;
			return new EqualsBuilder().append(getFailure(), cashUnitError.getFailure()).append(getCashUnit(), cashUnitError.getCashUnit()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("failure", getFailure()).append("cashUnit", getCashUnit()).toString();
	}
}
