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

package at.o2xfs.operator.task.xfs.idc;

import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.task.ExecuteTaskCommand;
import at.o2xfs.operator.task.Task;
import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.operator.ui.content.text.Label;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.idc.WFSIDCSTATUS;
import at.o2xfs.xfs.service.cmd.idc.IDCStatusCommand;
import at.o2xfs.xfs.service.idc.IDCService;

public class IDCStatusTask extends Task {

	private final static Logger LOG = LoggerFactory
			.getLogger(IDCStatusTask.class);

	private IDCService idcService = null;

	public IDCStatusTask(final IDCService idcService) {
		this.idcService = idcService;
	}

	@Override
	public void execute() {
		final String method = "execute()";
		try {
			final Table table = new Table(getClass(), "Component", "Status");
			final WFSIDCSTATUS idcStatus = new IDCStatusCommand(idcService)
					.execute();
			if (LOG.isInfoEnabled()) {
				LOG.debug(method, "idcStatus=" + idcStatus);
			}

			table.addRow(new Label(getClass(), "Device"), idcStatus.getDevice());
			table.addRow(new Label(getClass(), "Media"), idcStatus.getMedia());
			table.addRow(new Label(getClass(), "RetainBin"),
					idcStatus.getRetainBin());
			table.addRow(new Label(getClass(), "Security"),
					idcStatus.getSecurity());
			table.addRow(new Label(getClass(), "Cards"), idcStatus.getCards());

			for (Map.Entry<String, String> entry : idcStatus.getExtra()
					.entrySet()) {
				table.addRow(entry.getKey(), entry.getValue());
			}
			taskManager.setContent(table);
		} catch (XfsException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method,
						"Error executing IDCStatusCommand for XfsService: "
								+ idcService, e);
			}
		}
		taskManager.setBackCommand(new ExecuteTaskCommand(getParent(),
				taskManager));
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("idcService", idcService)
				.toString();
	}

}
