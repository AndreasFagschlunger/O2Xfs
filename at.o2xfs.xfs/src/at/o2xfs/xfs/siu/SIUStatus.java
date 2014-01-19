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

package at.o2xfs.xfs.siu;

import static at.o2xfs.xfs.siu.SIUConstant.AUXILIARIES_SIZE;
import static at.o2xfs.xfs.siu.SIUConstant.DOORS_SIZE;
import static at.o2xfs.xfs.siu.SIUConstant.GUIDLIGHTS_SIZE;
import static at.o2xfs.xfs.siu.SIUConstant.INDICATORS_SIZE;
import static at.o2xfs.xfs.siu.SIUConstant.SENSORS_SIZE;

import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import at.o2xfs.win32.LPZZSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.win32.WORD;
import at.o2xfs.win32.WORDArray;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.util.KeyValueMap;
import at.o2xfs.xfs.util.XfsConstants;

public class SIUStatus extends Struct {

	private WORD device = new WORD();
	private final WORDArray sensors = new WORDArray(SENSORS_SIZE);
	private final WORDArray doors = new WORDArray(DOORS_SIZE);
	private final WORDArray indicators = new WORDArray(INDICATORS_SIZE);
	private final WORDArray auxiliaries = new WORDArray(AUXILIARIES_SIZE);
	private final WORDArray guidLights = new WORDArray(GUIDLIGHTS_SIZE);
	private LPZZSTR extra = new LPZZSTR();
	private USHORT powerSaveRecoveryTime = new USHORT();
	private WORD antiFraudModule = new WORD();

	private SIUStatus(final XfsVersion version) {
		add(device);
		add(sensors);
		add(doors);
		add(indicators);
		add(auxiliaries);
		add(guidLights);
		add(extra);
		if (version.isGE(XfsVersion.V3_10)) {
			add(powerSaveRecoveryTime);
		} else {
			powerSaveRecoveryTime.allocate();
		}
		if (version.isGE(XfsVersion.V3_20)) {
			add(antiFraudModule);
		} else {
			antiFraudModule.allocate();
		}
	}

	public SIUStatus(final XfsVersion version, final Pointer p) {
		this(version);
		assignBuffer(p);
	}

	public SIUStatus(final XfsVersion version, final SIUStatus status) {
		this(version);
		allocate();
		device.put(status.device);
		for (int i = 0; i < SENSORS_SIZE; i++) {
			sensors.get(i).put(status.sensors.get(i));
		}
		for (int i = 0; i < DOORS_SIZE; i++) {
			doors.get(i).put(status.doors.get(i));
		}
		for (int i = 0; i < INDICATORS_SIZE; i++) {
			indicators.get(i).put(status.indicators.get(i));
		}
		for (int i = 0; i < AUXILIARIES_SIZE; i++) {
			auxiliaries.get(i).put(status.auxiliaries.get(i));
		}
		for (int i = 0; i < GUIDLIGHTS_SIZE; i++) {
			guidLights.get(i).put(status.guidLights.get(i));
		}
		setExtra(status.getExtra());
		powerSaveRecoveryTime.put(status.powerSaveRecoveryTime);
		antiFraudModule.put(status.antiFraudModule);
	}

	public SIUDeviceState getDevice() {
		return XfsConstants.valueOf(device, SIUDeviceState.class);
	}

	public void setDevice(final SIUDeviceState device) {
		this.device.put(device.getValue());
	}

	public int getSensorState(final SIUSensor sensor) {
		return sensors.get(sensor.intValue()).intValue();
	}

	public SIUSensorsStatus getSensorStatus() {
		return new SIUSensorsStatus(sensors.asIntArray());
	}

	public int getDoorState(final SIUDoor door) {
		return doors.get(door.intValue()).intValue();
	}

	public SIUDoorsStatus getDoorStatus() {
		return new SIUDoorsStatus(doors.asIntArray());
	}

	public int getIndicatorState(final SIUIndicator indicator) {
		return indicators.get(indicator.intValue()).intValue();
	}

	public SIUIndicatorsStatus getIndicatorStatus() {
		return new SIUIndicatorsStatus(indicators.asIntArray());
	}

	public int getAuxiliaryState(final SIUAuxiliary auxiliary) {
		return auxiliaries.get(auxiliary.intValue()).intValue();
	}

	public SIUAuxiliariesStatus getAuxiliaryStatus() {
		return new SIUAuxiliariesStatus(auxiliaries.asIntArray());
	}

	public int getGuidLightState(final SIUGuidLight guidLight) {
		return guidLights.get(guidLight.intValue()).intValue();
	}

	public SIUGuidLightsStatus getGuidLightStatus() {
		return new SIUGuidLightsStatus(guidLights.asIntArray());
	}

	public Map<String, String> getExtra() {
		return KeyValueMap.from(extra);
	}

	public void setExtra(final Map<String, String> extra) {
		this.extra.pointTo(KeyValueMap.toZZString(extra));
	}

	public int getPowerSaveRecoveryTime() {
		return powerSaveRecoveryTime.intValue();
	}

	public void setPowerSaveRecoveryTime(final int powerSaveRecoveryTime) {
		this.powerSaveRecoveryTime.put(powerSaveRecoveryTime);
	}

	public SIUAntiFraudModule getAntiFraudModule() {
		return XfsConstants.valueOf(antiFraudModule, SIUAntiFraudModule.class);
	}

	public void setAntiFraudModule(final SIUAntiFraudModule antiFraudModule) {
		this.antiFraudModule.put(antiFraudModule.getValue());
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("device", getDevice())
				.append("sensorStatus", getSensorStatus())
				.append("doorStatus", getDoorStatus())
				.append("indicatorStatus", getIndicatorStatus())
				.append("auxiliaryStatus", getAuxiliaryStatus())
				.append("guidLightStatus", getGuidLightStatus())
				.append("extra", getExtra())
				.append("powerSaveRecoveryTime", getPowerSaveRecoveryTime())
				.append("antiFraudModule", getAntiFraudModule()).toString();
	}
}
