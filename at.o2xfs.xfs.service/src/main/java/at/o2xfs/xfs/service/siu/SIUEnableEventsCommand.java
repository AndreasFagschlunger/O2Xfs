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

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cmd.AbstractAsyncCommand;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.events.XfsEventNotification;
import at.o2xfs.xfs.service.util.ExceptionUtil;
import at.o2xfs.xfs.siu.SIUAuxiliary;
import at.o2xfs.xfs.siu.SIUCapabilities;
import at.o2xfs.xfs.siu.SIUDoor;
import at.o2xfs.xfs.siu.SIUEnableEvents;
import at.o2xfs.xfs.siu.SIUExecuteCommand;
import at.o2xfs.xfs.siu.SIUGuidLight;
import at.o2xfs.xfs.siu.SIUIndicator;
import at.o2xfs.xfs.siu.SIUMessage;
import at.o2xfs.xfs.siu.SIUPortError;
import at.o2xfs.xfs.siu.SIUSensor;

public class SIUEnableEventsCommand extends
		AbstractAsyncCommand<SIUEnableEventsListener> implements
		XfsEventNotification {

	private static final Logger LOG = LoggerFactory
			.getLogger(SIUEnableEventsCommand.class);

	private final SIUService siuService;

	public SIUEnableEventsCommand(final SIUService siuService) {
		if (siuService == null) {
			ExceptionUtil.nullArgument("siuService");
		}
		this.siuService = siuService;
	}

	private void notifyPortError(final SIUPortError portError) {
		for (final SIUEnableEventsListener l : commandListeners) {
			l.portError(new SIUPortError(portError));
		}
	}

	@Override
	protected void doExecute() {
		try {
			final SIUCapabilities caps = new SIUCapabilitiesCallable(siuService)
					.call();
			final SIUEnableEvents enableEvents = new SIUEnableEvents();
			enableEvents.allocate();
			for (final SIUSensor sensor : SIUSensor.values()) {
				if (caps.isSensorAvailable(sensor)) {
					enableEvents.set(sensor).enableEvent();
				}
			}
			for (final SIUDoor door : SIUDoor.values()) {
				if (caps.isDoorAvailable(door)) {
					enableEvents.set(door).enableEvent();
				}
			}
			for (final SIUIndicator indicator : SIUIndicator.values()) {
				if (caps.isIndicatorAvailable(indicator)) {
					enableEvents.set(indicator).enableEvent();
				}
			}
			for (final SIUAuxiliary auxiliary : SIUAuxiliary.values()) {
				if (caps.isAuxiliaryAvailable(auxiliary)) {
					enableEvents.set(auxiliary).enableEvent();
				}
			}
			for (final SIUGuidLight guidLight : SIUGuidLight.values()) {
				if (caps.isGuidLightAvailable(guidLight)) {
					enableEvents.set(guidLight).enableEvent();
				}
			}
			final XfsCommand xfsCommand = new XfsExecuteCommand(siuService,
					SIUExecuteCommand.ENABLE_EVENTS, enableEvents);
			xfsCommand.execute(this);
		} catch (final Exception e) {
			notifyCommandFailed(e);
		}
	}

	@Override
	public void cancel() {
	}

	@Override
	public void fireIntermediateEvent(final WFSResult wfsResult) {
		try {
			final String method = "fireIntermediateEvent(WFSResult)";
			final SIUMessage message = wfsResult.getEventID(SIUMessage.class);
			switch (message) {
				case PORT_ERROR:
					final SIUPortError portError = new SIUPortError(
							wfsResult.getResults());
					if (LOG.isInfoEnabled()) {
						LOG.info(method, portError);
					}
					notifyPortError(portError);
					break;
				default:
					if (LOG.isWarnEnabled()) {
						LOG.warn(method, "Unknown intermediate event: "
								+ wfsResult);
					}
			}
		} finally {
			if (wfsResult != null) {
				XfsServiceManager.getInstance().free(wfsResult);
			}
		}
	}

	@Override
	public void fireOperationCompleteEvent(final WFSResult wfsResult) {
		final String method = "fireOperationCompleteEvent(WFSResult)";
		try {
			XfsException.throwFor(wfsResult.getResult());
			notifyCommandSuccessful();
		} catch (final XfsException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Command failed", e);
			}
			notifyCommandFailed(e);
		} finally {
			if (wfsResult != null) {
				XfsServiceManager.getInstance().free(wfsResult);
			}
		}
	}
}