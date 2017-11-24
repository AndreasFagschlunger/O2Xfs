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

import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.XfsExtra;
import at.o2xfs.xfs.cdm.CdmDeviceState;
import at.o2xfs.xfs.cdm.Dispenser;
import at.o2xfs.xfs.cdm.IntermediateStacker;
import at.o2xfs.xfs.cdm.SafeDoor;
import at.o2xfs.xfs.win32.XfsWord;

public class CdmStatus3 extends Struct {

	protected final XfsWord<CdmDeviceState> deviceState = new XfsWord<>(CdmDeviceState.class);
	protected final XfsWord<SafeDoor> safeDoor = new XfsWord<>(SafeDoor.class);
	protected final XfsWord<Dispenser> dispenser = new XfsWord<>(Dispenser.class);
	protected final XfsWord<IntermediateStacker> intermediateStacker = new XfsWord<>(IntermediateStacker.class);
	protected final Pointer positions = new Pointer();
	protected final XfsExtra extra = new XfsExtra();

	protected CdmStatus3() {
		add(deviceState);
		add(safeDoor);
		add(dispenser);
		add(intermediateStacker);
		add(positions);
		add(extra);
	}

	public CdmStatus3(Pointer p) {
		this();
		assignBuffer(p);
	}

	protected CdmStatus3(CdmStatus3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CdmStatus3 copy) {
		deviceState.set(copy.getDeviceState());
		safeDoor.set(copy.getSafeDoor());
		dispenser.set(copy.getDispenser());
		intermediateStacker.set(copy.getIntermediateStacker());
		positions.pointTo(new OutputPositions3(copy.getPositions()));
		extra.set(copy.getExtra());
	}

	public CdmDeviceState getDeviceState() {
		return deviceState.get();
	}

	public SafeDoor getSafeDoor() {
		return safeDoor.get();
	}

	public Dispenser getDispenser() {
		return dispenser.get();
	}

	public IntermediateStacker getIntermediateStacker() {
		return intermediateStacker.get();
	}

	public OutputPosition3[] getPositions() {
		return new OutputPositions3(positions).get();
	}

	public Map<String, String> getExtra() {
		return extra.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getDeviceState()).append(getSafeDoor()).append(getDispenser()).append(getIntermediateStacker()).append(getPositions())
				.append(getExtra()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CdmStatus3) {
			CdmStatus3 status = (CdmStatus3) obj;
			return new EqualsBuilder().append(getDeviceState(), status.getDeviceState()).append(getSafeDoor(), status.getSafeDoor()).append(getDispenser(), status.getDispenser())
					.append(getIntermediateStacker(), status.getIntermediateStacker()).append(getPositions(), status.getPositions()).append(getExtra(), status.getExtra())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("deviceState", getDeviceState()).append("safeDoor", getSafeDoor()).append("dispenser", getDispenser())
				.append("intermediateStacker", getIntermediateStacker()).append("positions", getPositions()).append("extra", getExtra()).toString();
	}
}
