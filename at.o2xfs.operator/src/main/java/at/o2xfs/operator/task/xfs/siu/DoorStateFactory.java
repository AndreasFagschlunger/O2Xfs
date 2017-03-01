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

package at.o2xfs.operator.task.xfs.siu;

import at.o2xfs.xfs.siu.SIUCabinetDoorsState;
import at.o2xfs.xfs.siu.SIUDoor;
import at.o2xfs.xfs.siu.SIUSafeDoorsState;
import at.o2xfs.xfs.siu.SIUVandalShieldState;
import at.o2xfs.xfs.util.XfsConstants;

class DoorStateFactory implements StateFactory<SIUDoor> {

	@Override
	public Object createState(final SIUDoor door, final int portStatus) {
		switch (door) {
			case CABINET:
				return XfsConstants.valueOf(portStatus,
						SIUCabinetDoorsState.class);
			case SAFE:
				return XfsConstants
						.valueOf(portStatus, SIUSafeDoorsState.class);
			case VANDALSHIELD:
				return XfsConstants.valueOf(portStatus,
						SIUVandalShieldState.class);
			case CABINET_FRONT:
			case CABINET_REAR:
			case CABINET_LEFT:
			case CABINET_RIGHT:
				return XfsConstants.valueOf(portStatus,
						SIUCabinetDoorsState.class);

		}
		throw new IllegalArgumentException("Illegal SIUDoor: " + door);
	}
}