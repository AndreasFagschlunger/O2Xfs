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

package at.o2xfs.xfs.v3_30.cdm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPWSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.xfs.win32.XfsCharArray;

public class BlacklistElement330 extends Struct {

	protected final LPWSTR serialNumber = new LPWSTR();
	protected final XfsCharArray currencyID = new XfsCharArray(3);
	protected final ULONG value = new ULONG();

	protected BlacklistElement330() {
		add(serialNumber);
		add(currencyID);
		add(value);
	}

	public BlacklistElement330(Pointer p) {
		this();
		assignBuffer(p);
	}

	public BlacklistElement330(BlacklistElement330 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(BlacklistElement330 copy) {
		serialNumber.set(copy.getSerialNumber());
		currencyID.set(copy.getCurrencyID());
		value.set(copy.getValue());
	}

	public String getSerialNumber() {
		return serialNumber.get();
	}

	public char[] getCurrencyID() {
		return currencyID.get();
	}

	public long getValue() {
		return value.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getSerialNumber()).append(getCurrencyID()).append(getValue()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BlacklistElement330) {
			BlacklistElement330 blacklistElement = (BlacklistElement330) obj;
			return new EqualsBuilder().append(getSerialNumber(), blacklistElement.getSerialNumber()).append(getCurrencyID(), blacklistElement.getCurrencyID())
					.append(getValue(), blacklistElement.getValue()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("serialNumber", getSerialNumber()).append("currencyID", getCurrencyID()).append("value", getValue()).toString();
	}
}
