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

package at.o2xfs.operator.task.xfs.idc;

import java.util.Map;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.operator.ui.content.text.Label;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.v3_00.idc.Status3;
import at.o2xfs.xfs.service.idc.IDCService;
import at.o2xfs.xfs.service.idc.cmd.IDCStatusCommand;

public class IDCStatusTask extends IDCTask {

	private final static Logger LOG = LoggerFactory.getLogger(IDCStatusTask.class);

	public IDCStatusTask() {
		super();
	}

	public IDCStatusTask(IDCService service) {
		super(service);
	}

	@Override
	protected void execute() {
		String method = "execute()";
		try {
			final Table table = new Table(getClass(), "Component", "Status");
			final Status3 status = new IDCStatusCommand(service).call();
			Label rootLabel = new Label(getClass().getSimpleName());
			table.addRow(rootLabel.append("Device"), status.getDeviceState());
			table.addRow(rootLabel.append("Media"), status.getMedia());
			table.addRow(rootLabel.append("RetainBin"), status.getRetainBin());
			table.addRow(rootLabel.append("Security"), status.getSecurity());
			table.addRow(rootLabel.append("Cards"), status.getCards());
			for (Map.Entry<String, String> entry : status.getExtra().entrySet()) {
				table.addRow(entry.getKey(), entry.getValue());
			}
			getContent().setUIElement(table);
		} catch (XfsException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error executing statusCommand for XfsService: " + service, e);
			}
		}
	}
}
