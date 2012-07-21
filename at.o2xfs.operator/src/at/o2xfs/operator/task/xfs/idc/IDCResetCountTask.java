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

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.task.ExecuteTaskCommand;
import at.o2xfs.operator.task.Task;
import at.o2xfs.operator.ui.content.text.Label;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.idc.IDCExecuteCommand;
import at.o2xfs.xfs.idc.WFSIDCSTATUS;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.cmd.idc.IDCStatusCallable;
import at.o2xfs.xfs.service.idc.IDCService;
import at.o2xfs.xfs.service.util.ExceptionUtil;

public class IDCResetCountTask extends Task {

	private static final Logger LOG = LoggerFactory
			.getLogger(IDCResetCountTask.class);

	private IDCService idcService = null;

	public IDCResetCountTask(final IDCService idcService) {
		ExceptionUtil.nullArgument("idcService");
		this.idcService = idcService;
	}

	@Override
	protected void execute() throws Exception {
		final String method = "execute()";
		final XfsCommand resetCountCommand = new XfsExecuteCommand(idcService,
				IDCExecuteCommand.WFS_CMD_IDC_RESET_COUNT);
		WFSResult wfsResult = null;
		try {
			wfsResult = resetCountCommand.call();
			final WFSIDCSTATUS status = new IDCStatusCallable(idcService)
					.call();
			final Label label = new Label(getClass(), "CardsRetained");
			label.setArguments(status.getCards());
			taskManager.setContent(label);
		} catch (final XfsException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error resetting count", e);
			}
			showError(e);
		} finally {
			if (wfsResult != null) {
				XfsServiceManager.getInstance().free(wfsResult);
			}
		}
		if (hasParent()) {
			taskManager.setNextCommand(new ExecuteTaskCommand(getParent(),
					taskManager));
		}
	}

}
