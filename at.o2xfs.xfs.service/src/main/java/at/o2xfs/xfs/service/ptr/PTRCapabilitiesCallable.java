/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.xfs.service.ptr;

import java.util.concurrent.Callable;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.ptr.PTRInfoCommand;
import at.o2xfs.xfs.ptr.WFSPTRCAPS;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.XfsInfoCommand;
import at.o2xfs.xfs.service.util.ExceptionUtil;

public class PTRCapabilitiesCallable implements Callable<WFSPTRCAPS> {

	private static final Logger LOG = LoggerFactory
			.getLogger(PTRCapabilitiesCallable.class);

	private final PTRService ptrService;

	public PTRCapabilitiesCallable(final PTRService ptrService) {
		if (ptrService == null) {
			ExceptionUtil.nullArgument("ptrService");
		}
		this.ptrService = ptrService;
	}

	@Override
	public WFSPTRCAPS call() throws XfsException {
		final String method = "call()";
		final XfsInfoCommand infoCommand = new XfsInfoCommand(ptrService,
				PTRInfoCommand.CAPABILITIES);
		final WFSResult wfsResult = infoCommand.call();
		try {
			final WFSPTRCAPS caps = new WFSPTRCAPS(wfsResult.getResults(),
					ptrService.getXfsVersion());
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "WFSPTRCAPS: " + caps);
			}
			return new WFSPTRCAPS(caps, ptrService.getXfsVersion());
		} finally {
			if (wfsResult != null) {
				XfsServiceManager.getInstance().free(wfsResult);
			}
		}
	}
}