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

import java.util.concurrent.Callable;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.idc.IDCInfoCommand;
import at.o2xfs.xfs.idc.WFSIDCSTATUS;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.XfsCallable;
import at.o2xfs.xfs.service.cmd.XfsInfoCommand;
import at.o2xfs.xfs.service.idc.IDCService;

public class IDCStatusCallable implements Callable<WFSIDCSTATUS> {

	private static final Logger LOG = LoggerFactory
			.getLogger(IDCStatusCallable.class);

	private final IDCService idcService;

	private final XfsInfoCommand statusCommand;

	public IDCStatusCallable(final IDCService idcService) {
		if (idcService == null) {
			throw new IllegalArgumentException("idcService must not be null");
		}
		this.idcService = idcService;
		statusCommand = new XfsInfoCommand(idcService,
				IDCInfoCommand.WFS_INF_IDC_STATUS);
	}

	@Override
	public WFSIDCSTATUS call() throws InterruptedException, XfsException {
		final String method = "call()";
		final WFSResult wfsResult = new XfsCallable(statusCommand).call();
		try {
			final WFSIDCSTATUS idcStatus = new WFSIDCSTATUS(
					idcService.getXfsVersion(), wfsResult.getResults());
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "WFSIDCSTATUS: " + idcStatus);
			}
			return new WFSIDCSTATUS(idcService.getXfsVersion(), idcStatus);
		} finally {
			if (wfsResult != null) {
				XfsServiceManager.getInstance().free(wfsResult);
			}
		}
	}

}
