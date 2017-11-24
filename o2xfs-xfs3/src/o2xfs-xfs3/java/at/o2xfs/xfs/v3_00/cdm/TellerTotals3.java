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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.xfs.win32.XfsCharArray;

public class TellerTotals3 extends Struct {

	protected final XfsCharArray currencyID = new XfsCharArray(3);
	protected final ULONG itemsReceived = new ULONG();
	protected final ULONG itemsDispensed = new ULONG();
	protected final ULONG coinsReceived = new ULONG();
	protected final ULONG coinsDispensed = new ULONG();
	protected final ULONG cashBoxReceived = new ULONG();
	protected final ULONG cashBoxDispensed = new ULONG();

	protected TellerTotals3() {
		add(currencyID);
		add(itemsReceived);
		add(itemsDispensed);
		add(coinsReceived);
		add(coinsDispensed);
		add(cashBoxReceived);
		add(cashBoxDispensed);
	}

	public TellerTotals3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public TellerTotals3(TellerTotals3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(TellerTotals3 copy) {
		currencyID.set(copy.getCurrencyID());
		itemsReceived.set(copy.getItemsReceived());
		itemsDispensed.set(copy.getItemsDispensed());
		coinsReceived.set(copy.getCoinsReceived());
		coinsDispensed.set(copy.getCoinsDispensed());
		cashBoxReceived.set(copy.getCashBoxReceived());
		cashBoxDispensed.set(copy.getCashBoxDispensed());
	}

	public char[] getCurrencyID() {
		return currencyID.get();
	}

	public long getItemsReceived() {
		return itemsReceived.get();
	}

	public long getItemsDispensed() {
		return itemsDispensed.get();
	}

	public long getCoinsReceived() {
		return coinsReceived.get();
	}

	public long getCoinsDispensed() {
		return coinsDispensed.get();
	}

	public long getCashBoxReceived() {
		return cashBoxReceived.get();
	}

	public long getCashBoxDispensed() {
		return cashBoxDispensed.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getCurrencyID()).append(getItemsReceived()).append(getItemsDispensed()).append(getCoinsReceived()).append(getCoinsDispensed())
				.append(getCashBoxReceived()).append(getCashBoxDispensed()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TellerTotals3) {
			TellerTotals3 tellerTotals3 = (TellerTotals3) obj;
			return new EqualsBuilder().append(getCurrencyID(), tellerTotals3.getCurrencyID()).append(getItemsReceived(), tellerTotals3.getItemsReceived())
					.append(getItemsDispensed(), tellerTotals3.getItemsDispensed()).append(getCoinsReceived(), tellerTotals3.getCoinsReceived())
					.append(getCoinsDispensed(), tellerTotals3.getCoinsDispensed()).append(getCashBoxReceived(), tellerTotals3.getCashBoxReceived())
					.append(getCashBoxDispensed(), tellerTotals3.getCashBoxDispensed()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("currencyID", getCurrencyID()).append("itemsReceived", getItemsReceived()).append("itemsDispensed", getItemsDispensed())
				.append("coinsReceived", getCoinsReceived()).append("coinsDispensed", getCoinsDispensed()).append("cashBoxReceived", getCashBoxReceived())
				.append("cashBoxDispensed", getCashBoxDispensed()).toString();
	}
}
