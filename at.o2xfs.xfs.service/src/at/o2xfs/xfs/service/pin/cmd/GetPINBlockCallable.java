package at.o2xfs.xfs.service.pin.cmd;

import java.util.concurrent.Callable;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.pin.PINExecuteCommand;
import at.o2xfs.xfs.pin.WfsPINBlock;
import at.o2xfs.xfs.pin.WfsXData;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.pin.PINService;

public class GetPINBlockCallable implements Callable<WfsXData> {

	private static final Logger LOG = LoggerFactory
			.getLogger(GetPINBlockCallable.class);

	private final PINService pinService;

	private final WfsPINBlock pinBlock;

	public GetPINBlockCallable(PINService pinService, WfsPINBlock pinBlock) {
		if (pinService == null) {
			throw new NullPointerException("PINService must not be null");
		}
		this.pinService = pinService;
		if (pinBlock == null) {
			throw new NullPointerException("WfsPINBlock must not be null");
		}
		this.pinBlock = pinBlock;
	}

	@Override
	public WfsXData call() throws Exception {
		final String method = "call()";
		WFSResult wfsResult = null;
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "pinBlock=" + pinBlock);
			}
			XfsCommand xfsCommand = new XfsExecuteCommand(pinService,
					PINExecuteCommand.GET_PINBLOCK, pinBlock);
			wfsResult = xfsCommand.call();
			WfsXData xData = new WfsXData(wfsResult.getResults());
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "WfsXData: " + xData.getData());
			}
			return new WfsXData(xData);
		} finally {
			if (wfsResult != null) {
				XfsServiceManager.getInstance().free(wfsResult);
			}
		}
	}
}
