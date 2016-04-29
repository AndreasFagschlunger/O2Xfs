package at.o2xfs.xfs.service.cdm.xfs3;

import at.o2xfs.xfs.cdm.CdmInfoCommand;
import at.o2xfs.xfs.cdm.v3_00.CdmStatus3;
import at.o2xfs.xfs.service.ReflectiveInfoCommand;
import at.o2xfs.xfs.service.cdm.CdmService;

public class CdmStatusCommand extends ReflectiveInfoCommand<CdmService, CdmInfoCommand, CdmStatus3> {

	public CdmStatusCommand(CdmService service) {
		super(service, CdmInfoCommand.STATUS, CdmStatus3.class);
	}
}