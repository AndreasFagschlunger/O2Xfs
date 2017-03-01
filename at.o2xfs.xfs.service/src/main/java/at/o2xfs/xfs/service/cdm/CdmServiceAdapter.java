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

package at.o2xfs.xfs.service.cdm;

import java.util.Optional;

import at.o2xfs.xfs.cdm.Position;
import at.o2xfs.xfs.cdm.v3_00.CashUnit3;
import at.o2xfs.xfs.cdm.v3_00.CountsChanged3;
import at.o2xfs.xfs.cdm.v3_00.ItemPosition3;
import at.o2xfs.xfs.cdm.v3_10.DevicePosition3_10;
import at.o2xfs.xfs.cdm.v3_10.PowerSaveChange3_10;
import at.o2xfs.xfs.cdm.v3_30.ShutterStatusChanged3_30;

public class CdmServiceAdapter implements CdmServiceListener {

	@Override
	public void onSafeDoorOpen() {

	}

	@Override
	public void onSafeDoorClosed() {

	}

	@Override
	public void onCashUnitThreshold(CashUnit3 cashUnit) {

	}

	@Override
	public void onCashUnitInfoChanged(CashUnit3 cashUnit) {

	}

	@Override
	public void onTellerInfoChanged(int tellerId) {

	}

	@Override
	public void onItemsTaken(Position position) {

	}

	@Override
	public void onCountsChanged(CountsChanged3 countsChanged) {

	}

	@Override
	public void onItemsPresented() {

	}

	@Override
	public void onMediaDetected(Optional<ItemPosition3> itemPosition) {

	}

	@Override
	public void onDevicePosition(DevicePosition3_10 devicePosition) {

	}

	@Override
	public void onPowerSaveChange(PowerSaveChange3_10 powerSaveChange) {

	}

	@Override
	public void onShutterStatusChanged(ShutterStatusChanged3_30 shutterStatusChanged) {

	}
}