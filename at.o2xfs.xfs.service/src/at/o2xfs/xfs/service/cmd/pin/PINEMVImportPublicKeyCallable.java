package at.o2xfs.xfs.service.cmd.pin;

import java.util.concurrent.Callable;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.pin.PINExecuteCommand;
import at.o2xfs.xfs.pin.WfsPINEMVImportPublicKey;
import at.o2xfs.xfs.pin.WfsPINEMVImportPublicKeyOutput;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.pin.PINService;

public final class PINEMVImportPublicKeyCallable implements
		Callable<WfsPINEMVImportPublicKeyOutput> {

	private static final Logger LOG = LoggerFactory
			.getLogger(PINEMVImportPublicKeyCallable.class);

	private final PINService pinService;

	private final WfsPINEMVImportPublicKey emvImportPublicKey;

	public PINEMVImportPublicKeyCallable(PINService pinService,
			WfsPINEMVImportPublicKey emvImportPublicKey) {
		this.pinService = pinService;
		this.emvImportPublicKey = emvImportPublicKey;
	}

	@Override
	public WfsPINEMVImportPublicKeyOutput call() throws Exception {
		final String method = "call()";
		XfsCommand xfsCommand = new XfsExecuteCommand(pinService,
				PINExecuteCommand.EMV_IMPORT_PUBLIC_KEY, emvImportPublicKey);
		WFSResult wfsResult = null;
		try {
			wfsResult = xfsCommand.call();
			WfsPINEMVImportPublicKeyOutput output = new WfsPINEMVImportPublicKeyOutput(
					wfsResult.getResults());
			if (LOG.isInfoEnabled()) {
				LOG.info(method, output);
			}
			return new WfsPINEMVImportPublicKeyOutput(output);
		} finally {
			if (wfsResult != null) {
				XfsServiceManager.getInstance().free(wfsResult);
			}
		}
	}

}
