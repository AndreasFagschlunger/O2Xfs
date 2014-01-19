package at.o2xfs.xfs.service.pin.cmd;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.pin.PINExecuteCommand;
import at.o2xfs.xfs.pin.PINMessage;
import at.o2xfs.xfs.pin.WfsPINEntry;
import at.o2xfs.xfs.pin.WfsPINGetPIN;
import at.o2xfs.xfs.pin.WfsPINKey;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.AbstractAsyncXfsCommand;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.pin.PINService;

public class GetPINCommand extends
		AbstractAsyncXfsCommand<GetPINListener, GetPINCompleteEvent> {

	private static final Logger LOG = LoggerFactory
			.getLogger(GetPINCommand.class);

	private final PINService pinService;

	private final WfsPINGetPIN pinGetPIN;

	public GetPINCommand(final PINService pinService,
			final WfsPINGetPIN pinGetPIN) {
		if (pinService == null) {
			throw new NullPointerException("pinService must not be null");
		}
		this.pinService = pinService;
		if (pinGetPIN == null) {
			throw new NullPointerException("WfsPINGetPIN must not be null");
		}
		this.pinGetPIN = pinGetPIN;
	}

	@Override
	protected XfsCommand createCommand() {
		return new XfsExecuteCommand(pinService, PINExecuteCommand.GET_PIN,
				pinGetPIN);
	}

	@Override
	protected void doExecute() throws XfsException {
		super.doExecute();
		if (pinService.getXfsVersion().isLT(XfsVersion.V3_10)) {
			fireEnterData();
		}
	}

	@Override
	public void fireIntermediateEvent(WFSResult wfsResult) {
		final String method = "fireIntermediateEvent(WFSResult)";
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "wfsResult=" + wfsResult);
			}
			PINMessage message = wfsResult.getEventID(PINMessage.class);
			switch (message) {
				case WFS_EXEE_PIN_KEY:
					WfsPINKey pinKey = new WfsPINKey(wfsResult.getResults());
					fireKeyPress(pinKey);
					break;
				case WFS_EXEE_PIN_ENTERDATA:
					fireEnterData();
					break;
				default:
					break;
			}
		} finally {
			XfsServiceManager.getInstance().free(wfsResult);
		}
	}

	private void fireKeyPress(WfsPINKey pinKey) {
		final String method = "fireKeyPress(WfsPINKey)";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, "Listeners: " + listeners.size() + ", WfsPINKey: "
					+ pinKey);
		}
		for (GetPINListener l : listeners) {
			l.onKeyPress(new WfsPINKey(pinKey));
		}
	}

	private void fireEnterData() {
		for (GetPINListener l : listeners) {
			l.onEnterData();
		}
	}

	@Override
	protected GetPINCompleteEvent createCompleteEvent(Pointer results) {
		final String method = "createCompleteEvent(Pointer)";
		WfsPINEntry pinEntry = new WfsPINEntry(results);
		if (LOG.isInfoEnabled()) {
			LOG.info(method, "WfsPINEntry: " + pinEntry);
		}
		return GetPINCompleteEvent.create(pinEntry);
	}
}