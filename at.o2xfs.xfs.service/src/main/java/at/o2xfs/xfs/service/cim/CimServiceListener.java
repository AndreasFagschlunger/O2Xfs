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

package at.o2xfs.xfs.service.cim;

import java.util.Optional;

import at.o2xfs.xfs.v3_00.cim.CashIn3;
import at.o2xfs.xfs.v3_00.cim.CountsChanged3;
import at.o2xfs.xfs.v3_00.cim.ItemPosition3;
import at.o2xfs.xfs.v3_10.cim.DevicePosition310;
import at.o2xfs.xfs.v3_10.cim.PositionInfo310;
import at.o2xfs.xfs.v3_10.cim.PowerSaveChange310;
import at.o2xfs.xfs.v3_30.cim.ShutterStatusChanged330;

public interface CimServiceListener {

	public void onSafeDoorOpen();

	public void onSafeDoorClosed();

	public void onCashUnitInfoChanged(CashIn3 cashUnit);

	public void onTellerInfoChanged(int tellerId);

	public void onItemsTaken(Optional<PositionInfo310> positionInfo);

	public void onCountsChanged(CountsChanged3 countsChanged);

	public void onItemsPresented(Optional<PositionInfo310> positionInfo);

	public void onItemsInserted(Optional<PositionInfo310> positionInfo);

	public void onMediaDetected(Optional<ItemPosition3> itemPosition);

	public void onDevicePosition(DevicePosition310 devicePosition);

	public void onPowerSaveChange(PowerSaveChange310 powerSaveChange);

	public void onShutterStatusChanged(ShutterStatusChanged330 shutterStatusChanged);
}
