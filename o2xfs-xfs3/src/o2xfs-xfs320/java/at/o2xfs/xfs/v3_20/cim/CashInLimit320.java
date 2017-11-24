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

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;

public class CashInLimit320 extends Struct {

	protected final ULONG totalItemsLimit = new ULONG();
	protected final Pointer amountLimit = new Pointer();

	protected CashInLimit320() {
		add(totalItemsLimit);
		add(amountLimit);
	}

	public CashInLimit320(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CashInLimit320(CashInLimit320 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CashInLimit320 copy) {
		totalItemsLimit.set(copy.getTotalItemsLimit());
		Optional<AmountLimit320> amountLimit = copy.getAmountLimit();
		if (amountLimit.isPresent()) {
			this.amountLimit.pointTo(amountLimit.get());
		}
	}

	public long getTotalItemsLimit() {
		return totalItemsLimit.get();
	}

	public Optional<AmountLimit320> getAmountLimit() {
		Optional<AmountLimit320> result = Optional.empty();
		if (!Pointer.NULL.equals(amountLimit)) {
			result = Optional.of(new AmountLimit320(amountLimit));
		}
		return result;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getTotalItemsLimit()).append(getAmountLimit()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CashInLimit320) {
			CashInLimit320 cashInLimit320 = (CashInLimit320) obj;
			return new EqualsBuilder().append(getTotalItemsLimit(), cashInLimit320.getTotalItemsLimit()).append(getAmountLimit(), cashInLimit320.getAmountLimit()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("totalItemsLimit", getTotalItemsLimit()).append("amountLimit", getAmountLimit()).toString();
	}
}
