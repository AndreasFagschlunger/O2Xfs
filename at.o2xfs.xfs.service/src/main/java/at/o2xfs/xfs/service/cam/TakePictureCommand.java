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

import at.o2xfs.common.Assert;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.cam.CamEventId;
import at.o2xfs.xfs.cam.CamExecuteCommand;
import at.o2xfs.xfs.cam.Takepict;
import at.o2xfs.xfs.service.EmptyCompleteEvent;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.AbstractAsyncXfsCommand;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;

public class TakePictureCommand
		extends AbstractAsyncXfsCommand<TakePictureListener, EmptyCompleteEvent> {

	private static final Logger LOG = LoggerFactory.getLogger(TakePictureCommand.class);

	final CamService service;
	final Takepict takepict;

	public TakePictureCommand(CamService service, Takepict takepict) {
		Assert.notNull(service);
		this.service = service;
		this.takepict = takepict;
	}

	@Override
	protected XfsCommand createCommand() {
		return new XfsExecuteCommand<CamExecuteCommand>(service, CamExecuteCommand.TAKE_PICTURE, takepict);
	}

	@Override
	public void fireIntermediateEvent(WFSResult wfsResult) {
		final String method = "fireIntermediateEvent(WFSResult)";
		try {
			CamEventId eventId = wfsResult.getEventID(CamEventId.class);
			switch (eventId) {
				case INVALIDDATA:
					notifyInvalidData();
					break;
				default:
					LOG.error(method, "Illegal CamEventId: " + eventId);
			}
		} finally {
			XfsServiceManager.getInstance().free(wfsResult);
		}
	}

	private void notifyInvalidData() {
		final String method = "notifyInvalidData()";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, Integer.valueOf(listeners.size()));
		}
		for (TakePictureListener l : listeners) {
			l.onInvalidData();
		}
	}

	@Override
	protected EmptyCompleteEvent createCompleteEvent(Pointer results) {
		return new EmptyCompleteEvent();
	}
}