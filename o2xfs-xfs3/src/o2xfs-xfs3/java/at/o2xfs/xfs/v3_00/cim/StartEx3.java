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
import at.o2xfs.win32.UShortArray;
import at.o2xfs.xfs.cim.ExchangeType;
import at.o2xfs.xfs.win32.XfsWord;

public class StartEx3 extends Struct {

	protected final XfsWord<ExchangeType> exchangeType = new XfsWord<>(ExchangeType.class);
	protected final USHORT tellerID = new USHORT();
	protected final USHORT count = new USHORT();
	protected final Pointer cUNumList = new Pointer();
	protected final Pointer output = new Pointer();

	protected StartEx3() {
		add(exchangeType);
		add(tellerID);
		add(count);
		add(cUNumList);
		add(output);
	}

	public StartEx3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public StartEx3(StartEx3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(StartEx3 copy) {
		exchangeType.set(copy.getExchangeType());
		tellerID.set(copy.getTellerID());
		count.set(copy.getCount());
		cUNumList.pointTo(new UShortArray(copy.getCUNumList()));
		output.pointTo(new Output3(copy.getOutput()));
	}

	public ExchangeType getExchangeType() {
		return exchangeType.get();
	}

	public int getTellerID() {
		return tellerID.get();
	}

	public int getCount() {
		return count.get();
	}

	public int[] getCUNumList() {
		return new UShortArray(cUNumList, count.intValue()).get();
	}

	public Output3 getOutput() {
		return new Output3(output);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getExchangeType()).append(getTellerID()).append(getCount()).append(getCUNumList()).append(getOutput()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StartEx3) {
			StartEx3 startEx3 = (StartEx3) obj;
			return new EqualsBuilder().append(getExchangeType(), startEx3.getExchangeType()).append(getTellerID(), startEx3.getTellerID()).append(getCount(), startEx3.getCount())
					.append(getCUNumList(), startEx3.getCUNumList()).append(getOutput(), startEx3.getOutput()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("exchangeType", getExchangeType()).append("tellerID", getTellerID()).append("count", getCount()).append("cUNumList", getCUNumList())
				.append("output", getOutput()).toString();
	}
}
