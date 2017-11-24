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

package at.o2xfs.xfs.v3_20.idc;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.idc.Direction;
import at.o2xfs.xfs.win32.XfsWord;

public class ParkCard320 extends Struct {

	protected final XfsWord<Direction> direction = new XfsWord<>(Direction.class);
	protected final USHORT parkingStation = new USHORT();

	protected ParkCard320() {
		add(direction);
		add(parkingStation);
	}

	public ParkCard320(Pointer p) {
		this();
		assignBuffer(p);
	}

	public ParkCard320(ParkCard320 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(ParkCard320 copy) {
		direction.set(copy.getDirection());
		parkingStation.set(copy.getParkingStation());
	}

	public Direction getDirection() {
		return direction.get();
	}

	public int getParkingStation() {
		return parkingStation.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getDirection()).append(getParkingStation()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ParkCard320) {
			ParkCard320 parkCard320 = (ParkCard320) obj;
			return new EqualsBuilder()
					.append(getDirection(), parkCard320.getDirection())
					.append(getParkingStation(), parkCard320.getParkingStation())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("direction", getDirection())
				.append("parkingStation", getParkingStation())
				.toString();
	}
}
