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
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cdm.InputPosition;
import at.o2xfs.xfs.cdm.Position;
import at.o2xfs.xfs.win32.XfsDWord;
import at.o2xfs.xfs.win32.XfsWord;

public class TellerDetails3 extends Struct {

	protected final USHORT tellerID = new USHORT();
	protected final XfsDWord<InputPosition> inputPosition = new XfsDWord<>(InputPosition.class);
	protected final XfsWord<Position> outputPosition = new XfsWord<>(Position.class);
	protected final Pointer tellerTotals = new Pointer();

	protected TellerDetails3() {
		add(tellerID);
		add(inputPosition);
		add(outputPosition);
		add(tellerTotals);
	}

	public TellerDetails3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public TellerDetails3(TellerDetails3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(TellerDetails3 copy) {
		tellerID.set(copy.getTellerID());
		inputPosition.set(copy.getInputPosition());
		outputPosition.set(copy.getOutputPosition());
		tellerTotals.pointTo(new TellerTotals3Array(copy.getTellerTotals()));
	}

	public int getTellerID() {
		return tellerID.get();
	}

	public InputPosition getInputPosition() {
		return inputPosition.get();
	}

	public Position getOutputPosition() {
		return outputPosition.get();
	}

	public TellerTotals3[] getTellerTotals() {
		return new TellerTotals3Array(tellerTotals).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getTellerID()).append(getInputPosition()).append(getOutputPosition()).append(getTellerTotals()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TellerDetails3) {
			TellerDetails3 tellerDetails3 = (TellerDetails3) obj;
			return new EqualsBuilder().append(getTellerID(), tellerDetails3.getTellerID()).append(getInputPosition(), tellerDetails3.getInputPosition())
					.append(getOutputPosition(), tellerDetails3.getOutputPosition()).append(getTellerTotals(), tellerDetails3.getTellerTotals()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("tellerID", getTellerID()).append("inputPosition", getInputPosition()).append("outputPosition", getOutputPosition())
				.append("tellerTotals", getTellerTotals()).toString();
	}
}
