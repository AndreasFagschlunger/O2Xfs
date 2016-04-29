package at.o2xfs.xfs.service.cdm.xfs3;

import at.o2xfs.xfs.cdm.CdmInfoCommand;
import at.o2xfs.xfs.cdm.v3_00.MixType3;
import at.o2xfs.xfs.service.ReflectiveInfoCommand;
import at.o2xfs.xfs.service.cdm.CdmService;

public class MixTypesCommand extends ReflectiveInfoCommand<CdmService, CdmInfoCommand, MixType3> {

	public MixTypesCommand(CdmService service) {
		super(service, CdmInfoCommand.MIX_TYPES, MixType3.class);
	}
}