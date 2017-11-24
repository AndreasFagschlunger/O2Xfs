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

import java.util.concurrent.Callable;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.idc.IdcExecuteCommand;
import at.o2xfs.xfs.v3_00.idc.RetainCard3;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.events.XfsEventNotification;
import at.o2xfs.xfs.service.idc.IDCService;

public class RetainCardCallable implements Callable<RetainCard3>, XfsEventNotification {

	private static final Logger LOG = LoggerFactory.getLogger(RetainCardCallable.class);

	private XfsCommand retainCardCommand = null;

	private WFSResult wfsResult = null;

	public RetainCardCallable(final IDCService cardReader) {
		if (cardReader == null) {
			throw new IllegalArgumentException("cardReader must not be null");
		}
		retainCardCommand = new XfsExecuteCommand<>(cardReader, IdcExecuteCommand.RETAIN_CARD);
	}

	@Override
	public RetainCard3 call() throws InterruptedException, XfsException {
		final String method = "call()";
		synchronized (this) {
			retainCardCommand.execute(this);
			while (wfsResult == null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug(method, "Waiting ...");
				}
				wait();
			}
		}
		try {
			XfsException.throwFor(wfsResult.getResult());
			final RetainCard3 retainCard = new RetainCard3(wfsResult.getResults());
			return new RetainCard3(retainCard);
		} finally {
			XfsServiceManager.getInstance().free(wfsResult);
			wfsResult = null;
		}
	}

	public void cancel() throws XfsException {
		retainCardCommand.cancel();
	}

	@Override
	public void fireIntermediateEvent(final WFSResult wfsResult) {
		try {
			if (LOG.isDebugEnabled()) {
				final String method = "fireIntermediateEvent(WFSResult)";
				LOG.debug(method, "Intermediate event: " + wfsResult);
			}
		} finally {
			if (wfsResult != null) {
				XfsServiceManager.getInstance().free(wfsResult);
			}
		}
	}

	@Override
	public void fireOperationCompleteEvent(final WFSResult wfsResult) {
		final String method = "fireOperationCompleteEvent(WFSResult)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "wfsResult=" + wfsResult);
		}
		final IdcExecuteCommand commandCode = wfsResult.getCommandCode(IdcExecuteCommand.class);
		if (IdcExecuteCommand.RETAIN_CARD.equals(commandCode)) {
			synchronized (this) {
				this.wfsResult = wfsResult;
				notifyAll();
			}
		} else {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Unknown operation complete event: " + wfsResult);
			}
			XfsServiceManager.getInstance().free(wfsResult);
		}
	}

}
