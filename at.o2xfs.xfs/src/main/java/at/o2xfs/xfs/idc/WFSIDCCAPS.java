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

import at.o2xfs.win32.BOOL;
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
 *
 */
public class WFSIDCCAPS
		extends Struct {

	/**
	 *
	 */
	private final static int WFS_IDC_GUIDLIGHTS_SIZE = 32;

	/**
	 * Specifies the logical service class; value is WFS_SERVICE_CLASS_IDC
	 */
	private final WORD clazz = new WORD();

	/**
	 * Specifies the type of the ID card unit as one of the following flags: {@link IDCType}
	 */
	private final XfsWord<IDCType> type = new XfsWord<>(IDCType.class);

	/**
	 * Specifies whether the logical device is part of a compound physical
	 * device and is either TRUE or FALSE.
	 */
	private final BOOL compound = new BOOL();

	/**
	 * Specifies the tracks that can be read by the ID card unit as a
	 * combination of the following flags: {@link IDCTrack}
	 */
	private final WORD readTracks = new WORD();

	/**
	 * Specifies the tracks that can be written by the ID card unit as a
	 * combination of the flags: {@link IDCTrack}
	 */
	private final WORD writeTracks = new WORD();

	/**
	 * Specifies the chip card protocols that are supported by the service
	 * provider as a combination of the following flags: {@link IDCChipProtocol}
	 */
	private final WORD chipProtocols = new WORD();

	/**
	 * Specifies the maximum numbers of cards that the retain bin can hold (zero
	 * if not available).
	 */
	private final USHORT cards = new USHORT();

	/**
	 * Specifies the type of security module used as one of the following flags: {@link IDCSecType}
	 */
	private final WORD secType = new WORD();

	/**
	 * Specifies the power-on capabilities of the device hardware, as one of the
	 * following flags {@link IDCPowerOption}; applicable only to motor driven
	 * ID card units.
	 */
	private final WORD powerOnOption = new WORD();

	/**
	 * Specifies the power-off capabilities of the device hardware, as one of
	 * the following flags {@link IDCPowerOption}; applicable only to motor
	 * driven ID card units.
	 */
	private final WORD powerOffOption = new WORD();

	/**
	 * Specifies whether the Flux Sensor on the card unit is programmable, this
	 * can either be TRUE or FALSE.
	 *
	 * @since 3.00
	 */
	private final BOOL fluxSensorProgrammable = new BOOL();

	/**
	 * Specifies whether a card may be read or written after having been pushed
	 * to the exit slot with an eject command. This value is either TRUE or
	 * FALSE. It is only TRUE if the capabilities of the device are not affected
	 * by one of these sequences of commands.
	 *
	 * @since 3.00
	 */
	private final BOOL readWriteAccessFollowingEject = new BOOL();

	/**
	 * A combination of the following flags specify the write capabilities, with
	 * respect to whether the device can write low coercivity (loco) and/or high
	 * coercivity (hico) magnetic stripes: {@link IDCWriteMethod}
	 *
	 * @since 3.00
	 */
	private final WORD writeMode = new WORD();

	/**
	 * Specifies the capabilities of the ID card unit (in relation to the user
	 * or permanent chip controlled by the service), for chip power management
	 * as a combination of the following flags: {@link IDCChipPower}
	 *
	 * @since 3.00
	 */
	private final WORD chipPower = new WORD();

	/**
	 * Points to a list of vendor-specific, or any other extended information.
	 * The information is returned as a series of "key=value" strings so that it
	 * is easily extensible by service providers. Each string is
	 * null-terminated, with the final string terminating with two null
	 * characters.
	 */
	private final LPZZSTR extra = new LPZZSTR();

	/**
	 * Specifies whether data track data is read on entry or exit from the dip
	 * card unit as one of the following flags: {@link IDCDIPMode}
	 *
	 * @since 3.10
	 */
	private final WORD dipMode = new WORD();

	/**
	 * Pointer to a zero terminated array that specifies the memory card
	 * protocols that are supported by the Service Provider as an array of
	 * constants. If this parameter is NULL then the Service Provider does not
	 * support any memory card protocols. Valid Memory Card Identifiers are: {@link IDCMemoryChipProtocol}
	 *
	 * @since 3.10
	 */
	private final Pointer memoryChipProtocols = new Pointer();

	/**
	 * Specifies which guidance lights are available.
	 *
	 * @since 3.10
	 */
	private final DWORDArray guidLights = new DWORDArray(WFS_IDC_GUIDLIGHTS_SIZE);

	/**
	 * Specifies the target position that is supported for the eject operation,
	 * as a combination of the following flags: {@link IDCEjectPosition}
	 *
	 * @since 3.10
	 */
	private final WORD ejectPosition = new WORD();

	/**
	 * Specifies whether power saving control is available. This can either be
	 * TRUE if available or FALSE if not available.
	 *
	 * @since 3.10
	 */
	private final BOOL powerSaveControl = new BOOL();

	/**
	 * Specifies the number of supported parking stations. If a zero value is
	 * specified there is no parking station supported.
	 *
	 * @since 3.20
	 */
	private final USHORT parkingStations = new USHORT();

	/**
	 * Specifies whether the anti-fraud module is available. This can either be
	 * TRUE if available or FALSE if not available.
	 *
	 * @since 3.20
	 */
	private final BOOL antiFraudModule = new BOOL();

	public WFSIDCCAPS(final XfsVersion xfsVersion) {
		add(clazz);
		add(type);
		add(compound);
		add(readTracks);
		add(writeTracks);
		add(chipProtocols);
		add(cards);
		add(secType);
		add(powerOnOption);
		add(powerOffOption);
		if (xfsVersion.isGE(XfsVersion.V3_00)) {
			add(fluxSensorProgrammable);
			add(readWriteAccessFollowingEject);
			add(writeMode);
			add(chipPower);
		} else {
			fluxSensorProgrammable.allocate();
			readWriteAccessFollowingEject.allocate();
			writeMode.allocate();
			chipPower.allocate();
		}
		add(extra);
		if (xfsVersion.isGE(XfsVersion.V3_10)) {
			add(dipMode);
			add(memoryChipProtocols);
			add(guidLights);
			add(ejectPosition);
			add(powerSaveControl);

		} else {
			dipMode.allocate();
			memoryChipProtocols.allocate();
			guidLights.allocate();
			ejectPosition.allocate();
			powerSaveControl.allocate();
		}
		if (xfsVersion.isGE(XfsVersion.V3_20)) {
			add(parkingStations);
			add(antiFraudModule);
		} else {
			parkingStations.allocate();
			antiFraudModule.allocate();
		}
	}

	public WFSIDCCAPS(final XfsVersion xfsVersion, final Pointer pCaps) {
		this(xfsVersion);
		assignBuffer(pCaps);
	}

	/**
	 * Copy constructor.
	 */
	public WFSIDCCAPS(final XfsVersion xfsVersion, final WFSIDCCAPS caps) {
		this(xfsVersion);
		allocate();
		clazz.set(caps.clazz);
		type.set(caps.getType());
		compound.set(caps.isCompound());
		readTracks.set(caps.readTracks);
		writeTracks.set(caps.writeTracks);
		chipProtocols.set(caps.chipProtocols);
		cards.set(caps.cards);
		secType.set(caps.secType);
		powerOnOption.set(caps.powerOnOption);
		powerOffOption.set(caps.powerOffOption);
		setFluxSensorProgrammable(caps.isFluxSensorProgrammable());
		setReadWriteAccessFollowingEject(caps.isReadWriteAccessFollowingEject());
		writeMode.set(caps.writeMode);
		chipPower.set(caps.chipPower);
		setExtra(caps.getExtra());
		dipMode.set(caps.dipMode);
		// FIXME: memoryChipProtocols
		for (int i = 0; i < guidLights.length; i++) {
			guidLights.get(i).set(caps.guidLights.get(i));
		}
		ejectPosition.set(caps.ejectPosition);
		setPowerSaveControl(caps.isPowerSaveControl());
		parkingStations.set(caps.parkingStations);
		setAntiFraudModule(caps.isAntiFraudModule());

	}

	/**
	 * @see #type
	 */
	public IDCType getType() {
		return XfsConstants.valueOf(type, IDCType.class);
	}

	/**
	 * @see #type
	 */
	public void setType(final IDCType type) {
		this.type.set(type);
	}

	/**
	 * @see #compound
	 */
	public boolean isCompound() {
		return compound.booleanValue();
	}

	/**
	 * @see #compound
	 */
	public void setCompound(final boolean compound) {
		this.compound.set(compound);
	}

	/**
	 * @see #readTracks
	 */
	public Set<IDCTrack> getReadTracks() {
		return XfsConstants.of(readTracks, IDCTrack.class);
	}

	/**
	 * @see #readTracks
	 */
	public void setReadTracks(final Set<IDCTrack> readTracks) {
		this.readTracks.set((int) Bitmask.of(readTracks));
	}

	/**
	 * @see #writeTracks
	 */
	public Set<IDCTrack> getWriteTracks() {
		return XfsConstants.of(writeTracks, IDCTrack.class);
	}

	/**
	 * @see #writeTracks
	 */
	public void setWriteTracks(final Set<IDCTrack> writeTracks) {
		this.writeTracks.set((int) Bitmask.of(writeTracks));
	}

	/**
	 * @see #chipProtocols
	 */
	public Set<IDCChipProtocol> getChipProtocols() {
		return XfsConstants.of(chipProtocols, IDCChipProtocol.class);
	}

	/**
	 * @see #chipProtocols
	 */
	public void setChipProtocols(final Set<IDCChipProtocol> chipProtocols) {
		this.chipProtocols.set((int) Bitmask.of(chipProtocols));
	}

	/**
	 * @see #cards
	 */
	public int getCards() {
		return cards.intValue();
	}

	/**
	 * @see #cards
	 */
	public void setCards(final int cards) {
		this.cards.set(cards);
	}

	/**
	 * @see #secType
	 */
	public IDCSecType getSecType() {
		return XfsConstants.valueOf(secType, IDCSecType.class);
	}

	/**
	 * @see #secType
	 */
	public void setSecType(final IDCSecType secType) {
		this.secType.set((int) secType.getValue());
	}

	/**
	 * @see #powerOnOption
	 */
	public IDCPowerOption getPowerOnOption() {
		return XfsConstants.valueOf(powerOnOption, IDCPowerOption.class);
	}

	/**
	 * @see #powerOnOption
	 */
	public void setPowerOnOption(final IDCPowerOption powerOnOption) {
		this.powerOnOption.set((int) powerOnOption.getValue());
	}

	/**
	 * @see #powerOffOption
	 */
	public IDCPowerOption getPowerOffOption() {
		return XfsConstants.valueOf(powerOffOption, IDCPowerOption.class);
	}

	/**
	 * @see #powerOffOption
	 */
	public void setPowerOffOption(final IDCPowerOption powerOffOption) {
		this.powerOffOption.set((int) powerOffOption.getValue());
	}

	/**
	 * @see #fluxSensorProgrammable
	 */
	public boolean isFluxSensorProgrammable() {
		return fluxSensorProgrammable.booleanValue();
	}

	/**
	 * @see #fluxSensorProgrammable
	 */
	public void setFluxSensorProgrammable(final boolean fluxSensorProgrammable) {
		this.fluxSensorProgrammable.set(fluxSensorProgrammable);
	}

	/**
	 * @see #readWriteAccessFollowingEject
	 */
	public boolean isReadWriteAccessFollowingEject() {
		return readWriteAccessFollowingEject.booleanValue();
	}

	/**
	 * @see #readWriteAccessFollowingEject
	 */
	public void setReadWriteAccessFollowingEject(final boolean readWriteAccessFollowingEject) {
		this.readWriteAccessFollowingEject.set(readWriteAccessFollowingEject);
	}

	/**
	 * @see #writeMode
	 */
	public Set<IDCWriteMethod> getWriteMode() {
		return XfsConstants.of(writeMode, IDCWriteMethod.class);
	}

	/**
	 * @see #writeMode
	 */
	public void setWriteMode(final Set<IDCWriteMethod> writeMode) {
		this.writeMode.set((int) Bitmask.of(writeMode));
	}

	/**
	 * @see #chipPower
	 */
	public Set<IDCChipPower> getChipPower() {
		return XfsConstants.of(chipPower, IDCChipPower.class);
	}

	/**
	 * @see #chipPower
	 */
	public void setChipPower(final Set<IDCChipPower> chipPower) {
		this.chipPower.set((int) Bitmask.of(chipPower));
	}

	/**
	 * @see #extra
	 */
	public Map<String, String> getExtra() {
		return KeyValueMap.from(extra);
	}

	/**
	 * @see #extra
	 */
	public void setExtra(final Map<String, String> extra) {
		this.extra.pointTo(KeyValueMap.toZZString(extra));
	}

	/**
	 * @see #dipMode
	 */
	public IDCDIPMode getDIPMode() {
		return XfsConstants.valueOf(dipMode, IDCDIPMode.class);
	}

	/**
	 * @see #dipMode
	 */
	public void setDIPMode(final IDCDIPMode dipMode) {
		this.dipMode.set((int) dipMode.getValue());
	}

	/**
	 * @see #memoryChipProtocols
	 */
	public Set<IDCMemoryChipProtocol> getMemoryChipProtocols() {
		// FIXME:
		return null;
	}

	/**
	 * @see #memoryChipProtocols
	 */
	public void setMemoryChipProtocols(final Set<IDCMemoryChipProtocol> memoryChipProtocols) {
		// FIXME: this.memoryChipProtocols = memoryChipProtocols;
	}

	public DWORDArray getGuidLights() {
		return guidLights;
	}

	/**
	 * @see #ejectPosition
	 */
	public Set<IDCEjectPosition> getEjectPosition() {
		return XfsConstants.of(ejectPosition, IDCEjectPosition.class);
	}

	/**
	 * @see #ejectPosition
	 */
	public void setEjectPosition(final Set<IDCEjectPosition> ejectPosition) {
		this.ejectPosition.set((int) Bitmask.of(ejectPosition));
	}

	public boolean isPowerSaveControl() {
		return powerSaveControl.booleanValue();
	}

	public void setPowerSaveControl(final boolean powerSaveControl) {
		this.powerSaveControl.set(powerSaveControl);
	}

	public int getParkingStations() {
		return parkingStations.intValue();
	}

	public void setParkingStations(final int parkingStations) {
		this.parkingStations.set(parkingStations);
	}

	public boolean isAntiFraudModule() {
		return antiFraudModule.booleanValue();
	}

	public void setAntiFraudModule(final boolean antiFraudModule) {
		this.antiFraudModule.set(antiFraudModule);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("clazz", clazz)
										.append("type", getType())
										.append("compound", isCompound())
										.append("readTracks", getReadTracks())
										.append("writeTracks", getWriteTracks())
										.append("chipProtocols", getChipProtocols())
										.append("cards", getCards())
										.append("secType", getSecType())
										.append("powerOnOption", getPowerOnOption())
										.append("powerOffOption", getPowerOffOption())
										.append("fluxSensorProgrammable", isFluxSensorProgrammable())
										.append("readWriteAccessFollowingEject", isReadWriteAccessFollowingEject())
										.append("writeMode", writeMode)
										.append("chipPower", getChipPower())
										.append("extra", getExtra())
										.append("dipMode", getDIPMode())
										.append("memoryChipProtocols", getMemoryChipProtocols())
										.append("guidLights", getGuidLights())
										.append("ejectPosition", getEjectPosition())
										.append("parkingStations", getParkingStations())
										.append("antiFraudModule", isAntiFraudModule())
										.toString();
	}
}