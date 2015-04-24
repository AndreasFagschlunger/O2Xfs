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

package at.o2xfs.xfs.idc;

import at.o2xfs.win32.DWORDArray;
import at.o2xfs.win32.LPZZSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.XfsWord;
import at.o2xfs.xfs.util.KeyValueMap;
import at.o2xfs.xfs.util.XfsConstants;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Andreas Fagschlunger
 *
 */
public class WfsIDCStatus
		extends Struct {

	/**
	 *
	 */
	private final static int WFS_IDC_GUIDLIGHTS_SIZE = 32;

	/**
	 * Specifies the state of the ID card device as one of the following flags: {@link IDCDeviceState}
	 */
	private final XfsWord<IDCDeviceState> device = new XfsWord<>(IDCDeviceState.class);

	/**
	 * Specifies the state of the ID card unit as one of the following flags: {@link IDCMedia}
	 */
	private final XfsWord<IDCMedia> media = new XfsWord<>(IDCMedia.class);

	/**
	 * Specifies the state of the ID card unit retain bin as one of the
	 * following flags: {@link IDCRetainBin}
	 */
	private final XfsWord<IDCRetainBin> retainBin = new XfsWord<>(IDCRetainBin.class);

	/**
	 * Specifies the state of the security unit as one of the following flags: {@link IDCSecurity}
	 */
	private final XfsWord<IDCSecurity> security = new XfsWord<>(IDCSecurity.class);

	/**
	 * The number of cards retained; applicable only to motor driven ID card
	 * units. This value is persistent (i.e., it survives power failures, opens,
	 * and closes): it is reset to zero by the WFS_CMS_IDC_RESET_COUNT command.
	 */
	private final USHORT cards = new USHORT();

	/**
	 * Specifies the state of the chip on the currently inserted card in the
	 * device as one of the following flags: {@link IDCChipState}
	 *
	 * @since 3.00
	 */
	private final XfsWord<IDCChipState> chipPower = new XfsWord<>(IDCChipState.class);

	/**
	 * Points to a list of vendor-specific, or any other extended, information.
	 * The information is returned as a series of "key=value" strings so that it
	 * is easily extensible by service providers. Each string is
	 * null-terminated, with the final string terminating with two null
	 * characters.
	 */
	private final LPZZSTR extra = new LPZZSTR();

	/**
	 * Specifies the state of the guidance light indicators. A number of
	 * guidance light types are defined below. Vendor specific guidance lights
	 * are defined starting from the end of the array. The maximum guidance
	 * light index is WFS_IDC_GUIDLIGHTS_MAX. Specifies the state of the
	 * guidance light indicator as WFS_IDC_GUIDANCE_NOT_AVAILABLE,
	 * WFS_IDC_GUIDANCE_OFF or a combination of the following flags consisting
	 * of one type B, and optionally one type C.
	 *
	 * TODO: Table
	 *
	 * @since 3.10
	 */
	private final DWORDArray guidLights = new DWORDArray(WFS_IDC_GUIDLIGHTS_SIZE);

	/**
	 * Specifies the state of the chip card module reader as one of the
	 * following values: {@link IDCChipModule}
	 *
	 * @since 3.10
	 */
	private XfsWord<IDCChipModule> chipModule = new XfsWord<>(IDCChipModule.class);

	/**
	 * Specifies the state of the magnetic card reader as one of the following
	 * values: {@link IDCMagModule}
	 *
	 * @since 3.10
	 */
	private XfsWord<IDCMagModule> magReadModule = new XfsWord<>(IDCMagModule.class);

	/**
	 * Specifies the state of the magnetic card writer as one of the following
	 * values: {@link IDCMagModule}
	 *
	 * @since 3.10
	 */
	private XfsWord<IDCMagModule> magWriteModule = new XfsWord<>(IDCMagModule.class);

	/**
	 * Specifies the state of the front image reader as one of the following
	 * values: {@link IDCImageModule}
	 *
	 * @since 3.10
	 */
	private XfsWord<IDCImageModule> frontImageModule = new XfsWord<>(IDCImageModule.class);

	/**
	 * Specifies the state of the back image reader as one of the following
	 * values: {@link IDCImageModule}
	 *
	 * @since 3.10
	 */
	private XfsWord<IDCImageModule> backImageModule = new XfsWord<>(IDCImageModule.class);

	/**
	 * Specifies the device position. The device position value is independent
	 * of the fwDevice value, e.g. when the device position is reported as
	 * WFS_IDC_DEVICENOTINPOSITION, fwDevice can have any of the values defined
	 * above (including WFS_IDC_DEVONLINE or WFS_IDC_DEVOFFLINE). If the device
	 * is not in its normal operating position (i.e. WFS_IDC_DEVICEINPOSITION)
	 * then media may not be presented through the normal customer interface.
	 * This value is one of the following values: {@link IDCDevicePosition}
	 *
	 * @since 3.10
	 */
	private XfsWord<IDCDevicePosition> devicePosition = new XfsWord<>(IDCDevicePosition.class);

	/**
	 * Specifies the actual number of seconds required by the device to resume
	 * its normal operational state from the current power saving mode. This
	 * value is zero if either the power saving mode has not been activated or
	 * no power save control is supported.
	 *
	 * @since 3.10
	 */
	private USHORT powerSaveRecoveryTime = new USHORT();

	public WfsIDCStatus(final XfsVersion xfsVersion) {
		add(device);
		add(media);
		add(retainBin);
		add(security);
		add(cards);
		if (xfsVersion.isGE(XfsVersion.V3_00)) {
			add(chipPower);
		} else {
			chipPower.allocate();
		}
		add(extra);
		if (xfsVersion.isGE(XfsVersion.V3_10)) {
			add(guidLights);
			add(chipModule);
			add(magReadModule);
			add(magWriteModule);
			add(frontImageModule);
			add(backImageModule);
			add(devicePosition);
			add(powerSaveRecoveryTime);
		} else {
			guidLights.allocate();
			chipModule.allocate();
			magReadModule.allocate();
			magWriteModule.allocate();
			frontImageModule.allocate();
			backImageModule.allocate();
			devicePosition.allocate();
			powerSaveRecoveryTime.allocate();
		}
	}

	public WfsIDCStatus(final XfsVersion xfsVersion, final Pointer pStatus) {
		this(xfsVersion);
		assignBuffer(pStatus);
	}

	public WfsIDCStatus(final XfsVersion xfsVersion, final WfsIDCStatus idcStatus) {
		this(xfsVersion);
		allocate();
		device.set(idcStatus.getDevice());
		media.set(idcStatus.getMedia());
		retainBin.set(idcStatus.getRetainBin());
		security.set(idcStatus.getSecurity());
		cards.set(idcStatus.cards);
		if (xfsVersion.isGE(XfsVersion.V2_00)) {
			chipPower.set(idcStatus.getChipPower());
		}
		extra.pointTo(KeyValueMap.toZZString(idcStatus.getExtra()));
	}

	/**
	 * @see #device
	 */
	public IDCDeviceState getDevice() {
		return XfsConstants.valueOf(device, IDCDeviceState.class);
	}

	/**
	 * @see #media
	 */
	public IDCMedia getMedia() {
		return XfsConstants.valueOf(media, IDCMedia.class);
	}

	/**
	 * @see #retainBin
	 */
	public IDCRetainBin getRetainBin() {
		return XfsConstants.valueOf(retainBin, IDCRetainBin.class);
	}

	/**
	 * @see #security
	 */
	public IDCSecurity getSecurity() {
		return XfsConstants.valueOf(security, IDCSecurity.class);
	}

	/**
	 * @see #cards
	 */
	public int getCards() {
		return cards.intValue();
	}

	/**
	 * @see #chipPower
	 */
	public IDCChipState getChipPower() {
		return XfsConstants.valueOf(chipPower, IDCChipState.class);
	}

	/**
	 * @see #chipPower
	 */
	public void setChipPower(final IDCChipState chipPower) {
		this.chipPower.set(chipPower);
	}

	/**
	 * @see #extra
	 */
	public Map<String, String> getExtra() {
		return KeyValueMap.from(extra);
	}

	/**
	 * @see #chipModule
	 */
	public IDCChipModule getChipModule() {
		return chipModule.get();
	}

	/**
	 * @see #chipModule
	 */
	public void setChipModule(final IDCChipModule chipModule) {
		this.chipModule.set(chipModule);
	}

	/**
	 * @see #magReadModule
	 */
	public IDCMagModule getMagReadModule() {
		return magReadModule.get();
	}

	/**
	 * @see #magReadModule
	 */
	public void setMagReadModule(final IDCMagModule magReadModule) {
		this.magReadModule.set(magReadModule);
	}

	/**
	 * @see #magWriteModule
	 */
	public IDCMagModule getMagWriteModule() {
		return magWriteModule.get();
	}

	/**
	 * @see #magWriteModule
	 */
	public void setMagWriteModule(final IDCMagModule magWriteModule) {
		this.magWriteModule.set(magWriteModule);
	}

	/**
	 * @see #frontImageModule
	 */
	public IDCImageModule getFrontImageModule() {
		return frontImageModule.get();
	}

	/**
	 * @see #frontImageModule
	 */
	public void setFrontImageModule(final IDCImageModule frontImageModule) {
		this.frontImageModule.set(frontImageModule);
	}

	/**
	 * @see #backImageModule
	 */
	public IDCImageModule getBackImageModule() {
		return backImageModule.get();
	}

	/**
	 * @see #backImageModule
	 */
	public void setBackImageModule(final IDCImageModule backImageModule) {
		this.backImageModule.set(backImageModule);
	}

	/**
	 * @see #devicePosition
	 */
	public IDCDevicePosition getDevicePosition() {
		return devicePosition.get();
	}

	/**
	 * @see #devicePosition
	 */
	public void setDevicePosition(final IDCDevicePosition devicePosition) {
		this.devicePosition.set(devicePosition);
	}

	/**
	 * @see #powerSaveRecoveryTime
	 */
	public int getPowerSaveRecoveryTime() {
		return powerSaveRecoveryTime.intValue();
	}

	/**
	 * @see #powerSaveRecoveryTime
	 */
	public void setPowerSaveRecoveryTime(final int powerSaveRecoveryTime) {
		this.powerSaveRecoveryTime.set(powerSaveRecoveryTime);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("device", getDevice())
										.append("media", getMedia())
										.append("retainBin", getRetainBin())
										.append("security", getSecurity())
										.append("cards", getCards())
										.append("chipPower", getChipPower())
										.append("extra", getExtra())
										.append("guidLights", guidLights)
										.append("chipModule", getChipModule())
										.append("magReadModule", getMagReadModule())
										.append("magWriteModule", getMagWriteModule())
										.append("frontImageModule", getFrontImageModule())
										.append("backImageModule", getBackImageModule())
										.append("devicePosition", getDevicePosition())
										.append("powerSaveRecoveryTime", getPowerSaveRecoveryTime())
										.toString();
	}

}