package at.o2xfs.xfs.service.cdm;

import java.util.Optional;

import at.o2xfs.xfs.cdm.Position;
import at.o2xfs.xfs.cdm.v3_00.CashUnit3;
import at.o2xfs.xfs.cdm.v3_00.CountsChanged3;
import at.o2xfs.xfs.cdm.v3_00.ItemPosition3;
import at.o2xfs.xfs.cdm.v3_10.DevicePosition3_10;
import at.o2xfs.xfs.cdm.v3_10.PowerSaveChange3_10;
import at.o2xfs.xfs.cdm.v3_30.ShutterStatusChanged3_30;

public interface CdmServiceListener {

	public void onSafeDoorOpen();

	public void onSafeDoorClosed();

	public void onCashUnitThreshold(CashUnit3 cashUnit);

	public void onCashUnitInfoChanged(CashUnit3 cashUnit);

	public void onTellerInfoChanged(int tellerId);

	public void onItemsTaken(Position position);

	public void onCountsChanged(CountsChanged3 countsChanged);

	public void onItemsPresented();

	public void onMediaDetected(Optional<ItemPosition3> itemPosition);

	public void onDevicePosition(DevicePosition3_10 devicePosition);

	public void onPowerSaveChange(PowerSaveChange3_10 powerSaveChange);

	public void onShutterStatusChanged(ShutterStatusChanged3_30 shutterStatusChanged);
}
