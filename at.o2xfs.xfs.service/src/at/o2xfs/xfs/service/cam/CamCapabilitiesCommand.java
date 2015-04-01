package at.o2xfs.xfs.service.cam;

import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.cam.CamCaps;
import at.o2xfs.xfs.cam.CamInfoCommand;
import at.o2xfs.xfs.cam.v320.CamCapsV3_20;
import at.o2xfs.xfs.service.AbstractInfoCommand;
import at.o2xfs.xfs.service.cmd.XfsInfoCommand;

public class CamCapabilitiesCommand
		extends AbstractInfoCommand<CamService, CamInfoCommand, CamCaps> {

	public CamCapabilitiesCommand(CamService service) {
		super(service);
	}

	@Override
	protected XfsInfoCommand<CamInfoCommand> createInfoCommand() {
		return new XfsInfoCommand<CamInfoCommand>(service, CamInfoCommand.CAPABILITIES);
	}

	@Override
	protected CamCaps createResult(WFSResult wfsResult) {
		CamCaps result = null;
		if (service.getXfsVersion().isGE(XfsVersion.V3_20)) {
			result = new CamCapsV3_20(new CamCapsV3_20(wfsResult.getResults()));
		} else {
			result = new CamCaps(new CamCaps(wfsResult.getResults()));
		}
		return result;
	}
}
