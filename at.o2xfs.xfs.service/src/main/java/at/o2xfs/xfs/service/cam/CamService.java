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

package at.o2xfs.xfs.service.cam;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.cam.CamEventId;
import at.o2xfs.xfs.cam.Media;
import at.o2xfs.xfs.service.XfsService;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.win32.XfsWord;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CamService
		extends XfsService {

	private static final Logger LOG = LoggerFactory.getLogger(CamService.class);

	private final List<CamServiceListener> listeners;

	protected CamService(String logicalName) {
		super(logicalName, XfsServiceClass.CAM);
		listeners = new CopyOnWriteArrayList<>();
	}

	public void add(CamServiceListener l) {
		listeners.add(l);
	}

	public void remove(CamServiceListener l) {
		listeners.remove(l);
	}

	@Override
	public void fireServiceEvent(WFSResult wfsResult) {

	}

	@Override
	public void fireUserEvent(WFSResult wfsResult) {
		final String method = "fireUserEvent(WFSResult)";
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "wfsResult=" + wfsResult);
			}
			CamEventId eventId = wfsResult.getEventID(CamEventId.class);
			switch (eventId) {
				case MEDIATHRESHOLD:
					XfsWord<Media> threshold = new XfsWord<>(Media.class, wfsResult.getResults());
					notifyMediaThreshold(threshold.get());
					break;
				default:
					LOG.error(method, "Illegal CamEventId: " + eventId);

			}
		} finally {
			XfsServiceManager.getInstance().free(wfsResult);
		}
	}

	private void notifyMediaThreshold(Media threshold) {
		for (CamServiceListener each : listeners) {
			each.onMediaThreshold(threshold);
		}
	}
}