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

package at.o2xfs.xfs.v3_30.idc;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.xfs.idc.AlternateInterface;
import at.o2xfs.xfs.idc.CVM;
import at.o2xfs.xfs.win32.XfsWord;

public class EmvClessOutcome330 extends Struct {

	protected final XfsWord<CVM> cvm = new XfsWord<>(CVM.class);
	protected final XfsWord<AlternateInterface> alternateInterface = new XfsWord<>(AlternateInterface.class);
	protected final BOOL receipt = new BOOL();
	protected final Pointer clessUIOutcome = new Pointer();
	protected final Pointer clessUIRestart = new Pointer();
	protected final ULONG clessFieldOffHoldTime = new ULONG();
	protected final ULONG cardRemovalTimeoutValue = new ULONG();
	protected final Pointer discretionaryData = new Pointer();

	protected EmvClessOutcome330() {
		add(cvm);
		add(alternateInterface);
		add(receipt);
		add(clessUIOutcome);
		add(clessUIRestart);
		add(clessFieldOffHoldTime);
		add(cardRemovalTimeoutValue);
		add(discretionaryData);
	}

	public EmvClessOutcome330(Pointer p) {
		this();
		assignBuffer(p);
	}

	public EmvClessOutcome330(EmvClessOutcome330 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(EmvClessOutcome330 copy) {
		cvm.set(copy.getCVM());
		alternateInterface.set(copy.getAlternateInterface());
		receipt.set(copy.isReceipt());
		clessUIOutcome.pointTo(new EmvClessUI330(copy.getClessUIOutcome()));
		clessUIRestart.pointTo(new EmvClessUI330(copy.getClessUIRestart()));
		clessFieldOffHoldTime.set(copy.getClessFieldOffHoldTime());
		cardRemovalTimeoutValue.set(copy.getCardRemovalTimeoutValue());
		discretionaryData.pointTo(new HexData330(copy.getDiscretionaryData()));
	}

	public CVM getCVM() {
		return cvm.get();
	}

	public AlternateInterface getAlternateInterface() {
		return alternateInterface.get();
	}

	public boolean isReceipt() {
		return receipt.get();
	}

	public EmvClessUI330 getClessUIOutcome() {
		return new EmvClessUI330(clessUIOutcome);
	}

	public EmvClessUI330 getClessUIRestart() {
		return new EmvClessUI330(clessUIRestart);
	}

	public long getClessFieldOffHoldTime() {
		return clessFieldOffHoldTime.get();
	}

	public long getCardRemovalTimeoutValue() {
		return cardRemovalTimeoutValue.get();
	}

	public byte[] getDiscretionaryData() {
		return new HexData330(discretionaryData).getData();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getCVM())
				.append(getAlternateInterface())
				.append(isReceipt())
				.append(getClessUIOutcome())
				.append(getClessUIRestart())
				.append(getClessFieldOffHoldTime())
				.append(getCardRemovalTimeoutValue())
				.append(getDiscretionaryData())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EmvClessOutcome330) {
			EmvClessOutcome330 emvClessOutcome330 = (EmvClessOutcome330) obj;
			return new EqualsBuilder()
					.append(getCVM(), emvClessOutcome330.getCVM())
					.append(getAlternateInterface(), emvClessOutcome330.getAlternateInterface())
					.append(isReceipt(), emvClessOutcome330.isReceipt())
					.append(getClessUIOutcome(), emvClessOutcome330.getClessUIOutcome())
					.append(getClessUIRestart(), emvClessOutcome330.getClessUIRestart())
					.append(getClessFieldOffHoldTime(), emvClessOutcome330.getClessFieldOffHoldTime())
					.append(getCardRemovalTimeoutValue(), emvClessOutcome330.getCardRemovalTimeoutValue())
					.append(getDiscretionaryData(), emvClessOutcome330.getDiscretionaryData())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("cvm", getCVM())
				.append("alternateInterface", getAlternateInterface())
				.append("receipt", isReceipt())
				.append("clessUIOutcome", getClessUIOutcome())
				.append("clessUIRestart", getClessUIRestart())
				.append("clessFieldOffHoldTime", getClessFieldOffHoldTime())
				.append("cardRemovalTimeoutValue", getCardRemovalTimeoutValue())
				.append("discretionaryData", getDiscretionaryData())
				.toString();
	}
}
