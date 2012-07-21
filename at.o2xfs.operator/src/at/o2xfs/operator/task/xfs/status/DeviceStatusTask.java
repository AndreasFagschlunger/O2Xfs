/*
 * Copyright (c) 2012, Andreas Fagschlunger. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.operator.task.xfs.status;

import at.o2xfs.operator.task.ExecuteTaskCommand;
import at.o2xfs.operator.task.Task;
import at.o2xfs.operator.task.TaskCommand;
import at.o2xfs.operator.task.xfs.idc.IDCStatusTask;
import at.o2xfs.operator.task.xfs.pin.PINStatusTask;
import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.idc.IDCStatusCommand;
import at.o2xfs.xfs.service.cmd.pin.PINStatusCommand;
import at.o2xfs.xfs.service.idc.IDCService;
import at.o2xfs.xfs.service.pin.PINService;

public class DeviceStatusTask extends Task {

	private Table table = null;

	private void addIDCService(final IDCService idcService)
			throws InterruptedException {
		Object idcStatus = null;
		try {
			idcStatus = new IDCStatusCommand(idcService).execute().getDevice();
		} catch (XfsException e) {
			idcStatus = e.getError();
		}
		final IDCStatusTask idcStatusTask = new IDCStatusTask(idcService);
		idcStatusTask.setParent(this);
		final TaskCommand command = new ExecuteTaskCommand(idcStatusTask,
				taskManager);
		table.addRowWithCommand(command, idcService, idcStatus);
	}

	private void addPINService(final PINService pinService)
			throws InterruptedException {
		Object pinStatus = null;
		try {
			pinStatus = new PINStatusCommand(pinService).execute().getDevice();
		} catch (XfsException e) {
			pinStatus = e.getError();
		}

		final PINStatusTask pinStatusTask = new PINStatusTask(pinService);
		pinStatusTask.setParent(this);
		final TaskCommand command = new ExecuteTaskCommand(pinStatusTask,
				taskManager);
		table.addRowWithCommand(command, pinService, pinStatus);
	}

	@Override
	public void execute() throws Exception {
		table = new Table(getClass(), "Device", "Status");
		final XfsServiceManager manager = XfsServiceManager.getInstance();
		for (final IDCService idcService : manager
				.getServices(IDCService.class)) {
			addIDCService(idcService);
		}
		for (final PINService pinService : manager
				.getServices(PINService.class)) {
			addPINService(pinService);
		}
		taskManager.setContent(table);
		if (hasParent()) {
			taskManager.setBackCommand(new ExecuteTaskCommand(getParent(),
					taskManager));
		}
	}
}
