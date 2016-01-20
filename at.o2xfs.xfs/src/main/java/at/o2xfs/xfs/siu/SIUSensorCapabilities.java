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

import static at.o2xfs.xfs.siu.SIUConstant.NOT_AVAILABLE;

import at.o2xfs.xfs.util.XfsConstants;

import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SIUSensorCapabilities {

	private final int[] sensors;

	public SIUSensorCapabilities(final int[] sensors) {
		this.sensors = sensors;
	}

	private int getSensor(final SIUSensor sensor) {
		return sensors[(int) sensor.getValue()];
	}

	private boolean isAvailable(final SIUSensor sensor) {
		return getSensor(sensor) != NOT_AVAILABLE;
	}

	public boolean isOperatorSwitchAvailable() {
		return isAvailable(SIUSensor.OPERATORSWITCH);
	}

	public Set<SIUOperatorSwitchMode> getOperatorSwitch() {
		final int operatorSwitch = getSensor(SIUSensor.OPERATORSWITCH);
		return XfsConstants.of(operatorSwitch, SIUOperatorSwitchMode.class);
	}

	public boolean isTamperSensorAvailable() {
		return isAvailable(SIUSensor.TAMPER);
	}

	public boolean isInternalTamperSensorAvailable() {
		return isAvailable(SIUSensor.INTTAMPER);
	}

	public boolean isSeismicSensorAvailable() {
		return isAvailable(SIUSensor.SEISMIC);
	}

	public boolean isHeatSensorAvailable() {
		return isAvailable(SIUSensor.HEAT);
	}

	public boolean isProximitySensorAvailable() {
		return isAvailable(SIUSensor.PROXIMITY);
	}

	public boolean isAmbientLightSensorAvailable() {
		return isAvailable(SIUSensor.AMBLIGHT);
	}

	public Set<SIUAudioJackMode> getAudioJackCapabilities() {
		final int audioJack = getSensor(SIUSensor.ENHANCEDAUDIO);
		return XfsConstants.of(audioJack, SIUAudioJackMode.class);
	}

	public boolean isBootSwitchAvailable() {
		return isAvailable(SIUSensor.BOOT_SWITCH);
	}

	public boolean isConsumerDisplaySensorAvailable() {
		return isAvailable(SIUSensor.CONSUMER_DISPLAY);
	}

	public boolean isOperatorCallButtonAvailable() {
		return isAvailable(SIUSensor.OPERATOR_CALL_BUTTON);
	}

	public Set<SIUHandsetMode> getHandsetCapabilities() {
		final int handset = getSensor(SIUSensor.HANDSETSENSOR);
		return XfsConstants.of(handset, SIUHandsetMode.class);
	}

	public Set<SIUGeneralPurposeInputPort> getGeneralPurposeInputPorts() {
		final int inputPorts = getSensor(SIUSensor.GENERALINPUTPORT);
		return XfsConstants.of(inputPorts, SIUGeneralPurposeInputPort.class);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("OperatorSwitch", getOperatorSwitch())
										.append("TamperSensor", isTamperSensorAvailable())
										.append("InternalTamperSensor", isInternalTamperSensorAvailable())
										.append("SeismicSensor", isSeismicSensorAvailable())
										.append("HeatSensor", isHeatSensorAvailable())
										.append("ProximitySensor", isProximitySensorAvailable())
										.append("AmbientLightSensor", isAmbientLightSensorAvailable())
										.append("AudioJack", getAudioJackCapabilities())
										.append("BootSwitch", isBootSwitchAvailable())
										.append("ConsumerDisplaySensor", isConsumerDisplaySensorAvailable())
										.append("OperatorCallButton", isOperatorCallButtonAvailable())
										.append("Handset", getHandsetCapabilities())
										.append("GeneralPurposeInputPorts", getGeneralPurposeInputPorts())
										.toString();
	}
}