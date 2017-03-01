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

package at.o2xfs.xfs.service.pin.cmd;

import at.o2xfs.common.Assert;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.pin.PINExecuteCommand;
import at.o2xfs.xfs.pin.WfsPinCrypt;
import at.o2xfs.xfs.pin.WfsXData;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.pin.PINService;

import java.util.concurrent.Callable;

public class PinCryptCommand
		implements Callable<WfsXData> {

	private static final Logger LOG = LoggerFactory.getLogger(PinCryptCommand.class);

	private final PINService pinService;
	private final WfsPinCrypt pinCrypt;

	public PinCryptCommand(PINService pinService, WfsPinCrypt pinCrypt) {
		Assert.notNull(pinService);
		Assert.notNull(pinCrypt);
		this.pinService = pinService;
		this.pinCrypt = pinCrypt;
	}

	@Override
	public WfsXData call() throws XfsException {
		final String method = "call()";
		WFSResult result = null;
		try {
			result = new XfsExecuteCommand(pinService, PINExecuteCommand.CRYPT, pinCrypt).call();
			WfsXData xData = new WfsXData(result.getResults());
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "WfsXData: " + xData);
			}
			return new WfsXData(xData);
		} finally {
			if (result != null) {
				XfsServiceManager.getInstance().free(result);
			}
		}
	}
}