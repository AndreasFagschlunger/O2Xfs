package at.o2xfs.xfs.service.cdm.xfs3;

import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cdm.CdmInfoCommand;
import at.o2xfs.xfs.cdm.v3_00.MixTable3;
import at.o2xfs.xfs.service.ReflectiveInfoCommand;
import at.o2xfs.xfs.service.cdm.CdmService;

public class MixTableCommand extends ReflectiveInfoCommand<CdmService, CdmInfoCommand, MixTable3> {

	public MixTableCommand(CdmService service, int mixNumber) {
		super(service, CdmInfoCommand.MIX_TABLE, new USHORT(mixNumber), MixTable3.class);
	}
}