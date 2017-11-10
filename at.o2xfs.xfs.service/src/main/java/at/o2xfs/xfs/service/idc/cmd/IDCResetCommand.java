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

package at.o2xfs.xfs.service.idc.cmd;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.DWORD;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.idc.IdcExecuteCommand;
import at.o2xfs.xfs.idc.PowerOffOption;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.AbstractAsyncCommand;
import at.o2xfs.xfs.service.cmd.IAsyncCommandListener;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.idc.IDCService;
import at.o2xfs.xfs.service.util.ExceptionUtil;

public class IDCResetCommand extends AbstractAsyncCommand<IAsyncCommandListener> {

	private static final Logger LOG = LoggerFactory.getLogger(IDCResetCommand.class);

	private IDCService idcService = null;

	private PowerOffOption cardFoundAction = null;

	private XfsCommand resetCommand = null;

	public IDCResetCommand(final IDCService idcService) {
		if (idcService == null) {
			ExceptionUtil.nullArgument("idcService");
		}
		this.idcService = idcService;
	}

	public void setCardFoundAction(final PowerOffOption cardFoundAction) throws IllegalArgumentException {
		this.cardFoundAction = cardFoundAction;
	}

	@Override
	protected void doExecute() {
		final String method = "doExecute()";
		synchronized (this) {
			if (resetCommand != null) {
				throw new IllegalStateException("Already executed");
			}
			createResetCommand();
			WFSResult wfsResult = null;
			try {
				wfsResult = resetCommand.call();
				notifyCommandSuccessful();
			} catch (final Exception e) {
				if (LOG.isErrorEnabled()) {
					LOG.error(method, "Error executing XfsCommand: " + resetCommand, e);
				}
				notifyCommandFailed(e);
			} finally {
				if (wfsResult != null) {
					XfsServiceManager.getInstance().free(wfsResult);
				}
			}
			resetCommand = null;
		}
	}

	private void createResetCommand() {
		DWORD resetIn = null;
		if (cardFoundAction != null) {
			resetIn = new DWORD(cardFoundAction.getValue());
		}
		resetCommand = new XfsExecuteCommand<>(idcService, IdcExecuteCommand.RESET, resetIn);
	}

	@Override
	public void cancel() {
	}

}