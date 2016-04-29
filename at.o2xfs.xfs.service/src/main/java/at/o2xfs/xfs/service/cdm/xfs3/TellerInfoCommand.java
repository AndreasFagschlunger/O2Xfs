package at.o2xfs.xfs.service.cdm.xfs3;

import at.o2xfs.xfs.cdm.CdmInfoCommand;
import at.o2xfs.xfs.cdm.v3_00.TellerDetails3;
import at.o2xfs.xfs.cdm.v3_00.TellerInfo3;
import at.o2xfs.xfs.service.ReflectiveInfoCommand;
import at.o2xfs.xfs.service.cdm.CdmService;

public class TellerInfoCommand extends ReflectiveInfoCommand<CdmService, CdmInfoCommand, TellerDetails3> {

	public TellerInfoCommand(CdmService service, TellerInfo3 tellerInfo) {
		super(service, CdmInfoCommand.TELLER_INFO, tellerInfo, TellerDetails3.class);
	}
}