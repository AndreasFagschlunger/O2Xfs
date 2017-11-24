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
import at.o2xfs.win32.SHORT;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.win32.XfsCharArray;

public class CurrencyExp3 extends Struct {

	protected final XfsCharArray currencyID = new XfsCharArray(3);
	protected final SHORT exponent = new SHORT();

	protected CurrencyExp3() {
		add(currencyID);
		add(exponent);
	}

	public CurrencyExp3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CurrencyExp3(CurrencyExp3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CurrencyExp3 copy) {
		currencyID.set(copy.getCurrencyID());
		exponent.set(copy.getExponent());
	}

	public char[] getCurrencyID() {
		return currencyID.get();
	}

	public short getExponent() {
		return exponent.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getCurrencyID()).append(getExponent()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CurrencyExp3) {
			CurrencyExp3 currencyExp3 = (CurrencyExp3) obj;
			return new EqualsBuilder().append(getCurrencyID(), currencyExp3.getCurrencyID()).append(getExponent(), currencyExp3.getExponent()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("currencyID", getCurrencyID()).append("exponent", getExponent()).toString();
	}
}
