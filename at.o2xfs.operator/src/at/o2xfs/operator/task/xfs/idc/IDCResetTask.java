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

import at.o2xfs.operator.task.ExecuteTaskCommand;
import at.o2xfs.operator.task.Task;
import at.o2xfs.xfs.service.cmd.IAsyncCommandListener;
import at.o2xfs.xfs.service.cmd.idc.IDCResetCommand;
import at.o2xfs.xfs.service.idc.IDCService;
import at.o2xfs.xfs.service.util.ExceptionUtil;

public class IDCResetTask extends Task implements IAsyncCommandListener {

	private IDCService idcService = null;

	public IDCResetTask(final IDCService idcService) {
		if (idcService == null) {
			ExceptionUtil.nullArgument("idcService");
		}
		this.idcService = idcService;
	}

	@Override
	protected void execute() {
		final IDCResetCommand resetCommand = new IDCResetCommand(idcService);
		resetCommand.addCommandListener(this);
		resetCommand.execute();
	}

	@Override
	public void commandSuccessful() {
		finishTask();
	}

	@Override
	public void commandCancelled() {
		finishTask();
	}

	@Override
	public void commandFailed(final Exception e) {
		showError(e);
		finishTask();
	}

	private void finishTask() {
		if (hasParent()) {
			taskManager.setNextCommand(new ExecuteTaskCommand(getParent(),
					taskManager));
		}
	}
}
