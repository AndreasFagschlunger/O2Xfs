package at.o2xfs.xfs.service.pin.cmd;

import java.util.EnumSet;
import java.util.Set;

import org.junit.Test;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.pin.PINFK;
import at.o2xfs.xfs.pin.WfsPINGetPIN;
import at.o2xfs.xfs.pin.WfsPINKey;
import at.o2xfs.xfs.service.XfsCommandTest;
import at.o2xfs.xfs.service.cmd.event.CancelEvent;
import at.o2xfs.xfs.service.cmd.event.ErrorEvent;
import at.o2xfs.xfs.service.pin.PINService;

public class GetPINCommandTest extends XfsCommandTest implements GetPINListener {

	private static final Logger LOG = LoggerFactory
			.getLogger(GetPINCommandTest.class);

	private boolean running = false;

	@Test
	public final void test() throws InterruptedException {
		for (PINService pinService : xfsServiceManager
				.getServices(PINService.class)) {
			WfsPINGetPIN pinGetPIN = new WfsPINGetPIN();
			pinGetPIN.allocate();
			pinGetPIN.setMinLen(4);
			pinGetPIN.setMaxLen(12);
			pinGetPIN.setEcho('*');
			Set<PINFK> activeKeys = EnumSet.range(PINFK.FK_0, PINFK.FK_CANCEL);
			pinGetPIN.setActiveKeys(activeKeys);
			pinGetPIN.setTerminateKeys(EnumSet.range(PINFK.FK_ENTER,
					PINFK.FK_CANCEL));
			GetPINCommand pinCommand = new GetPINCommand(pinService, pinGetPIN);
			pinCommand.addCommandListener(this);
			synchronized (this) {
				pinCommand.execute();
				running = true;
				while (running) {
					wait();
				}
			}
		}
	}

	@Override
	public void onCancel(CancelEvent event) {
		LOG.info("onCancel(CancelEvent)", event);
		stop();
	}

	@Override
	public void onError(ErrorEvent event) {
		LOG.info("onError(ErrorEvent)", event);
		stop();
	}

	@Override
	public void onComplete(GetPINCompleteEvent event) {
		LOG.info("onComplete(GetPINCompleteEvent)", event);
		stop();
	}

	private void stop() {
		synchronized (this) {
			running = false;
			notifyAll();
		}
	}

	@Override
	public void onKeyPress(WfsPINKey pinKey) {
		LOG.info("onKeyPress(WfsPINKey)", pinKey);
	}

	@Override
	public void onEnterData() {
		LOG.info("", "");
	}
}