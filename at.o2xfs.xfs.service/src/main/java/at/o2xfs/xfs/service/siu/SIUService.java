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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.service.XfsService;
import at.o2xfs.xfs.siu.SIUMessage;
import at.o2xfs.xfs.siu.SIUPortEvent;

public class SIUService extends XfsService {

	private static final Logger LOG = LoggerFactory.getLogger(SIUService.class);

	private final List<SIUServiceListener> serviceListeners;

	public SIUService(final String logicalName) {
		super(logicalName, XfsServiceClass.SIU);
		serviceListeners = new CopyOnWriteArrayList<SIUServiceListener>();
	}

	private void notifyPortStatus(final SIUPortEvent portEvent) {
		for (final SIUServiceListener l : serviceListeners) {
			l.portStatus(new SIUPortEvent(portEvent));
		}
	}

	public void addServiceListener(final SIUServiceListener listener) {
		serviceListeners.add(listener);
	}

	public void removeServiceListener(final SIUServiceListener listener) {
		serviceListeners.remove(listener);
	}

	@Override
	public void fireServiceEvent(final WFSResult wfsResult) {
		final String method = "fireServiceEvent(WFSResult)";
		final SIUMessage eventID = wfsResult.getEventID(SIUMessage.class);
		switch (eventID) {
			case PORT_STATUS:
				final SIUPortEvent portEvent = new SIUPortEvent(
						wfsResult.getResults());
				if (LOG.isInfoEnabled()) {
					LOG.info(method, portEvent);
				}
				notifyPortStatus(portEvent);
				break;
			default:
				if (LOG.isWarnEnabled()) {
					LOG.warn(method, "Unknown service event: " + wfsResult);
				}
		}
	}

	@Override
	public void fireUserEvent(final WFSResult wfsResult) {
		final String method = "fireUserEvent(WFSResult)";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, wfsResult);
		}
	}

}