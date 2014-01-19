package at.o2xfs.xfs.service.pin;

import java.util.EnumSet;
import java.util.Set;

import org.junit.Test;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.pin.PINFDK;
import at.o2xfs.xfs.pin.PINFK;
import at.o2xfs.xfs.pin.WFSPINFDK;
import at.o2xfs.xfs.pin.WFSPINFUNCKEYDETAIL;
import at.o2xfs.xfs.pin.WfsPINGetData;
import at.o2xfs.xfs.pin.WfsPINKey;
import at.o2xfs.xfs.service.XfsCommandTest;
import at.o2xfs.xfs.service.cmd.pin.PINDataListener;
import at.o2xfs.xfs.service.cmd.pin.PINFunctionKeysCommand;
import at.o2xfs.xfs.service.cmd.pin.PINGetDataCommand;

public class GetDataCommandTest extends XfsCommandTest implements
		PINDataListener {

	private static final Logger LOG = LoggerFactory
			.getLogger(GetDataCommandTest.class);

	private boolean running = false;

	@Test
	public void test() throws InterruptedException, XfsException {
		for (PINService pinService : xfsServiceManager
				.getServices(PINService.class)) {
			PINGetDataCommand command = new PINGetDataCommand(pinService,
					createGetData(pinService));
			command.addCommandListener(this);
			synchronized (this) {
				command.execute();
				running = true;
				while (running) {
					wait();
				}
			}
		}
	}

	private WfsPINGetData createGetData(PINService pinService)
			throws InterruptedException, XfsException {
		final String method = "createGetData(PINService)";
		final WFSPINFUNCKEYDETAIL funcKeyDetail = new PINFunctionKeysCommand(
				pinService).execute();
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "funcKeyDetail=" + funcKeyDetail);
		}
		final WfsPINGetData result = new WfsPINGetData();
		result.allocate();
		result.setActiveKeys(funcKeyDetail.getFunctionKeys());
		final Set<PINFDK> activeFDKs = EnumSet.noneOf(PINFDK.class);
		for (final WFSPINFDK pinFDK : funcKeyDetail.getFDKs()) {
			activeFDKs.add(pinFDK.getFDK());
		}
		result.setActiveFDKs(activeFDKs);
		result.setTerminateKeys(EnumSet.of(PINFK.FK_CANCEL, PINFK.FK_ENTER));
		result.setMaxLen(0);
		result.setAutoEnd(false);
		return result;
	}

	@Override
	public void commandCancelled() {
		stop();
	}

	@Override
	public void commandFailed(Exception e) {
		e.printStackTrace();
		stop();
	}

	@Override
	public void commandSuccessful() {
		stop();
	}

	private void stop() {
		synchronized (this) {
			running = false;
			notifyAll();
		}
	}

	@Override
	public void keyPressed(WfsPINKey pinKey) {
		LOG.info("keyPressed(WfsPINKey)", pinKey);
	}
}
