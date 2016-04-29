package at.o2xfs.xfs.service.cdm.xfs3;

import at.o2xfs.xfs.cdm.CdmInfoCommand;
import at.o2xfs.xfs.cdm.v3_00.CdmCaps3;
import at.o2xfs.xfs.service.ReflectiveInfoCommand;
import at.o2xfs.xfs.service.cdm.CdmService;

public class CdmCapabilitiesCommand extends ReflectiveInfoCommand<CdmService, CdmInfoCommand, CdmCaps3> {

	public CdmCapabilitiesCommand(CdmService service) {
		super(service, CdmInfoCommand.CAPABILITIES, CdmCaps3.class);
	}
}