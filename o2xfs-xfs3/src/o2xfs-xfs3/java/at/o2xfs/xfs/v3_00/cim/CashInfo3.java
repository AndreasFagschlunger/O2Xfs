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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;

public class CashInfo3 extends Struct {

	public static class Builder {

		private final CashIn3[] cashIn;

		public Builder(CashIn3[] cashIn) {
			this.cashIn = cashIn;
		}

		public CashInfo3 build() {
			return new CashInfo3(this);
		}
	}

	protected final USHORT count = new USHORT();
	protected final Pointer cashIn = new Pointer();

	protected CashInfo3() {
		add(count);
		add(cashIn);
	}

	protected CashInfo3(Builder builder) {
		this();
		allocate();
		count.set(builder.cashIn.length);
		cashIn.pointTo(new CashIn3Array(builder.cashIn));
	}

	public CashInfo3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CashInfo3(CashInfo3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CashInfo3 copy) {
		count.set(copy.getCount());
		cashIn.pointTo(new CashIn3Array(copy.getCashIn()));
	}

	public int getCount() {
		return count.get();
	}

	public CashIn3[] getCashIn() {
		return new CashIn3Array(cashIn, getCount()).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getCount()).append(getCashIn()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CashInfo3) {
			CashInfo3 cashInfo3 = (CashInfo3) obj;
			return new EqualsBuilder()
					.append(getCount(), cashInfo3.getCount())
					.append(getCashIn(), cashInfo3.getCashIn())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("count", getCount()).append("cashIn", getCashIn()).toString();
	}
}
