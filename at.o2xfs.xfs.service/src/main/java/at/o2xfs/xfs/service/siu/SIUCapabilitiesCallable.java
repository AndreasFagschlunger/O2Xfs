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

package at.o2xfs.xfs.service.siu;

import java.util.concurrent.Callable;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsInfoCommand;
import at.o2xfs.xfs.service.util.ExceptionUtil;
import at.o2xfs.xfs.siu.SIUCapabilities;
import at.o2xfs.xfs.siu.SIUInfoCommand;

public class SIUCapabilitiesCallable implements Callable<SIUCapabilities> {

	private static final Logger LOG = LoggerFactory
			.getLogger(SIUCapabilitiesCallable.class);

	private final SIUService siuService;

	public SIUCapabilitiesCallable(final SIUService siuService) {
		if (siuService == null) {
			ExceptionUtil.nullArgument("siuService");
		}
		this.siuService = siuService;
	}

	@Override
	public SIUCapabilities call() throws XfsException {
		final String method = "call()";
		final XfsCommand xfsCommand = new XfsInfoCommand(siuService,
				SIUInfoCommand.CAPABILITIES);
		WFSResult wfsResult = null;
		try {
			wfsResult = xfsCommand.call();
			final SIUCapabilities capabilities = new SIUCapabilities(
					siuService.getXfsVersion(), wfsResult.getResults());
			if (LOG.isInfoEnabled()) {
				LOG.info(method, capabilities);
			}
			return new SIUCapabilities(siuService.getXfsVersion(), capabilities);
		} finally {
			if (wfsResult != null) {
				XfsServiceManager.getInstance().free(wfsResult);
			}
		}
	}
}