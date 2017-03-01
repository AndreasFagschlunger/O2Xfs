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

import java.util.HashMap;
import java.util.Map;

import at.o2xfs.xfs.siu.SIUAmbientLightSensorState;
import at.o2xfs.xfs.siu.SIUAudioJackState;
import at.o2xfs.xfs.siu.SIUBootSwitchSensorState;
import at.o2xfs.xfs.siu.SIUConsumerDisplayState;
import at.o2xfs.xfs.siu.SIUGeneralPurposeInputPort;
import at.o2xfs.xfs.siu.SIUHandsetState;
import at.o2xfs.xfs.siu.SIUHeatSensorState;
import at.o2xfs.xfs.siu.SIUOperatorCallButtonState;
import at.o2xfs.xfs.siu.SIUOperatorSwitchState;
import at.o2xfs.xfs.siu.SIUProximitySensorState;
import at.o2xfs.xfs.siu.SIUSeismicSensorState;
import at.o2xfs.xfs.siu.SIUSensor;
import at.o2xfs.xfs.siu.SIUTamperSensorState;
import at.o2xfs.xfs.util.XfsConstants;

class SensorStateFactory implements StateFactory<SIUSensor> {

	@Override
	public Object createState(final SIUSensor sensor, final int state) {
		switch (sensor) {
			case OPERATORSWITCH:
				return XfsConstants
						.valueOf(state, SIUOperatorSwitchState.class);
			case TAMPER:
			case INTTAMPER:
				return XfsConstants.valueOf(state, SIUTamperSensorState.class);
			case SEISMIC:
				return XfsConstants.valueOf(state, SIUSeismicSensorState.class);
			case HEAT:
				return XfsConstants.valueOf(state, SIUHeatSensorState.class);
			case PROXIMITY:
				return XfsConstants.valueOf(state,
						SIUProximitySensorState.class);
			case AMBLIGHT:
				return XfsConstants.valueOf(state,
						SIUAmbientLightSensorState.class);
			case ENHANCEDAUDIO:
				return XfsConstants.valueOf(state, SIUAudioJackState.class);
			case BOOT_SWITCH:
				return XfsConstants.valueOf(state,
						SIUBootSwitchSensorState.class);
			case CONSUMER_DISPLAY:
				return XfsConstants.valueOf(state,
						SIUConsumerDisplayState.class);
			case OPERATOR_CALL_BUTTON:
				return XfsConstants.valueOf(state,
						SIUOperatorCallButtonState.class);
			case HANDSETSENSOR:
				return XfsConstants.valueOf(state, SIUHandsetState.class);
			case GENERALINPUTPORT:
				return createGeneralPurposeInputPortState(state);

		}
		throw new IllegalArgumentException("Illegal SIUSensor: " + sensor);
	}

	private Map<SIUGeneralPurposeInputPort, Boolean> createGeneralPurposeInputPortState(
			final int portState) {
		final Map<SIUGeneralPurposeInputPort, Boolean> portStates = new HashMap<SIUGeneralPurposeInputPort, Boolean>(
				SIUGeneralPurposeInputPort.values().length);
		for (final SIUGeneralPurposeInputPort inputPort : SIUGeneralPurposeInputPort
				.values()) {
			boolean turnedOn = false;
			if ((portState & inputPort.getValue()) == inputPort.getValue()) {
				turnedOn = true;
			}
			portStates.put(inputPort, Boolean.valueOf(turnedOn));
		}
		return portStates;
	}
}