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

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cdm.Position;
import at.o2xfs.xfs.win32.XfsWord;

public class Dispense3 extends Struct {

	public static class Builder {

		private int tellerID = 0;
		private int mixNumber = 0;
		private Position position = Position.NULL;
		private boolean present = false;
		private Denomination3 denomination = null;

		public Builder tellerID(int tellerID) {
			this.tellerID = tellerID;
			return this;
		}

		public Builder mixNumber(int mixNumber) {
			this.mixNumber = mixNumber;
			return this;
		}

		public Builder position(Position position) {
			this.position = position;
			return this;
		}

		public Builder present(boolean present) {
			this.present = present;
			return this;
		}

		public Builder denomination(Denomination3 denomination) {
			this.denomination = denomination;
			return this;
		}

		public Dispense3 build() {
			return new Dispense3(this);
		}
	}

	protected final USHORT tellerID = new USHORT();
	protected final USHORT mixNumber = new USHORT();
	protected final XfsWord<Position> position = new XfsWord<>(Position.class);
	protected final BOOL present = new BOOL();
	protected final Pointer denomination = new Pointer();

	protected Dispense3() {
		add(tellerID);
		add(mixNumber);
		add(position);
		add(present);
		add(denomination);
	}

	protected Dispense3(Builder builder) {
		this();
		allocate();
		tellerID.set(builder.tellerID);
		mixNumber.set(builder.mixNumber);
		position.set(builder.position);
		present.set(builder.present);
		denomination.pointTo(builder.denomination);
	}

	public Dispense3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Dispense3(Dispense3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Dispense3 copy) {
		tellerID.set(copy.getTellerID());
		mixNumber.set(copy.getMixNumber());
		position.set(copy.getPosition());
		present.set(copy.isPresent());
		denomination.pointTo(copy.getDenomination());
	}

	public int getTellerID() {
		return tellerID.get();
	}

	public int getMixNumber() {
		return mixNumber.get();
	}

	public Position getPosition() {
		return position.get();
	}

	public boolean isPresent() {
		return present.get();
	}

	public Denomination3 getDenomination() {
		return new Denomination3(new Denomination3(denomination));
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getTellerID()).append(getMixNumber()).append(getPosition()).append(isPresent()).append(getDenomination()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Dispense3) {
			Dispense3 dispense3 = (Dispense3) obj;
			return new EqualsBuilder().append(getTellerID(), dispense3.getTellerID()).append(getMixNumber(), dispense3.getMixNumber())
					.append(getPosition(), dispense3.getPosition()).append(isPresent(), dispense3.isPresent()).append(getDenomination(), dispense3.getDenomination()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("tellerID", getTellerID()).append("mixNumber", getMixNumber()).append("position", getPosition()).append("present", isPresent())
				.append("denomination", getDenomination()).toString();
	}
}
