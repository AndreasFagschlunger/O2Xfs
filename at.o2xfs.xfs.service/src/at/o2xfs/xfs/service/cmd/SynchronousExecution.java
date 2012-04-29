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

package at.o2xfs.xfs.service.cmd;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.events.XfsEventNotification;

/**
 * @author Andreas Fagschlunger
 * 
 */
class SynchronousExecution implements XfsEventNotification {

	private final static Logger LOG = LoggerFactory
			.getLogger(SynchronousExecution.class);

	private XfsCommand xfsCommand = null;

	private WFSResult wfsResult = null;

	public SynchronousExecution(final XfsCommand xfsCommand) {
		this.xfsCommand = xfsCommand;
	}

	public synchronized WFSResult execute() throws XfsException {
		final String method = "execute()";
		xfsCommand.execute(this);
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "Waiting...");
			}
			wait();
			try {
				XfsException.throwFor(wfsResult.getResult());
			} catch (XfsException e) {
				XfsServiceManager.getInstance().free(wfsResult);
				throw e;
			}
		} catch (InterruptedException e) {
			LOG.error(method,
					"Interrupted while waiting for operation to complete", e);
		}
		return wfsResult;
	}

	@Override
	public void fireIntermediateEvent(WFSResult wfsResult) {
		final String method = "fireIntermediateEvent(WFSResult)";
		if (LOG.isWarnEnabled()) {
			LOG.warn(method, "Intermediate event occurred: " + wfsResult);
		}
		XfsServiceManager.getInstance().free(wfsResult);
	}

	/**
	 * @see at.o2xfs.xfs.service.events.XfsEventNotification#fireOperationCompleteEvent
	 *      (at.o2xfs.xfs.WFSResult)
	 */
	@Override
	public void fireOperationCompleteEvent(final WFSResult wfsResult) {
		final String method = "fireOperationCompleteEvent(WFSResult)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "wfsResult=" + wfsResult);
		}
		this.wfsResult = wfsResult;
		synchronized (this) {
			notify();
		}
	}

}
