package at.o2xfs.xfs.service.siu.cmd;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.AbstractAsyncXfsCommand;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.cmd.event.SuccessEvent;
import at.o2xfs.xfs.service.siu.SIUService;
import at.o2xfs.xfs.service.siu.SiuFactory;
import at.o2xfs.xfs.siu.SIUExecuteCommand;
import at.o2xfs.xfs.siu.SIUMessage;
import at.o2xfs.xfs.siu.SIUPortError;
import at.o2xfs.xfs.v3_00.siu.SetGuidLight3;

public class SetGuidLightCommand extends AbstractAsyncXfsCommand<SetGuidLightListener, SuccessEvent> {

	private static final Logger LOG = LoggerFactory.getLogger(SetGuidLightCommand.class);

	private final SIUService service;
	private final SetGuidLight3 setGuidLight;

	public SetGuidLightCommand(SIUService service, SetGuidLight3 setGuidLight) {
		this.service = service;
		this.setGuidLight = setGuidLight;
	}

	@Override
	protected XfsCommand createCommand() {
		return new XfsExecuteCommand<SIUExecuteCommand>(service, SIUExecuteCommand.SET_GUIDLIGHT, setGuidLight);
	}

	@Override
	public void fireIntermediateEvent(WFSResult wfsResult) {
		String method = "fireIntermediateEvent(WFSResult)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "wfsResult=" + wfsResult);
		}
		try {
			SIUMessage message = wfsResult.getEventID(SIUMessage.class);
			switch (message) {
			case PORT_ERROR:
				firePortError(SiuFactory.create(service.getXfsVersion(), wfsResult.getResults(), SIUPortError.class));
				break;
			default:
				throw new IllegalArgumentException(message.toString());
			}
		} finally {
			XfsServiceManager.getInstance().free(wfsResult);
		}
	}

	private void firePortError(SIUPortError portError) {
		LOG.info("firePortError(SIUPortError)", portError);
		for (SetGuidLightListener each : listeners) {
			each.onPortError(portError);
		}
	}

	@Override
	protected SuccessEvent createCompleteEvent(Pointer results) {
		return SuccessEvent.build();
	}

}
