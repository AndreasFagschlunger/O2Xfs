package at.o2xfs.xfs.service.cdm.xfs3;

import at.o2xfs.xfs.cdm.v3_00.CashUnitError3;
import at.o2xfs.xfs.service.cmd.event.CommandListener;

public interface DenominateListener extends CommandListener<DenominationEvent> {

	public void onCashUnitError(CashUnitError3 cashUnitError);
}