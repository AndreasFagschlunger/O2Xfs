package at.o2xfs.xfs.service.cam;

import at.o2xfs.xfs.cam.CamInfoCommand;
import at.o2xfs.xfs.cam.CamStatus;
import at.o2xfs.xfs.service.ReflectiveInfoCommand;

public class CamStatusCommand extends ReflectiveInfoCommand<CamService, CamInfoCommand, CamStatus> {

	public CamStatusCommand(CamService service) {
		super(service, CamInfoCommand.STATUS, CamStatus.class);
	}
}