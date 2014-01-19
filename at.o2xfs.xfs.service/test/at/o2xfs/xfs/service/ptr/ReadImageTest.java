package at.o2xfs.xfs.service.ptr;

import org.junit.Assert;
import org.junit.Test;

import at.o2xfs.xfs.ptr.PTRReadForm;
import at.o2xfs.xfs.ptr.WFSPTRCAPS;
import at.o2xfs.xfs.service.XfsCommandTest;
import at.o2xfs.xfs.service.cmd.event.CancelEvent;
import at.o2xfs.xfs.service.cmd.event.ErrorEvent;
import at.o2xfs.xfs.service.cmd.ptr.ReadImageCommand;
import at.o2xfs.xfs.service.cmd.ptr.ReadImageCommandListener;
import at.o2xfs.xfs.service.cmd.ptr.ReadImageCompleteEvent;

public class ReadImageTest extends XfsCommandTest implements
		ReadImageCommandListener {

	private boolean running = false;

	private boolean successful = false;

	@Test
	public final void test() throws Exception {
		for (PTRService service : xfsServiceManager
				.getServices(PTRService.class)) {
			WFSPTRCAPS caps = new PTRCapabilitiesCallable(service).call();
			if (caps.getReadForm().contains(PTRReadForm.IMAGE)) {
				readImage(service);
			}
		}
	}

	private void readImage(PTRService service) {
		ReadImageCommand command = new ReadImageCommand(service);
		command.addCommandListener(this);
		synchronized (this) {
			command.execute();
			running = true;
			while (running) {
				try {
					wait();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			Assert.assertTrue(successful);
		}
	}

	private void stop() {
		synchronized (this) {
			running = false;
			notifyAll();
		}
	}

	@Override
	public void onCancel(CancelEvent event) {
		stop();
	}

	@Override
	public void onError(ErrorEvent event) {
		stop();
	}

	@Override
	public void onComplete(ReadImageCompleteEvent event) {
		successful = true;
		stop();
	}
}
