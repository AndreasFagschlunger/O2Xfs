/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.xfs.service.ptr;

import at.o2xfs.xfs.ptr.PTRReadForm;
import at.o2xfs.xfs.ptr.WFSPTRCAPS;
import at.o2xfs.xfs.service.XfsCommandTest;
import at.o2xfs.xfs.service.cmd.event.CancelEvent;
import at.o2xfs.xfs.service.cmd.event.ErrorEvent;
import at.o2xfs.xfs.service.ptr.cmd.ReadImageCommand;
import at.o2xfs.xfs.service.ptr.cmd.ReadImageCommandListener;
import at.o2xfs.xfs.service.ptr.cmd.ReadImageCompleteEvent;

import org.junit.Assert;
import org.junit.Test;

public class ReadImageTest
		extends XfsCommandTest
		implements ReadImageCommandListener {

	private boolean running = false;

	private boolean successful = false;

	@Test
	public final void test() throws Exception {
		for (PTRService service : serviceManager.getServices(PTRService.class)) {
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