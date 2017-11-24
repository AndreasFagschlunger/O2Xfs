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

package at.o2xfs.xfs.service.idc;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.idc.IdcMessage;
import at.o2xfs.xfs.v3_00.idc.CardAction3;
import at.o2xfs.xfs.service.XfsService;

public class IDCService extends XfsService {

	private final static Logger LOG = LoggerFactory.getLogger(IDCService.class);

	private List<IDCServiceListener> serviceListeners = null;

	public IDCService(final String logicalName) {
		super(logicalName, XfsServiceClass.IDC);
		serviceListeners = new CopyOnWriteArrayList<IDCServiceListener>();
	}

	private void notifyCardTaken() {
		for (IDCServiceListener listener : serviceListeners) {
			listener.cardTaken();
		}
	}

	public void addIDCServiceListener(final IDCServiceListener listener) {
		serviceListeners.add(listener);
	}

	public void removeIDCServiceListener(final IDCServiceListener listener) {
		serviceListeners.remove(listener);
	}

	@Override
	public void fireServiceEvent(final WFSResult wfsResult) {
		final String method = "fireServiceEvent(WFSResult)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "wfsResult=" + wfsResult);
		}
		final IdcMessage idcMessage = wfsResult.getEventID(IdcMessage.class);
		switch (idcMessage) {
		case SRVE_MEDIAREMOVED:
			notifyCardTaken();
			break;
		case SRVE_CARDACTION:
			fireCardActionEvent(wfsResult);
			break;
		}
	}

	private void fireCardActionEvent(final WFSResult wfsResult) {
		final String method = "fireCardActionEvent(WFSResult)";
		final CardAction3 cardAction = IdcFactory.create(getXfsVersion(), wfsResult.getResults(), CardAction3.class);
		if (LOG.isInfoEnabled()) {
			LOG.info(method, cardAction);
		}
		for (final IDCServiceListener listener : serviceListeners) {
			listener.cardAction(cardAction);
		}
	}

	@Override
	public void fireUserEvent(final WFSResult wfsResult) {
		final String method = "fireUserEvent(WFSResult)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "wfsResult=" + wfsResult);
		}
	}

}
