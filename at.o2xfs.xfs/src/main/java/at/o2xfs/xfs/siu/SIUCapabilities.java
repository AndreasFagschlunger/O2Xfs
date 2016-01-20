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

import static at.o2xfs.xfs.siu.SIUConstant.AUXILIARIES_SIZE;
import static at.o2xfs.xfs.siu.SIUConstant.DOORS_SIZE;
import static at.o2xfs.xfs.siu.SIUConstant.GUIDLIGHTS_SIZE;
import static at.o2xfs.xfs.siu.SIUConstant.INDICATORS_SIZE;
import static at.o2xfs.xfs.siu.SIUConstant.SENSORS_SIZE;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.LPZZSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.WORD;
import at.o2xfs.win32.WORDArray;
import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.util.Bitmask;
import at.o2xfs.xfs.util.KeyValueMap;
import at.o2xfs.xfs.util.XfsConstants;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SIUCapabilities
		extends Struct {

	private WORD serviceClass = new WORD();
	private final WORD type = new WORD();
	private final WORDArray sensors = new WORDArray(SENSORS_SIZE);
	private final WORDArray doors = new WORDArray(DOORS_SIZE);
	private final WORDArray indicators = new WORDArray(INDICATORS_SIZE);
	private final WORDArray auxiliaries = new WORDArray(AUXILIARIES_SIZE);
	private final WORDArray guidLights = new WORDArray(GUIDLIGHTS_SIZE);
	private LPZZSTR extra = new LPZZSTR();
	private BOOL powerSaveControl = new BOOL();
	private WORD autoStartupMode = new WORD();
	private BOOL antiFraudModule = new BOOL();

	private SIUCapabilities(final XfsVersion version) {
		add(serviceClass);
		add(type);
		add(sensors);
		add(doors);
		add(indicators);
		add(auxiliaries);
		add(guidLights);
		add(extra);
		if (version.isGE(XfsVersion.V3_10)) {
			add(powerSaveControl);
		} else {
			powerSaveControl.allocate();
		}
		if (version.isGE(XfsVersion.V3_20)) {
			add(autoStartupMode);
			add(antiFraudModule);
		} else {
			autoStartupMode.allocate();
			antiFraudModule.allocate();
		}
	}

	public SIUCapabilities(final XfsVersion version, final Pointer p) {
		this(version);
		assignBuffer(p);
	}

	public SIUCapabilities(final XfsVersion version, final SIUCapabilities capabilities) {
		this(version);
		allocate();
		serviceClass.set(capabilities.serviceClass);
		type.set(capabilities.type);
		for (int i = 0; i < SENSORS_SIZE; i++) {
			sensors.get(i).set(capabilities.sensors.get(i));
		}
		for (int i = 0; i < DOORS_SIZE; i++) {
			doors.get(i).set(capabilities.doors.get(i));
		}
		for (int i = 0; i < INDICATORS_SIZE; i++) {
			indicators.get(i).set(capabilities.indicators.get(i));
		}
		for (int i = 0; i < AUXILIARIES_SIZE; i++) {
			auxiliaries.get(i).set(capabilities.auxiliaries.get(i));
		}
		for (int i = 0; i < GUIDLIGHTS_SIZE; i++) {
			guidLights.get(i).set(capabilities.guidLights.get(i));
		}
		setExtra(capabilities.getExtra());
		powerSaveControl.set(capabilities.isPowerSaveControl());
		autoStartupMode.set(capabilities.autoStartupMode);
		setAntiFraudModule(capabilities.isAntiFraudModule());
	}

	public XfsServiceClass getServiceClass() {
		return XfsConstants.valueOf(serviceClass, XfsServiceClass.class);
	}

	public Set<SIUPortType> getType() {
		return XfsConstants.of(type, SIUPortType.class);
	}

	private boolean isAvailable(final WORDArray ports, final SIUPortIndex portIndex) {
		return ports.get(portIndex.intValue()).intValue() != SIUConstant.NOT_AVAILABLE;
	}

	public boolean isSensorAvailable(final SIUSensor sensor) {
		return isAvailable(sensors, sensor);
	}

	public SIUSensorCapabilities getSensors() {
		return new SIUSensorCapabilities(sensors.asIntArray());
	}

	public boolean isDoorAvailable(final SIUDoor door) {
		return isAvailable(doors, door);
	}

	public SIUDoorCapabilities getDoors() {
		return new SIUDoorCapabilities(doors.asIntArray());
	}

	public boolean isIndicatorAvailable(final SIUIndicator indicator) {
		return isAvailable(indicators, indicator);
	}

	public SIUIndicatorCapabilities getIndicators() {
		return new SIUIndicatorCapabilities(indicators.asIntArray());
	}

	public boolean isAuxiliaryAvailable(final SIUAuxiliary auxiliary) {
		return isAvailable(auxiliaries, auxiliary);
	}

	public SIUAuxiliariesCapabilities getAuxiliaries() {
		return new SIUAuxiliariesCapabilities(auxiliaries.asIntArray());
	}

	public boolean isGuidLightAvailable(final SIUGuidLight guidLight) {
		return isAvailable(guidLights, guidLight);
	}

	public SIUGuidLightCapabilities getGuidLights() {
		return new SIUGuidLightCapabilities(guidLights.asIntArray());
	}

	public Map<String, String> getExtra() {
		return KeyValueMap.from(extra);
	}

	public void setExtra(final Map<String, String> extra) {
		this.extra.pointTo(KeyValueMap.toZZString(extra));
	}

	public boolean isPowerSaveControl() {
		return powerSaveControl.booleanValue();
	}

	public void setPowerSaveControl(final boolean powerSaveControl) {
		this.powerSaveControl.set(powerSaveControl);
	}

	public Set<SIUAutoStartupMode> getAutoStartupMode() {
		return XfsConstants.of(autoStartupMode, SIUAutoStartupMode.class);
	}

	public void setAutoStartupMode(final Set<SIUAutoStartupMode> autoStartupMode) {
		this.autoStartupMode.set((int) Bitmask.of(autoStartupMode));
	}

	public boolean isAntiFraudModule() {
		return antiFraudModule.booleanValue();
	}

	public void setAntiFraudModule(final boolean antiFraudModule) {
		this.antiFraudModule.set(antiFraudModule);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("serviceClass", getServiceClass())
										.append("type", getType())
										.append("sensors", getSensors())
										.append("doors", getDoors())
										.append("indicators", getIndicators())
										.append("auxiliaries", getAuxiliaries())
										.append("guidLights", getGuidLights())
										.append("extra", getExtra())
										.append("powerSaveControl", isPowerSaveControl())
										.append("autoStartupMode", getAutoStartupMode())
										.append("antiFraudModule", isAntiFraudModule())
										.toString();
	}
}