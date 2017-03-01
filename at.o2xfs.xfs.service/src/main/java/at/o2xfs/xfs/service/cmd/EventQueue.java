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

package at.o2xfs.xfs.service.cmd;

import java.util.ArrayList;
import java.util.List;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.service.events.XfsEventNotification;

/**
 * @author Andreas Fagschlunger
 */
public class EventQueue implements XfsEventNotification {

	private static final Logger LOG = LoggerFactory.getLogger(EventQueue.class);

	private final Thread dispatchThread;

	private final List<WFSResult> intermediateEvents;

	private final XfsEventNotification eventNotification;

	private WFSResult operationCompleteEvent = null;

	public EventQueue(final XfsEventNotification eventNotification) {
		if (eventNotification == null) {
			throw new IllegalArgumentException();
		}
		this.eventNotification = eventNotification;
		intermediateEvents = new ArrayList<WFSResult>();
		dispatchThread = new Thread(new Runnable() {

			@Override
			public void run() {
				waitAndDispatch();
			}
		});
		dispatchThread.start();
	}

	private void waitAndDispatch() {
		final String method = "waitAndDispatch()";
		try {
			while (!dispatchThread.isInterrupted()) {
				WFSResult intermediateEvent = null;
				synchronized (this) {
					while (intermediateEvents.isEmpty()
							&& operationCompleteEvent == null) {
						if (LOG.isDebugEnabled()) {
							LOG.debug(method, "Waiting ...");
						}
						wait();
					}
					if (!intermediateEvents.isEmpty()) {
						intermediateEvent = intermediateEvents.remove(0);
					}
				}
				if (intermediateEvent != null) {
					eventNotification.fireIntermediateEvent(intermediateEvent);
				} else {
					eventNotification
							.fireOperationCompleteEvent(operationCompleteEvent);
					return;
				}
			}
		} catch (final InterruptedException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Interrupted while waiting", e);
			}
		}
	}

	@Override
	public void fireIntermediateEvent(final WFSResult intermediateEvent) {
		synchronized (this) {
			intermediateEvents.add(intermediateEvent);
			notifyAll();
		}
	}

	@Override
	public void fireOperationCompleteEvent(final WFSResult ocEvent) {
		synchronized (this) {
			operationCompleteEvent = ocEvent;
			notifyAll();
		}
	}
}