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

import at.o2xfs.xfs.XfsConstant;
import at.o2xfs.xfs.util.XfsConstants;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SIUDoorsStatus {

	private final int[] doors;

	public SIUDoorsStatus(final int[] doors) {
		if (doors == null) {
			throw new IllegalArgumentException("doors must not be null");
		} else if (doors.length != SIUConstant.DOORS_SIZE) {
			throw new IllegalArgumentException("doors.length != " + SIUConstant.DOORS_SIZE);
		}
		this.doors = doors;
	}

	private <E extends Enum<E> & XfsConstant> E getState(final SIUDoor door, final Class<E> type) {
		final int value = doors[(int) door.getValue()];
		return XfsConstants.valueOf(value, type);
	}

	public SIUCabinetDoorsState getCabinetDoorsState() {
		return getState(SIUDoor.CABINET, SIUCabinetDoorsState.class);
	}

	public SIUSafeDoorsState getSafeDoorsState() {
		return getState(SIUDoor.SAFE, SIUSafeDoorsState.class);
	}

	public SIUVandalShieldState getVandalShieldState() {
		return getState(SIUDoor.VANDALSHIELD, SIUVandalShieldState.class);
	}

	public SIUCabinetDoorsState getFrontCabinetDoorsState() {
		return getState(SIUDoor.CABINET_FRONT, SIUCabinetDoorsState.class);
	}

	public SIUCabinetDoorsState getRearCabinetDoorsState() {
		return getState(SIUDoor.CABINET_REAR, SIUCabinetDoorsState.class);
	}

	public SIUCabinetDoorsState getLeftCabinetDoorsState() {
		return getState(SIUDoor.CABINET_LEFT, SIUCabinetDoorsState.class);
	}

	public SIUCabinetDoorsState getRightCabinetDoorsState() {
		return getState(SIUDoor.CABINET_RIGHT, SIUCabinetDoorsState.class);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("cabinetDoorsState", getCabinetDoorsState())
										.append("safeDoorsState", getSafeDoorsState())
										.append("vandalShieldState", getVandalShieldState())
										.append("frontCabinetDoorsState", getFrontCabinetDoorsState())
										.append("rearCabinetDoorsState", getRearCabinetDoorsState())
										.append("leftCabinetDoorsState", getLeftCabinetDoorsState())
										.append("rearCabinetDoorsState", getRearCabinetDoorsState())
										.toString();
	}

}