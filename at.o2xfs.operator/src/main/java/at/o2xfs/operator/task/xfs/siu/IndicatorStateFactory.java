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

package at.o2xfs.operator.task.xfs.siu;

import java.util.EnumMap;
import java.util.Map;

import at.o2xfs.xfs.siu.SIUAudioIndicatorState;
import at.o2xfs.xfs.siu.SIUConsumerDisplayState;
import at.o2xfs.xfs.siu.SIUFasciaLightState;
import at.o2xfs.xfs.siu.SIUGeneralPurposeOutputPort;
import at.o2xfs.xfs.siu.SIUHeatSensorState;
import at.o2xfs.xfs.siu.SIUIndicator;
import at.o2xfs.xfs.siu.SIULamp;
import at.o2xfs.xfs.siu.SIUOpenClosedIndicatorState;
import at.o2xfs.xfs.siu.SIUSignageDisplayState;
import at.o2xfs.xfs.util.XfsConstants;

class IndicatorStateFactory implements StateFactory<SIUIndicator> {

	@Override
	public Object createState(final SIUIndicator indicator, final int portStatus) {
		switch (indicator) {
			case OPENCLOSE:
				return XfsConstants.valueOf(portStatus,
						SIUOpenClosedIndicatorState.class);
			case FASCIALIGHT:
				return XfsConstants.valueOf(portStatus,
						SIUFasciaLightState.class);
			case AUDIO:
				return XfsConstants
						.of(portStatus, SIUAudioIndicatorState.class);
			case HEATING:
				return XfsConstants.valueOf(portStatus,
						SIUHeatSensorState.class);
			case CONSUMER_DISPLAY_BACKLIGHT:
				return XfsConstants.valueOf(portStatus,
						SIUConsumerDisplayState.class);
			case SIGNAGEDISPLAY:
				return XfsConstants.valueOf(portStatus,
						SIUSignageDisplayState.class);
			case TRANSINDICATOR:
				return createTransactionIndicatorsState(portStatus);
			case GENERALOUTPUTPORT:
				return createOutputPortsState(portStatus);
		}
		throw new IllegalArgumentException("SIUIndicator: " + indicator);
	}

	private Map<SIULamp, Boolean> createTransactionIndicatorsState(
			final int portStatus) {
		final Map<SIULamp, Boolean> states = new EnumMap<SIULamp, Boolean>(
				SIULamp.class);
		for (final SIULamp lamp : SIULamp.values()) {
			final boolean turnedOn = (portStatus & lamp.getValue()) == lamp
					.getValue();
			states.put(lamp, Boolean.valueOf(turnedOn));
		}
		return states;
	}

	private Map<SIUGeneralPurposeOutputPort, Boolean> createOutputPortsState(
			final int portStatus) {
		final Map<SIUGeneralPurposeOutputPort, Boolean> portStates = new EnumMap<SIUGeneralPurposeOutputPort, Boolean>(
				SIUGeneralPurposeOutputPort.class);
		for (final SIUGeneralPurposeOutputPort port : SIUGeneralPurposeOutputPort
				.values()) {
			final boolean turnedOn = (portStatus & port.getValue()) == port
					.getValue();
			portStates.put(port, Boolean.valueOf(turnedOn));
		}
		return portStates;
	}
}