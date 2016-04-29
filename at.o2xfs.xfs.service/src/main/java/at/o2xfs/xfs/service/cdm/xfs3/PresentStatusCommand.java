package at.o2xfs.xfs.service.cdm.xfs3;

import at.o2xfs.xfs.cdm.CdmInfoCommand;
import at.o2xfs.xfs.cdm.Position;
import at.o2xfs.xfs.cdm.v3_00.PresentStatus3;
import at.o2xfs.xfs.service.ReflectiveInfoCommand;
import at.o2xfs.xfs.service.cdm.CdmService;
import at.o2xfs.xfs.win32.XfsWord;

public class PresentStatusCommand extends ReflectiveInfoCommand<CdmService, CdmInfoCommand, PresentStatus3> {

	public PresentStatusCommand(CdmService service, Position position) {
		super(service, CdmInfoCommand.PRESENT_STATUS, XfsWord.valueOf(position), PresentStatus3.class);
	}
}