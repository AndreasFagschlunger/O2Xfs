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

package at.o2xfs.xfs.service.cmd.idc;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.DWORD;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.ZList;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsCancelledException;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.idc.IDCExecuteCommand;
import at.o2xfs.xfs.idc.IDCMessage;
import at.o2xfs.xfs.idc.IDCTrack;
import at.o2xfs.xfs.idc.WFSIDCCARDDATA;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.AbstractAsyncCommand;
import at.o2xfs.xfs.service.cmd.EventQueue;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.events.XfsEventNotification;
import at.o2xfs.xfs.service.idc.IDCService;
import at.o2xfs.xfs.util.Bitmask;

public class ReadCardCommand extends AbstractAsyncCommand<ReadCardListener>
		implements XfsEventNotification {

	private final static Logger LOG = LoggerFactory
			.getLogger(ReadCardCommand.class);

	private final XfsServiceManager serviceManager = XfsServiceManager
			.getInstance();

	private final IDCService idcService;

	private Set<IDCTrack> readData = null;

	private XfsCommand xfsCommand = null;

	private final EventQueue eventQueue;

	public ReadCardCommand(final IDCService idcService) {
		if (idcService == null) {
			throw new IllegalArgumentException("idcService must not be null");
		}
		this.idcService = idcService;
		eventQueue = new EventQueue(this);
		readData = EnumSet.noneOf(IDCTrack.class);
	}

	public void addReadData(final IDCTrack idcTrack) {
		readData.add(idcTrack);
	}

	private void notifyCardInserted() {
		for (final ReadCardListener listener : commandListeners) {
			listener.cardInserted();
		}
	}

	private void notifyCardInvalid() {
		for (final ReadCardListener listener : commandListeners) {
			listener.cardInvalid();
		}
	}

	private void notifyCardRead(final List<WFSIDCCARDDATA> cardData) {
		for (final ReadCardListener listener : commandListeners) {
			listener.cardRead(cardData);

		}
	}

	@Override
	protected void executeInternal() {
		final String method = "executeInternal()";
		xfsCommand = new XfsExecuteCommand(idcService,
				IDCExecuteCommand.WFS_CMD_IDC_READ_RAW_DATA, new DWORD(
						Bitmask.of(readData)));
		try {
			xfsCommand.execute(eventQueue);
		} catch (final XfsException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error executing XfsCommand: " + xfsCommand,
						e);
			}
			notifyCommandFailed(e);
		}
	}

	@Override
	public void cancel() {
		if (xfsCommand == null) {
			return;
		}
		try {
			xfsCommand.cancel();
		} catch (final XfsException e) {
			final String method = "cancel()";
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "", e);
			}
		}
	}

	@Override
	public void fireIntermediateEvent(final WFSResult wfsResult) {
		final String method = "fireIntermediateEvent(WFSResult)";
		try {
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "wfsResult=" + wfsResult);
			}
			final IDCMessage message = wfsResult.getEventID(IDCMessage.class);
			switch (message) {
				case WFS_EXEE_IDC_MEDIAINSERTED:
					notifyCardInserted();
					break;
				case WFS_EXEE_IDC_INVALIDMEDIA:
					notifyCardInvalid();
					break;
				default:
					if (LOG.isWarnEnabled()) {
						LOG.warn(method, "Unknown event: " + wfsResult);
					}
			}
		} finally {
			serviceManager.free(wfsResult);
		}
	}

	@Override
	public void fireOperationCompleteEvent(final WFSResult wfsResult) {
		final String method = "fireOperationCompleteEvent(WFSResult)";
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "wfsResult=" + wfsResult);
			}
			XfsException.throwFor(wfsResult.getResult());
			final List<WFSIDCCARDDATA> cardDataList = new ArrayList<WFSIDCCARDDATA>();
			final ZList zList = new ZList(wfsResult.getBuffer());
			for (final Pointer pCardData : zList) {
				final WFSIDCCARDDATA cardData = new WFSIDCCARDDATA(
						idcService.getXfsVersion(), pCardData);
				if (LOG.isDebugEnabled()) {
					LOG.debug(method, "cardData=" + cardData);
				}
				cardDataList.add(new WFSIDCCARDDATA(idcService.getXfsVersion(),
						cardData));
			}
			notifyCardRead(cardDataList);
			notifyCommandSuccessful();
		} catch (XfsCancelledException e) {
			notifyCommandCancelled();
		} catch (XfsException e) {
			notifyCommandFailed(e);
		} finally {
			serviceManager.free(wfsResult);
		}
	}
}
