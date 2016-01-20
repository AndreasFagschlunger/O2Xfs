/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.xfs.siu;

import at.o2xfs.xfs.XfsConstant;
import at.o2xfs.xfs.util.XfsConstants;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SIUSensorsStatus {

	private final int[] sensors;

	protected SIUSensorsStatus(final int[] sensors) {
		if (sensors == null) {
			throw new IllegalArgumentException("sensors must not be null");
		} else if (sensors.length != SIUConstant.SENSORS_SIZE) {
			throw new IllegalArgumentException("sensors.length != " + SIUConstant.SENSORS_SIZE);
		}
		this.sensors = sensors;
	}

	private <E extends Enum<E> & XfsConstant> E getState(final SIUSensor sensor, final Class<E> type) {
		final int value = sensors[sensor.intValue()];
		return XfsConstants.valueOf(value, type);
	}

	public SIUOperatorSwitchState getOperatorSwitchState() {
		return getState(SIUSensor.OPERATORSWITCH, SIUOperatorSwitchState.class);
	}

	public SIUTamperSensorState getTamperSensorState() {
		return getState(SIUSensor.TAMPER, SIUTamperSensorState.class);
	}

	public SIUTamperSensorState getInternalTamperSensorState() {
		return getState(SIUSensor.INTTAMPER, SIUTamperSensorState.class);
	}

	public SIUSeismicSensorState getSeismicSensorState() {
		return getState(SIUSensor.SEISMIC, SIUSeismicSensorState.class);
	}

	public SIUHeatSensorState getHeatSensorState() {
		return getState(SIUSensor.HEAT, SIUHeatSensorState.class);
	}

	public SIUProximitySensorState getProximitySensorState() {
		return getState(SIUSensor.PROXIMITY, SIUProximitySensorState.class);
	}

	public SIUAmbientLightSensorState getAmbientLightSensorState() {
		return getState(SIUSensor.AMBLIGHT, SIUAmbientLightSensorState.class);
	}

	public SIUAudioJackState getAudioJackState() {
		return getState(SIUSensor.ENHANCEDAUDIO, SIUAudioJackState.class);
	}

	public SIUBootSwitchSensorState getBootSwitchSensorState() {
		return getState(SIUSensor.BOOT_SWITCH, SIUBootSwitchSensorState.class);
	}

	public SIUConsumerDisplayState getConsumerDisplayState() {
		return getState(SIUSensor.CONSUMER_DISPLAY, SIUConsumerDisplayState.class);
	}

	public SIUOperatorCallButtonState getOperatorCallButtonState() {
		return getState(SIUSensor.OPERATOR_CALL_BUTTON, SIUOperatorCallButtonState.class);
	}

	public SIUHandsetState getHandsetState() {
		return getState(SIUSensor.HANDSETSENSOR, SIUHandsetState.class);
	}

	public Map<SIUGeneralPurposeInputPort, Boolean> getGeneralPurposeInputPortStates() {
		final Map<SIUGeneralPurposeInputPort, Boolean> portStates = new HashMap<SIUGeneralPurposeInputPort, Boolean>(SIUGeneralPurposeInputPort.values().length);
		final int value = sensors[SIUSensor.GENERALINPUTPORT.intValue()];
		for (final SIUGeneralPurposeInputPort inputPort : SIUGeneralPurposeInputPort.values()) {
			boolean turnedOn = false;
			if ((value & inputPort.getValue()) == inputPort.getValue()) {
				turnedOn = true;
			}
			portStates.put(inputPort, Boolean.valueOf(turnedOn));
		}
		return portStates;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("operatorSwitchState", getOperatorSwitchState())
										.append("tamperSensorState", getTamperSensorState())
										.append("internalTamperSensorState", getInternalTamperSensorState())
										.append("seismicSensorState", getSeismicSensorState())
										.append("heatSensorState", getHeatSensorState())
										.append("proximitySensorState", getProximitySensorState())
										.append("ambientLightSensorState", getAmbientLightSensorState())
										.append("audioJackState", getAudioJackState())
										.append("bootSwitchSensorState", getBootSwitchSensorState())
										.append("consumerDisplayState", getConsumerDisplayState())
										.append("operatorCallButtonState", getOperatorCallButtonState())
										.append("handsetState", getHandsetState())
										.append("generalPurposeInputPortStates", getGeneralPurposeInputPortStates())
										.toString();
	}
}