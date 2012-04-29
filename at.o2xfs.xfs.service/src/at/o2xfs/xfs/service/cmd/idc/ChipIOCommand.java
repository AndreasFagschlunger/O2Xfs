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

package at.o2xfs.xfs.service.cmd.idc;

import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.idc.IDCExecuteCommand;
import at.o2xfs.xfs.idc.WFSIDCCHIPIO;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.IXfsCommand;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.idc.IDCService;
import at.o2xfs.xfs.service.util.ExceptionUtil;

/**
 * This command is used to communicate with the chip.
 * 
 * @author Andreas Fagschlunger
 */
public class ChipIOCommand implements IXfsCommand<WFSIDCCHIPIO> {

	private IDCService idcService = null;

	private WFSIDCCHIPIO chipIoIn = null;

	public ChipIOCommand(final IDCService idcService,
			final WFSIDCCHIPIO chipIoIn) {
		ExceptionUtil.nullArgument("idcService");
		this.idcService = idcService;
		this.chipIoIn = chipIoIn;
	}

	@Override
	public WFSIDCCHIPIO execute() throws XfsException {
		XfsCommand xfsCommand = new XfsExecuteCommand(idcService,
				IDCExecuteCommand.WFS_CMD_IDC_CHIP_IO, chipIoIn);
		WFSResult wfsResult = null;
		try {
			wfsResult = xfsCommand.call();
			final WFSIDCCHIPIO chipIoOut = new WFSIDCCHIPIO(
					wfsResult.getBuffer());
			return new WFSIDCCHIPIO(chipIoOut);
		} finally {
			if (wfsResult != null) {
				XfsServiceManager.getInstance().free(wfsResult);
			}
		}
	}

}
