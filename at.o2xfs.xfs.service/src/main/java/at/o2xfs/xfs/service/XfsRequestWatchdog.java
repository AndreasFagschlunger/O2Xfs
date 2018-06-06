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
import java.util.Iterator;
import java.util.List;

import at.o2xfs.common.Assert;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.XfsAPI;
import at.o2xfs.xfs.type.RequestId;

public class XfsRequestWatchdog {

	private static final Logger LOG = LoggerFactory.getLogger(XfsRequestWatchdog.class);

	private class Request {

		private static final long EXTRA_TIME = 10L * 1000L;

		private final long startTime;
		private final RequestId requestId;
		private final long timeOut;
		private boolean cancelled = false;

		private Request(RequestId requestId, long timeOut) {
			startTime = System.currentTimeMillis();
			this.requestId = requestId;
			this.timeOut = timeOut;
		}

		private long getTimeoutTime() {
			if (cancelled) {
				return startTime + (timeOut * 2) + EXTRA_TIME;
			}
			return startTime + timeOut + EXTRA_TIME;
		}
	}

	private final RequestQueue requestQueue;

	private final List<Request> requests;

	private Thread thread;

	public XfsRequestWatchdog(RequestQueue requestQueue) {
		Assert.notNull(requestQueue);
		this.requestQueue = requestQueue;
		requests = new ArrayList<XfsRequestWatchdog.Request>();
	}

	public void start() {
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				watchOut();
			}
		});
		thread.start();
	}

	public void stop() {
		if (thread != null) {
			thread.interrupt();
		}
	}

	private void watchOut() {
		final String method = "watchOut()";
		try {
			while (!thread.isInterrupted()) {
				synchronized (requests) {
					if (requests.isEmpty()) {
						if (LOG.isDebugEnabled()) {
							LOG.debug(method, "Waiting for requests ...");
						}
						requests.wait();
					} else {
						long waitTime = nextTimeout() - System.currentTimeMillis();
						if (LOG.isDebugEnabled()) {
							LOG.debug(method, "Waiting for " + waitTime + " milliseconds");
						}
						if (waitTime > 0) {
							requests.wait(waitTime);
						}
					}
					checkRequests();
				}
			}
		} catch (InterruptedException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "Interrupted", e);
			}
		}
	}

	private long nextTimeout() {
		long result = requests.get(0).getTimeoutTime();
		for (Request request : requests) {
			result = Math.min(result, request.getTimeoutTime());
		}
		return result;
	}

	private void checkRequests() {
		final String method = "checkRequests()";
		final long currentTime = System.currentTimeMillis();
		for (Iterator<Request> i = requests.iterator(); i.hasNext();) {
			Request request = i.next();
			if (request.getTimeoutTime() > currentTime) {
				continue;
			}
			long activeTime = request.getTimeoutTime() - request.startTime;
			if (request.cancelled) {
				if (LOG.isWarnEnabled()) {
					LOG.warn(method,
							"Request " + request.requestId + " has been cancelled but is still active, terminate ...");
				}
				i.remove();
				requestQueue.terminateRequest(request.requestId);
			} else {
				if (LOG.isWarnEnabled()) {
					LOG.warn(method, "Request " + request.requestId + " has been active for " + activeTime
							+ " milliseconds and may be hung, try cancel ...");
				}
				request.cancelled = true;
				requestQueue.cancelRequest(request.requestId);
			}
		}
	}

	public void remove(RequestId requestId) {
		final String method = "remove(RequestId)";
		synchronized (requests) {
			for (Request request : requests) {
				if (request.requestId.equals(requestId)) {
					if (LOG.isDebugEnabled()) {
						LOG.debug(method, "Remove RequestId: " + request.requestId);
					}
					requests.remove(request);
					return;
				}
			}
		}
	}

	public void add(RequestId requestId, Long timeOut) {
		final String method = "add(RequestId, Long)";
		if (timeOut == null || timeOut.longValue() == XfsAPI.WFS_INDEFINITE_WAIT) {
			return;
		}
		synchronized (requests) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "Add RequestId: " + requestId);
			}
			requests.add(new Request(requestId, timeOut));
			requests.notifyAll();
		}
	}
}