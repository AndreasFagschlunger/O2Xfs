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

package at.o2xfs.operator.task.xfs;

import java.util.Map;

import at.o2xfs.operator.task.ExecuteTaskCommand;
import at.o2xfs.operator.task.Task;
import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.operator.ui.content.text.Label;
import at.o2xfs.xfs.XfsError;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.service.XfsService;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.idc.IDCService;
import at.o2xfs.xfs.service.lookup.RegistryServiceLookup;
import at.o2xfs.xfs.service.pin.PINService;

public class XfsStartUpTask extends Task {

	private final int STATUS_COLUMN = 1;

	public void execute() {
		final XfsServiceManager xfsServiceManager = XfsServiceManager
				.getInstance();
		final Table table = new Table(getClass(), "Service", "Status");
		taskManager.setContent(table);
		final Map<String, XfsServiceClass> services = new RegistryServiceLookup()
				.lookup();
		int row = 0;
		for (Map.Entry<String, XfsServiceClass> service : services.entrySet()) {
			final String logicalName = service.getKey();
			final Class<? extends XfsService> serviceClass = map(service
					.getValue());
			if (serviceClass == null) {
				continue;
			}
			table.addRow(new Object[] { logicalName,
					new Label(getClass()).append("Initiate") });
			Object status = null;
			try {
				xfsServiceManager.openAndRegister(logicalName, serviceClass);
				status = XfsError.WFS_SUCCESS;
			} catch (XfsException e) {
				status = e.getError();
			}
			table.setValueAt(status, row++, STATUS_COLUMN);
		}

		if (hasChildNodes()) {
			final Task task = getChildAt(0);
			task.setParent(null);
			taskManager
					.setNextCommand(new ExecuteTaskCommand(task, taskManager));
		}
	}

	private Class<? extends XfsService> map(final XfsServiceClass serviceClass) {
		switch (serviceClass) {
			case WFS_SERVICE_CLASS_IDC:
				return IDCService.class;
			case WFS_SERVICE_CLASS_PIN:
				return PINService.class;
				// case WFS_SERVICE_CLASS_SIU:
				// return SIUService.class;
				// case WFS_SERVICE_CLASS_TTU:
				// return TTUService.class;
			default:
				return null;
		}
	}
}
