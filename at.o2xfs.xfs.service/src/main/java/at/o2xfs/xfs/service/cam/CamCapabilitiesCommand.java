package at.o2xfs.xfs.service.cam;

import at.o2xfs.xfs.cam.CamCaps;
import at.o2xfs.xfs.cam.CamInfoCommand;
import at.o2xfs.xfs.service.ReflectiveInfoCommand;

public class CamCapabilitiesCommand extends ReflectiveInfoCommand<CamService, CamInfoCommand, CamCaps> {

	public CamCapabilitiesCommand(CamService service) {
		super(service, CamInfoCommand.CAPABILITIES, CamCaps.class);
	}
}