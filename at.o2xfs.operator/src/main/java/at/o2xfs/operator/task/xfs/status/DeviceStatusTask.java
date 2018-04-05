/*
 * Copyright (c) 2017, Andreas Fagschlunger. All rights reserved.
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

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.task.ExecuteSubTaskCommand;
import at.o2xfs.operator.task.Task;
import at.o2xfs.operator.task.xfs.idc.IDCStatusTask;
import at.o2xfs.operator.task.xfs.pin.PINStatusTask;
import at.o2xfs.operator.task.xfs.ptr.PTRStatusTask;
import at.o2xfs.operator.task.xfs.siu.SIUStatusTask;
import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.pin.WFSPINSTATUS;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.idc.IDCService;
import at.o2xfs.xfs.service.idc.cmd.IDCStatusCommand;
import at.o2xfs.xfs.service.pin.PINService;
import at.o2xfs.xfs.service.pin.cmd.PINStatusCommand;
import at.o2xfs.xfs.service.ptr.PTRService;
import at.o2xfs.xfs.service.ptr.PTRStatusCallable;
import at.o2xfs.xfs.service.siu.SIUService;
import at.o2xfs.xfs.service.siu.SIUStatusCallable;
import at.o2xfs.xfs.siu.SIUStatus;
import at.o2xfs.xfs.v3_00.idc.Status3;
import at.o2xfs.xfs.v3_00.ptr.PtrStatus3;

public class DeviceStatusTask extends Task {

	private static final Logger LOG = LoggerFactory.getLogger(DeviceStatusTask.class);

	private Table table = null;

	private void addIDCService(final IDCService idcService) {
		try {
			Status3 status = new IDCStatusCommand(idcService).call();
			table.addRowWithCommand(new ExecuteSubTaskCommand(taskManager, new IDCStatusTask(idcService)), idcService,
					status.getDeviceState());
		} catch (XfsException e) {
			table.addRow(idcService, e.getError());
		}
	}

	private void addPINService(final PINService pinService) {
		try {
			WFSPINSTATUS status = new PINStatusCommand(pinService).call();
			table.addRowWithCommand(new ExecuteSubTaskCommand(taskManager, new PINStatusTask(pinService)), pinService,
					status.getDevice());
		} catch (XfsException e) {
			table.addRow(pinService, e.getError());
		}
	}

	private void addPTRService(final PTRService service) {
		try {
			PtrStatus3 status = new PTRStatusCallable(service).call();
			table.addRowWithCommand(new ExecuteSubTaskCommand(taskManager, new PTRStatusTask(service)), service,
					status.getDevice());
		} catch (XfsException e) {
			table.addRow(service, e.getError());
		}
	}

	private void addSIUService(final SIUService service) {
		try {
			SIUStatus status = new SIUStatusCallable(service).call();
			table.addRowWithCommand(new ExecuteSubTaskCommand(taskManager, new SIUStatusTask(service)), service,
					status.getDevice());
		} catch (XfsException e) {
			table.addRow(service, e.getError());
		}
	}

	@Override
	public void doExecute() {
		final String method = "doExecute()";
		table = new Table(getClass(), "Device", "Status");
		getContent().setUIElement(table);
		final XfsServiceManager manager = XfsServiceManager.getInstance();
		for (final IDCService idcService : manager.getServices(IDCService.class)) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "idcService=" + idcService);
			}
			addIDCService(idcService);
		}
		for (final PINService pinService : manager.getServices(PINService.class)) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "pinService=" + pinService);
			}
			addPINService(pinService);
		}
		for (final PTRService service : manager.getServices(PTRService.class)) {
			addPTRService(service);
		}
		for (final SIUService service : manager.getServices(SIUService.class)) {
			addSIUService(service);
		}
	}
}
