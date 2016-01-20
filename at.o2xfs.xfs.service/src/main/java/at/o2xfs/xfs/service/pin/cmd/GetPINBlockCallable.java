/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.xfs.service.pin.cmd;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.pin.PINExecuteCommand;
import at.o2xfs.xfs.pin.WfsPINBlock;
import at.o2xfs.xfs.pin.WfsXData;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.pin.PINService;

import java.util.concurrent.Callable;

public class GetPINBlockCallable
		implements Callable<WfsXData> {

	private static final Logger LOG = LoggerFactory.getLogger(GetPINBlockCallable.class);

	private final PINService pinService;

	private final WfsPINBlock pinBlock;

	public GetPINBlockCallable(PINService pinService, WfsPINBlock pinBlock) {
		if (pinService == null) {
			throw new NullPointerException("PINService must not be null");
		}
		this.pinService = pinService;
		if (pinBlock == null) {
			throw new NullPointerException("WfsPINBlock must not be null");
		}
		this.pinBlock = pinBlock;
	}

	@Override
	public WfsXData call() throws Exception {
		final String method = "call()";
		WFSResult wfsResult = null;
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "pinBlock=" + pinBlock);
			}
			XfsCommand xfsCommand = new XfsExecuteCommand(pinService, PINExecuteCommand.GET_PINBLOCK, pinBlock);
			wfsResult = xfsCommand.call();
			WfsXData xData = new WfsXData(wfsResult.getResults());
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "WfsXData: " + xData);
			}
			return new WfsXData(xData);
		} finally {
			if (wfsResult != null) {
				XfsServiceManager.getInstance().free(wfsResult);
			}
		}
	}
}