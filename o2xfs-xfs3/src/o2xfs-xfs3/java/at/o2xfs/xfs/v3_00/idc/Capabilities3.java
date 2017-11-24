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

package at.o2xfs.xfs.v3_00.idc;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.XfsExtra;
import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.idc.ChipPowerAction;
import at.o2xfs.xfs.idc.ChipProtocols;
import at.o2xfs.xfs.idc.DataSource;
import at.o2xfs.xfs.idc.PowerOffOption;
import at.o2xfs.xfs.idc.SecType;
import at.o2xfs.xfs.idc.Type;
import at.o2xfs.xfs.idc.WriteMethod;
import at.o2xfs.xfs.win32.XfsWord;
import at.o2xfs.xfs.win32.XfsWordBitmask;

public class Capabilities3 extends Struct {

	protected final XfsWord<XfsServiceClass> serviceClass = new XfsWord<>(XfsServiceClass.class);
	protected final XfsWord<Type> type = new XfsWord<>(Type.class);
	protected final BOOL compound = new BOOL();
	protected final XfsWordBitmask<DataSource> readTracks = new XfsWordBitmask<>(DataSource.class);
	protected final XfsWordBitmask<DataSource> writeTracks = new XfsWordBitmask<>(DataSource.class);
	protected final XfsWordBitmask<ChipProtocols> chipProtocols = new XfsWordBitmask<>(ChipProtocols.class);
	protected final USHORT cards = new USHORT();
	protected final XfsWord<SecType> secType = new XfsWord<>(SecType.class);
	protected final XfsWord<PowerOffOption> powerOnOption = new XfsWord<>(PowerOffOption.class);
	protected final XfsWord<PowerOffOption> powerOffOption = new XfsWord<>(PowerOffOption.class);
	protected final BOOL fluxSensorProgrammable = new BOOL();
	protected final BOOL readWriteAccessFollowingEject = new BOOL();
	protected final XfsWordBitmask<WriteMethod> writeMode = new XfsWordBitmask<>(WriteMethod.class);
	protected final XfsWordBitmask<ChipPowerAction> chipPower = new XfsWordBitmask<>(ChipPowerAction.class);
	protected final XfsExtra extra = new XfsExtra();

	protected Capabilities3() {
		add(serviceClass);
		add(type);
		add(compound);
		add(readTracks);
		add(writeTracks);
		add(chipProtocols);
		add(cards);
		add(secType);
		add(powerOnOption);
		add(powerOffOption);
		add(fluxSensorProgrammable);
		add(readWriteAccessFollowingEject);
		add(writeMode);
		add(chipPower);
		add(extra);
	}

	public Capabilities3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Capabilities3(Capabilities3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Capabilities3 copy) {
		serviceClass.set(copy.getServiceClass());
		type.set(copy.getType());
		compound.set(copy.isCompound());
		readTracks.set(copy.getReadTracks());
		writeTracks.set(copy.getWriteTracks());
		chipProtocols.set(copy.getChipProtocols());
		cards.set(copy.getCards());
		secType.set(copy.getSecType());
		powerOnOption.set(copy.getPowerOnOption());
		powerOffOption.set(copy.getPowerOffOption());
		fluxSensorProgrammable.set(copy.isFluxSensorProgrammable());
		readWriteAccessFollowingEject.set(copy.isReadWriteAccessFollowingEject());
		writeMode.set(copy.getWriteMode());
		chipPower.set(copy.getChipPower());
		extra.set(copy.getExtra());
	}

	public XfsServiceClass getServiceClass() {
		return serviceClass.get();
	}

	public Type getType() {
		return type.get();
	}

	public boolean isCompound() {
		return compound.get();
	}

	public Set<DataSource> getReadTracks() {
		return readTracks.get();
	}

	public Set<DataSource> getWriteTracks() {
		return writeTracks.get();
	}

	public Set<ChipProtocols> getChipProtocols() {
		return chipProtocols.get();
	}

	public int getCards() {
		return cards.get();
	}

	public SecType getSecType() {
		return secType.get();
	}

	public PowerOffOption getPowerOnOption() {
		return powerOnOption.get();
	}

	public PowerOffOption getPowerOffOption() {
		return powerOffOption.get();
	}

	public boolean isFluxSensorProgrammable() {
		return fluxSensorProgrammable.get();
	}

	public boolean isReadWriteAccessFollowingEject() {
		return readWriteAccessFollowingEject.get();
	}

	public Set<WriteMethod> getWriteMode() {
		return writeMode.get();
	}

	public Set<ChipPowerAction> getChipPower() {
		return chipPower.get();
	}

	public Map<String, String> getExtra() {
		return extra.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getServiceClass())
				.append(getType())
				.append(isCompound())
				.append(getReadTracks())
				.append(getWriteTracks())
				.append(getChipProtocols())
				.append(getCards())
				.append(getSecType())
				.append(getPowerOnOption())
				.append(getPowerOffOption())
				.append(isFluxSensorProgrammable())
				.append(isReadWriteAccessFollowingEject())
				.append(getWriteMode())
				.append(getChipPower())
				.append(getExtra())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Capabilities3) {
			Capabilities3 capabilities3 = (Capabilities3) obj;
			return new EqualsBuilder()
					.append(getServiceClass(), capabilities3.getServiceClass())
					.append(getType(), capabilities3.getType())
					.append(isCompound(), capabilities3.isCompound())
					.append(getReadTracks(), capabilities3.getReadTracks())
					.append(getWriteTracks(), capabilities3.getWriteTracks())
					.append(getChipProtocols(), capabilities3.getChipProtocols())
					.append(getCards(), capabilities3.getCards())
					.append(getSecType(), capabilities3.getSecType())
					.append(getPowerOnOption(), capabilities3.getPowerOnOption())
					.append(getPowerOffOption(), capabilities3.getPowerOffOption())
					.append(isFluxSensorProgrammable(), capabilities3.isFluxSensorProgrammable())
					.append(isReadWriteAccessFollowingEject(), capabilities3.isReadWriteAccessFollowingEject())
					.append(getWriteMode(), capabilities3.getWriteMode())
					.append(getChipPower(), capabilities3.getChipPower())
					.append(getExtra(), capabilities3.getExtra())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("serviceClass", getServiceClass())
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
				.append("writeMode", getWriteMode())
				.append("chipPower", getChipPower())
				.append("extra", getExtra())
				.toString();
	}
}
