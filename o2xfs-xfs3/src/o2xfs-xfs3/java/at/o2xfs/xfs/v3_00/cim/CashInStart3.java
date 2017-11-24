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

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cim.Position;
import at.o2xfs.xfs.win32.XfsWord;

public class CashInStart3 extends Struct {

	public static class Builder {

		private int tellerId;
		private boolean useRecycleUnits;
		private Position outputPosition;
		private Position inputPosition;

		public Builder() {
			tellerId = 0;
			useRecycleUnits = false;
			outputPosition = Position.NULL;
			inputPosition = Position.NULL;
		}

		public Builder tellerId(int tellerId) {
			this.tellerId = tellerId;
			return this;
		}

		public Builder useRecycleUnits(boolean useRecycleUnits) {
			this.useRecycleUnits = useRecycleUnits;
			return this;
		}

		public Builder outputPosition(Position outputPosition) {
			this.outputPosition = outputPosition;
			return this;
		}

		public Builder inputPosition(Position inputPosition) {
			this.inputPosition = inputPosition;
			return this;
		}

		public CashInStart3 build() {
			return new CashInStart3(this);
		}
	}

	protected final USHORT tellerID = new USHORT();
	protected final BOOL useRecycleUnits = new BOOL();
	protected final XfsWord<Position> outputPosition = new XfsWord<>(Position.class);
	protected final XfsWord<Position> inputPosition = new XfsWord<>(Position.class);

	protected CashInStart3() {
		add(tellerID);
		add(useRecycleUnits);
		add(outputPosition);
		add(inputPosition);
	}

	protected CashInStart3(Builder builder) {
		this();
		allocate();
		tellerID.set(builder.tellerId);
		useRecycleUnits.set(builder.useRecycleUnits);
		outputPosition.set(builder.outputPosition);
		inputPosition.set(builder.inputPosition);
	}

	public CashInStart3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CashInStart3(CashInStart3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CashInStart3 copy) {
		tellerID.set(copy.getTellerID());
		useRecycleUnits.set(copy.isUseRecycleUnits());
		outputPosition.set(copy.getOutputPosition());
		inputPosition.set(copy.getInputPosition());
	}

	public int getTellerID() {
		return tellerID.get();
	}

	public boolean isUseRecycleUnits() {
		return useRecycleUnits.get();
	}

	public Position getOutputPosition() {
		return outputPosition.get();
	}

	public Position getInputPosition() {
		return inputPosition.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getTellerID())
				.append(isUseRecycleUnits())
				.append(getOutputPosition())
				.append(getInputPosition())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CashInStart3) {
			CashInStart3 cashInStart3 = (CashInStart3) obj;
			return new EqualsBuilder()
					.append(getTellerID(), cashInStart3.getTellerID())
					.append(isUseRecycleUnits(), cashInStart3.isUseRecycleUnits())
					.append(getOutputPosition(), cashInStart3.getOutputPosition())
					.append(getInputPosition(), cashInStart3.getInputPosition())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("tellerID", getTellerID())
				.append("useRecycleUnits", isUseRecycleUnits())
				.append("outputPosition", getOutputPosition())
				.append("inputPosition", getInputPosition())
				.toString();
	}
}
