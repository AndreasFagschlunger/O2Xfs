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

package at.o2xfs.xfs.pin;

import at.o2xfs.win32.DWORD;
import at.o2xfs.win32.DWORDArray;
import at.o2xfs.win32.LPZZSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.XfsWord;
import at.o2xfs.xfs.util.Bitmask;
import at.o2xfs.xfs.util.KeyValueMap;
import at.o2xfs.xfs.util.XfsConstants;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Andreas Fagschlunger
 */
public class WFSPINSTATUS
		extends Struct {

	private static final int WFS_PIN_GUIDLIGHTS_SIZE = 32;

	/**
	 * Specifies the state of the PIN pad device as one of the following flags: {@link PINDeviceState}
	 */
	private final XfsWord<PINDeviceState> device = new XfsWord<>(PINDeviceState.class);

	/**
	 * Specifies the state of the Encryption Module as one of the following
	 * flags: {@link PINEncStat}
	 */
	private final XfsWord<PINEncStat> encStat = new XfsWord<>(PINEncStat.class);

	/**
	 * Specifies a list of vendor-specific, or any other extended, information.
	 * The information is returned as a series of �key=value� strings so that it
	 * is easily extendable by service providers. Each string will be
	 * null-terminated, with the final string terminating with two null
	 * characters.
	 */
	private final LPZZSTR extra = new LPZZSTR();

	/**
	 * Specifies the state of the guidance light indicators.
	 *
	 * @since 3.10
	 */
	private final DWORDArray guidLights = new DWORDArray(WFS_PIN_GUIDLIGHTS_SIZE);

	/**
	 * Specifies whether automatic beep tone on key press is active or not.
	 * Active and in-active key beeping is reported independently.
	 * fwAutoBeepMode can take a combination of the following values, if the
	 * flag is not set auto beeping is not activated (or not supported) for that
	 * key type (i.e. active or inactive keys): {@link PINAutoBeepMode}
	 *
	 * @since 3.10
	 */
	private final WORD autoBeepModes = new WORD();

	/**
	 * Specifies the state of the public verification or encryption key in the
	 * PIN certificate modules as one of the following flags: {@link PINCertificateState}
	 *
	 * @since 3.10
	 */
	private final DWORD certificateState = new DWORD();

	/**
	 *
	 * Specifies the device position. The device position value is independent
	 * of the fwDevice value, e.g. when the device position is reported as
	 * WFS_PIN_DEVICENOTINPOSITION, fwDevice can have any of the values defined
	 * above (including WFS_PIN_DEVONLINE or WFS_PIN_DEVOFFLINE). This value is
	 * one of the following values: {@link PINDevicePosition}
	 *
	 * @since 3.10
	 */
	private final XfsWord<PINDevicePosition> devicePosition = new XfsWord<>(PINDevicePosition.class);

	/**
	 * Specifies the actual number of seconds required by the device to resume
	 * its normal operational state from the current power saving mode. This
	 * value is zero if either the power saving mode has not been activated or
	 * no power save control is supported.
	 *
	 * @since 3.10
	 */
	private final USHORT powerSaveRecoveryTime = new USHORT();

	/**
	 * Specifies the state of the anti-fraud module as one of the following
	 * values: {@link PINAntiFraudModule}
	 *
	 * @since 3.20
	 */
	private final WORD antiFraudModule = new WORD();

	public WFSPINSTATUS(final XfsVersion xfsVersion) {
		add(device);
		add(encStat);
		add(extra);
		if (xfsVersion.isGE(XfsVersion.V3_10)) {
			add(guidLights);
			add(autoBeepModes);
			add(certificateState);
			add(devicePosition);
			add(powerSaveRecoveryTime);
		} else {
			guidLights.allocate();
			autoBeepModes.allocate();
			certificateState.allocate();
			devicePosition.allocate();
			powerSaveRecoveryTime.allocate();
		}
		if (xfsVersion.isGE(XfsVersion.V3_20)) {
			add(antiFraudModule);
		} else {
			antiFraudModule.allocate();
		}
	}

	public WFSPINSTATUS(final XfsVersion xfsVersion, final Pointer p) {
		this(xfsVersion);
		assignBuffer(p);
	}

	public WFSPINSTATUS(final XfsVersion xfsVersion, final WFSPINSTATUS pinStatus) {
		this(xfsVersion);
		allocate();
		device.set(pinStatus.getDevice());
		encStat.set(pinStatus.getEncStat());
		setExtra(pinStatus.getExtra());
		// TODO: guidLights
		autoBeepModes.set(pinStatus.autoBeepModes);
		certificateState.set(pinStatus.certificateState);
		devicePosition.set(pinStatus.getDevicePosition());
		powerSaveRecoveryTime.set(pinStatus.powerSaveRecoveryTime);
		antiFraudModule.set(pinStatus.antiFraudModule);
	}

	public PINDeviceState getDevice() {
		return XfsConstants.valueOf(device, PINDeviceState.class);
	}

	public void setDevice(final PINDeviceState device) {
		this.device.set(device);
	}

	public PINEncStat getEncStat() {
		return XfsConstants.valueOf(encStat, PINEncStat.class);
	}

	public void setEncStat(final PINEncStat encStat) {
		this.encStat.set(encStat);
	}

	public Map<String, String> getExtra() {
		return KeyValueMap.from(extra);
	}

	public void setExtra(final Map<String, String> extra) {
		this.extra.pointTo(KeyValueMap.toZZString(extra));
	}

	public DWORDArray getGuidLights() {
		return guidLights;
	}

	public Set<PINAutoBeepMode> getAutoBeepModes() {
		return XfsConstants.of(autoBeepModes, PINAutoBeepMode.class);
	}

	public void setAutoBeepModes(final Set<PINAutoBeepMode> autoBeepModes) {
		this.autoBeepModes.set((int) Bitmask.of(autoBeepModes));
	}

	public PINCertificateState getCertificateState() {
		return XfsConstants.valueOf(certificateState, PINCertificateState.class);
	}

	public void setCertificateState(final PINCertificateState certificateState) {
		this.certificateState.set(certificateState.getValue());
	}

	public PINDevicePosition getDevicePosition() {
		return XfsConstants.valueOf(devicePosition, PINDevicePosition.class);
	}

	public void setDevicePosition(final PINDevicePosition devicePosition) {
		this.devicePosition.set(devicePosition);
	}

	public int getPowerSaveRecoveryTime() {
		return powerSaveRecoveryTime.intValue();
	}

	public void setPowerSaveRecoveryTime(final int powerSaveRecoveryTime) {
		this.powerSaveRecoveryTime.set(powerSaveRecoveryTime);
	}

	public PINAntiFraudModule getAntiFraudModule() {
		return XfsConstants.valueOf(antiFraudModule, PINAntiFraudModule.class);
	}

	public void setAntiFraudModule(final PINAntiFraudModule antiFraudModule) {
		this.antiFraudModule.set((int) antiFraudModule.getValue());
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("device", getDevice())
										.append("encStat", getEncStat())
										.append("extra", getExtra())
										.append("guidLights", getGuidLights())
										.append("autoBeepModes", getAutoBeepModes())
										.append("certificateState", getCertificateState())
										.append("devicePosition", getDevicePosition())
										.append("powerSaveRecoveryTime", getPowerSaveRecoveryTime())
										.append("antiFraudModule", getAntiFraudModule())
										.toString();
	}
}