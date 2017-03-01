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

package at.o2xfs.xfs.service;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsMessage;
import at.o2xfs.xfs.service.events.XfsEventNotification;

public class EventDispatcher {

	private final static Logger LOG = LoggerFactory
			.getLogger(EventDispatcher.class);

	public EventDispatcher() {
	}

	public void dispatch(final XfsMessage xfsMessage,
			final XfsEventNotification eventNotification,
			final WFSResult wfsResult) {
		final String method = "dispatch(XFSMessage, IXfsEventNotification, WFSResult)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "xfsMessage=" + xfsMessage
					+ ",eventNotification=" + eventNotification + ",wfsResult="
					+ wfsResult);
		}
		try {
			if (xfsMessage.isOperationComplete()) {
				eventNotification.fireOperationCompleteEvent(wfsResult);
			} else if (xfsMessage.isIntermediateEvent()) {
				eventNotification.fireIntermediateEvent(wfsResult);
			}
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error dispatching XfsEvent", e);
			}
		}
	}

	public void dispatch(final XfsMessage message, final XfsService xfsService,
			final WFSResult wfsResult) {
		try {
			switch (message) {
				case WFS_SERVICE_EVENT:
					xfsService.fireServiceEvent(wfsResult);
					break;
				case WFS_USER_EVENT:
					xfsService.fireUserEvent(wfsResult);
					break;
				case WFS_SYSTEM_EVENT:
					xfsService.fireSystemEvent(wfsResult);
					break;
			}
		} finally {
			XfsServiceManager.getInstance().free(wfsResult);
		}
	}
}