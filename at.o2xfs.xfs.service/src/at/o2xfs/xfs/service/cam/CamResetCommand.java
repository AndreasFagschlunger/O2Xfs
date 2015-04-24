package at.o2xfs.xfs.service.cam;

import at.o2xfs.xfs.cam.CamExecuteCommand;
import at.o2xfs.xfs.service.cmd.XfsCallable;
import at.o2xfs.xfs.service.cmd.XfsInfoCommand;

public class CamResetCommand
		extends XfsCallable {

	public CamResetCommand(CamService service) {
		super(new XfsInfoCommand<CamExecuteCommand>(service, CamExecuteCommand.RESET));
	}
}
