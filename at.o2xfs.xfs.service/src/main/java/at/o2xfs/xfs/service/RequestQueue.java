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

import java.util.ArrayList;
import java.util.List;

import at.o2xfs.common.Assert;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.DWORD;
import at.o2xfs.win32.INT;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsConstant;
import at.o2xfs.xfs.XfsError;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.XfsMessage;
import at.o2xfs.xfs.service.cmd.XfsCloseCommand;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsDeRegisterCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.cmd.XfsInfoCommand;
import at.o2xfs.xfs.service.cmd.XfsOpenCommand;
import at.o2xfs.xfs.service.cmd.XfsRegisterCommand;
import at.o2xfs.xfs.service.events.XfsEventNotification;
import at.o2xfs.xfs.type.RequestId;

public class RequestQueue {

	private static final Logger LOG = LoggerFactory.getLogger(RequestQueue.class);

	private class Request {

		private final RequestId requestId;

		private final XfsEventNotification eventNotification;

		private final XfsCommand command;

		private final Long timeOut;

		public Request(RequestId requestId, XfsEventNotification eventNotification, XfsCommand command, Long timeOut) {
			Assert.notNull(requestId);
			Assert.notNull(eventNotification);
			Assert.notNull(command);
			this.requestId = requestId;
			this.eventNotification = eventNotification;
			this.command = command;
			this.timeOut = timeOut;
		}
	}

	private final List<Request> requests;

	private final XfsRequestWatchdog watchdog;

	public RequestQueue() {
		requests = new ArrayList<RequestQueue.Request>();
		watchdog = new XfsRequestWatchdog(this);
		watchdog.start();
	}

	void cancelRequest(RequestId requestId) {
		final String method = "cancelRequest(RequestId)";
		XfsCommand command = getRequestById(requestId).command;
		try {
			command.cancel();
		} catch (XfsException e) {
			LOG.error(method, "Error cancelling XfsCommand: " + command, e);
		}
	}

	void terminateRequest(RequestId requestId) {
		final String method = "terminateRequest(RequestId)";
		synchronized (requests) {
			Request request = getRequestById(requestId);
			requests.remove(request);
			WFSResult result = new WFSResult(requestId, request.command.getXFSService().getService(),
					new INT((int) XfsError.CANCELED.getValue()), new DWORD(commandCode(request.command).getValue()));
			if (LOG.isWarnEnabled()) {
				LOG.warn(method, "Firing operation complete event: " + result);
			}
			request.eventNotification.fireOperationCompleteEvent(result);
		}
	}

	private XfsConstant commandCode(XfsCommand command) {
		if (command instanceof XfsOpenCommand) {
			return XfsMessage.WFS_OPEN_COMPLETE;
		} else if (command instanceof XfsCloseCommand) {
			return XfsMessage.WFS_CLOSE_COMPLETE;
		} else if (command instanceof XfsRegisterCommand) {
			return XfsMessage.WFS_REGISTER_COMPLETE;
		} else if (command instanceof XfsDeRegisterCommand) {
			return XfsMessage.WFS_DEREGISTER_COMPLETE;
		} else if (command instanceof XfsInfoCommand) {
			return (XfsConstant) ((XfsInfoCommand) command).getCategory();
		} else if (command instanceof XfsExecuteCommand) {
			return (XfsConstant) ((XfsExecuteCommand) command).getCommand();
		} else {
			throw new IllegalArgumentException("XfsCommand: " + command);
		}
	}

	public void addRequest(RequestId requestId, XfsEventNotification eventNotification, XfsCommand command,
			Long timeOut) {
		synchronized (requests) {
			requests.add(new Request(requestId, eventNotification, command, timeOut));
			watchdog.add(requestId, timeOut);
			requests.notifyAll();
		}
	}

	private Request getRequestById(RequestId requestId) {
		for (Request request : requests) {
			if (request.requestId.equals(requestId)) {
				return request;
			}
		}
		return null;
	}

	public void removeRequest(RequestId requestId) {
		synchronized (requests) {
			Assert.assertTrue(requests.remove(getRequestById(requestId)));
			watchdog.remove(requestId);
		}
	}

	public XfsEventNotification getEventNotification(RequestId requestId) {
		final String method = "getEventNotification(RequestId)";
		Assert.notNull(requestId);
		Request request = null;
		synchronized (requests) {
			while ((request = getRequestById(requestId)) == null) {
				try {
					if (LOG.isDebugEnabled()) {
						LOG.debug(method, "Waiting for RequestId: " + requestId);
					}
					requests.wait();
				} catch (InterruptedException e) {
					if (LOG.isErrorEnabled()) {
						LOG.error(method, "Interrupted", e);
					}
					throw new RuntimeException(e);
				}
			}
		}
		return request.eventNotification;
	}

	public void close() {
		watchdog.stop();
	}
}