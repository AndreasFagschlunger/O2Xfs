package at.o2xfs.xfs.service.cam;

import at.o2xfs.common.Assert;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.cam.CamEventId;
import at.o2xfs.xfs.cam.CamExecuteCommand;
import at.o2xfs.xfs.cam.Takepict;
import at.o2xfs.xfs.service.EmptyCompleteEvent;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.AbstractAsyncXfsCommand;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;

public class TakePictureCommand
		extends AbstractAsyncXfsCommand<TakePictureListener, EmptyCompleteEvent> {

	private static final Logger LOG = LoggerFactory.getLogger(TakePictureCommand.class);

	final CamService service;
	final Takepict takepict;

	public TakePictureCommand(CamService service, Takepict takepict) {
		Assert.notNull(service);
		this.service = service;
		this.takepict = takepict;
	}

	@Override
	protected XfsCommand createCommand() {
		return new XfsExecuteCommand<CamExecuteCommand>(service, CamExecuteCommand.TAKE_PICTURE, takepict);
	}

	@Override
	public void fireIntermediateEvent(WFSResult wfsResult) {
		final String method = "fireIntermediateEvent(WFSResult)";
		try {
			CamEventId eventId = wfsResult.getEventID(CamEventId.class);
			switch (eventId) {
				case INVALIDDATA:
					notifyInvalidData();
					break;
				default:
					LOG.error(method, "Illegal CamEventId: " + eventId);
			}
		} finally {
			XfsServiceManager.getInstance().free(wfsResult);
		}
	}

	private void notifyInvalidData() {
		final String method = "notifyInvalidData()";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, Integer.valueOf(listeners.size()));
		}
		for (TakePictureListener l : listeners) {
			l.onInvalidData();
		}
	}

	@Override
	protected EmptyCompleteEvent createCompleteEvent(Pointer results) {
		return new EmptyCompleteEvent();
	}
}