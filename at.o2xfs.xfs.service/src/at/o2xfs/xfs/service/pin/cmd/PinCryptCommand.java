package at.o2xfs.xfs.service.pin.cmd;

import at.o2xfs.common.Assert;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.pin.PINExecuteCommand;
import at.o2xfs.xfs.pin.WfsPinCrypt;
import at.o2xfs.xfs.pin.WfsXData;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.pin.PINService;

import java.util.concurrent.Callable;

public class PinCryptCommand
		implements Callable<WfsXData> {

	private static final Logger LOG = LoggerFactory.getLogger(PinCryptCommand.class);

	private final PINService pinService;
	private final WfsPinCrypt pinCrypt;

	public PinCryptCommand(PINService pinService, WfsPinCrypt pinCrypt) {
		Assert.notNull(pinService);
		Assert.notNull(pinCrypt);
		this.pinService = pinService;
		this.pinCrypt = pinCrypt;
	}

	@Override
	public WfsXData call() throws XfsException {
		final String method = "call()";
		WFSResult result = null;
		try {
			result = new XfsExecuteCommand(pinService, PINExecuteCommand.CRYPT, pinCrypt).call();
			WfsXData xData = new WfsXData(result.getResults());
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "WfsXData: " + xData);
			}
			return new WfsXData(xData);
		} finally {
			if (result != null) {
				XfsServiceManager.getInstance().free(result);
			}
		}
	}
}
