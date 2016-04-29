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