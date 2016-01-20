/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.xfs.siu;

import at.o2xfs.xfs.util.XfsConstants;

import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SIUDoorCapabilities {

	private final int[] doorsCapabilities;

	public SIUDoorCapabilities(final int[] doorsCapabilities) {
		this.doorsCapabilities = doorsCapabilities;
	}

	private Set<SIUDoorCapability> getDoorCapabilities(final SIUDoor door) {
		return XfsConstants.of(doorsCapabilities[(int) door.getValue()], SIUDoorCapability.class);
	}

	public Set<SIUDoorCapability> getCabinetDoors() {
		return getDoorCapabilities(SIUDoor.CABINET);
	}

	public Set<SIUDoorCapability> getSafeDoors() {
		return getDoorCapabilities(SIUDoor.SAFE);
	}

	public Set<SIUVandalShieldCapability> getVandalShield() {
		return XfsConstants.of(	doorsCapabilities[(int) SIUDoor.VANDALSHIELD.getValue()],
								SIUVandalShieldCapability.class);
	}

	public Set<SIUDoorCapability> getFrontCabinetDoors() {
		return getDoorCapabilities(SIUDoor.CABINET_FRONT);
	}

	public Set<SIUDoorCapability> getRearCabinetDoors() {
		return getDoorCapabilities(SIUDoor.CABINET_REAR);
	}

	public Set<SIUDoorCapability> getLeftCabinetDoors() {
		return getDoorCapabilities(SIUDoor.CABINET_LEFT);
	}

	public Set<SIUDoorCapability> getRightCabinetDoors() {
		return getDoorCapabilities(SIUDoor.CABINET_RIGHT);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("cabinetDoors", getCabinetDoors())
										.append("safeDoors", getSafeDoors())
										.append("vandalShield", getVandalShield())
										.append("frontCabinetDoors", getFrontCabinetDoors())
										.append("rearCabinetDoors", getRearCabinetDoors())
										.append("leftCabinetDoors", getLeftCabinetDoors())
										.append("rightCabinetDoors", getRightCabinetDoors())
										.toString();
	}
}