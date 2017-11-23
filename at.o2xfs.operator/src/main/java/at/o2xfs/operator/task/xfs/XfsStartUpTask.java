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

package at.o2xfs.operator.task.xfs;

import java.util.Map;

import at.o2xfs.operator.task.DefaultTaskCommand;
import at.o2xfs.operator.task.Task;
import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.operator.ui.content.text.Label;
import at.o2xfs.xfs.XfsError;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.service.XfsService;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cdm.CdmService;
import at.o2xfs.xfs.service.idc.IDCService;
import at.o2xfs.xfs.service.pin.PINService;
import at.o2xfs.xfs.service.ptr.PTRService;
import at.o2xfs.xfs.service.siu.SIUEnableEventsCommand;
import at.o2xfs.xfs.service.siu.SIUService;

public class XfsStartUpTask extends Task {

	private final int STATUS_COLUMN = 1;

	@Override
	protected boolean setCloseCommandPerDefault() {
		return false;
	}

	@Override
	protected void doExecute() {
		getCommands().clear();
		final XfsServiceManager serviceManager = XfsServiceManager.getInstance();
		try {
			serviceManager.initialize();
		} catch (XfsException e) {
			showException(e);
			setDefaultTaskCommand();
			return;
		}
		final Table table = new Table(getClass(), "Service", "Status");
		getContent().setUIElement(table);
		final Map<String, XfsServiceClass> services = new PropertiesServiceLookup().lookup();
		int row = 0;
		for (Map.Entry<String, XfsServiceClass> service : services.entrySet()) {
			final String logicalName = service.getKey();
			final Class<? extends XfsService> serviceClass = map(service.getValue());
			if (serviceClass == null) {
				continue;
			}
			table.addRow(logicalName, new Label(getClass().getSimpleName(), "Initiate"));
			Object status = null;
			try {
				final XfsService xfsService = serviceManager.openAndRegister(logicalName, serviceClass);
				status = XfsError.SUCCESS;
				if (XfsServiceClass.SIU.equals(service.getValue())) {
					new SIUEnableEventsCommand((SIUService) xfsService).execute();
				}
			} catch (final XfsException e) {
				status = e.getError();
			}
			table.setValueAt(status, row++, STATUS_COLUMN);
		}
		setDefaultTaskCommand();
	}

	private void setDefaultTaskCommand() {
		getCommands().setNextCommand(new DefaultTaskCommand(taskManager));
	}

	private Class<? extends XfsService> map(final XfsServiceClass serviceClass) {
		switch (serviceClass) {
		case CDM:
			return CdmService.class;
		case IDC:
			return IDCService.class;
		case PIN:
			return PINService.class;
		case PTR:
			return PTRService.class;
		case SIU:
			return SIUService.class;
		// case TTU:
		// return TTUService.class;
		default:
			return null;
		}
	}
}