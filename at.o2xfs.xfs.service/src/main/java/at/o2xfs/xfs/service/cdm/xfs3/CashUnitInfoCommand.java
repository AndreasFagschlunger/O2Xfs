package at.o2xfs.xfs.service.cdm.xfs3;

import at.o2xfs.xfs.cdm.CdmInfoCommand;
import at.o2xfs.xfs.cdm.v3_00.CashUnitInfo3;
import at.o2xfs.xfs.service.ReflectiveInfoCommand;
import at.o2xfs.xfs.service.cdm.CdmService;

public class CashUnitInfoCommand extends ReflectiveInfoCommand<CdmService, CdmInfoCommand, CashUnitInfo3> {

	public CashUnitInfoCommand(CdmService service) {
		super(service, CdmInfoCommand.CASH_UNIT_INFO, CashUnitInfo3.class);
	}
}