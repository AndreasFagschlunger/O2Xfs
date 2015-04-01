package at.o2xfs.xfs.service.cam;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.cam.CamInfoCommand;
import at.o2xfs.xfs.cam.CamStatus;
import at.o2xfs.xfs.cam.v320.CamStatusV3_20;
import at.o2xfs.xfs.service.AbstractInfoCommand;
import at.o2xfs.xfs.service.cmd.XfsInfoCommand;

public class CamStatusCommand
		extends AbstractInfoCommand<CamService, CamInfoCommand, CamStatus> {

	private final static Logger LOG = LoggerFactory.getLogger(CamStatusCommand.class);

	public CamStatusCommand(CamService service) {
		super(service);
	}

	@Override
	protected XfsInfoCommand<CamInfoCommand> createInfoCommand() {
		return new XfsInfoCommand<CamInfoCommand>(service, CamInfoCommand.STATUS);
	}

	@Override
	protected CamStatus createResult(WFSResult wfsResult) {
		final String method = "createResult(WFSResult)";
		CamStatus result = null;
		if (service.getXfsVersion().isGE(XfsVersion.V3_20)) {
			CamStatusV3_20 camStatus = new CamStatusV3_20(wfsResult.getResults());
			result = new CamStatusV3_20(camStatus);
		} else {
			CamStatus camStatus = new CamStatus(wfsResult.getResults());
			result = new CamStatus(camStatus);
		}
		LOG.info(method, result);
		return result;
	}
}
