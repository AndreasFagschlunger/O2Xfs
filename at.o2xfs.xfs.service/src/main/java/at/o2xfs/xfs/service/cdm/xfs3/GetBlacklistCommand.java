package at.o2xfs.xfs.service.cdm.xfs3;

import at.o2xfs.xfs.cdm.CdmInfoCommand;
import at.o2xfs.xfs.cdm.v3_30.Blacklist3_30;
import at.o2xfs.xfs.service.ReflectiveInfoCommand;
import at.o2xfs.xfs.service.cdm.CdmService;

public class GetBlacklistCommand extends ReflectiveInfoCommand<CdmService, CdmInfoCommand, Blacklist3_30> {

	public GetBlacklistCommand(CdmService service) {
		super(service, CdmInfoCommand.GET_BLACKLIST, Blacklist3_30.class);
	}
}