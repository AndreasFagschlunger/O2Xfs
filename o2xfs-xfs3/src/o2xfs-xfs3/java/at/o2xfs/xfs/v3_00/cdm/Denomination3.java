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

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.win32.XfsCharArray;
import at.o2xfs.xfs.win32.XfsUIntArray;

public class Denomination3 extends Struct {

	public static class Builder {
		private char[] currencyID = new char[] { ' ', ' ', ' ' };
		private long amount = 0L;
		private Optional<long[]> values = Optional.empty();
		private long cashBox = 0L;

		public Builder currencyID(char[] currencyID) {
			this.currencyID = Arrays.copyOf(currencyID, currencyID.length);
			return this;
		}

		public Builder amount(long amount) {
			this.amount = amount;
			return this;
		}

		public Builder values(long values[]) {
			this.values = Optional.of(Arrays.copyOf(values, values.length));
			return this;
		}

		public Builder cashBox(long cashBox) {
			this.cashBox = cashBox;
			return this;
		}

		public Denomination3 build() {
			return new Denomination3(this);
		}
	}

	protected final XfsCharArray currencyID = new XfsCharArray(3);
	protected final ULONG amount = new ULONG();
	protected final USHORT count = new USHORT();
	protected final Pointer values = new Pointer();
	protected final ULONG cashBox = new ULONG();

	protected Denomination3() {
		add(currencyID);
		add(amount);
		add(count);
		add(values);
		add(cashBox);
	}

	public Denomination3(Builder builder) {
		this();
		allocate();
		currencyID.set(builder.currencyID);
		amount.set(builder.amount);
		if (builder.values.isPresent()) {
			count.set(builder.values.get().length);
			values.pointTo(new XfsUIntArray(builder.values.get()));
		}
		cashBox.set(builder.cashBox);
	}

	public Denomination3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Denomination3(Denomination3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Denomination3 copy) {
		currencyID.set(copy.getCurrencyID());
		amount.set(copy.getAmount());
		count.set(copy.getCount());
		Optional<long[]> values = copy.getValues();
		if (values.isPresent()) {
			this.values.pointTo(new XfsUIntArray(values.get()));
		}
		cashBox.set(copy.getCashBox());
	}

	public char[] getCurrencyID() {
		return currencyID.get();
	}

	public long getAmount() {
		return amount.get();
	}

	public int getCount() {
		return count.get();
	}

	public Optional<long[]> getValues() {
		Optional<long[]> result = Optional.empty();
		if (!Pointer.NULL.equals(values)) {
			result = Optional.of(new XfsUIntArray(values, getCount()).get());
		}
		return result;
	}

	public long getCashBox() {
		return cashBox.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getCurrencyID()).append(getAmount()).append(getCount()).append(getValues()).append(getCashBox()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Denomination3) {
			Denomination3 denomination3 = (Denomination3) obj;
			return new EqualsBuilder().append(getCurrencyID(), denomination3.getCurrencyID()).append(getAmount(), denomination3.getAmount())
					.append(getCount(), denomination3.getCount()).append(getValues().orElse(null), denomination3.getValues().orElse(null))
					.append(getCashBox(), denomination3.getCashBox()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("currencyID", getCurrencyID()).append("amount", getAmount()).append("count", getCount()).append("values", getValues().orElse(null))
				.append("cashBox", getCashBox()).toString();
	}
}
