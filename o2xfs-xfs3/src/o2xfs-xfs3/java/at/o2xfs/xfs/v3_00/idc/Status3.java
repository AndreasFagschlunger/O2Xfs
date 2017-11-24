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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.XfsExtra;
import at.o2xfs.xfs.idc.ChipPower;
import at.o2xfs.xfs.idc.IdcDeviceState;
import at.o2xfs.xfs.idc.Position;
import at.o2xfs.xfs.idc.RetainBin;
import at.o2xfs.xfs.idc.Security;
import at.o2xfs.xfs.win32.XfsWord;

public class Status3 extends Struct {

	protected final XfsWord<IdcDeviceState> deviceState = new XfsWord<>(IdcDeviceState.class);
	protected final XfsWord<Position> media = new XfsWord<>(Position.class);
	protected final XfsWord<RetainBin> retainBin = new XfsWord<>(RetainBin.class);
	protected final XfsWord<Security> security = new XfsWord<>(Security.class);
	protected final USHORT cards = new USHORT();
	protected final XfsWord<ChipPower> chipPower = new XfsWord<>(ChipPower.class);
	protected final XfsExtra extra = new XfsExtra();

	protected Status3() {
		add(deviceState);
		add(media);
		add(retainBin);
		add(security);
		add(cards);
		add(chipPower);
		add(extra);
	}

	public Status3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Status3(Status3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Status3 copy) {
		deviceState.set(copy.getDeviceState());
		media.set(copy.getMedia());
		retainBin.set(copy.getRetainBin());
		security.set(copy.getSecurity());
		cards.set(copy.getCards());
		chipPower.set(copy.getChipPower());
		extra.set(copy.getExtra());
	}

	public IdcDeviceState getDeviceState() {
		return deviceState.get();
	}

	public Position getMedia() {
		return media.get();
	}

	public RetainBin getRetainBin() {
		return retainBin.get();
	}

	public Security getSecurity() {
		return security.get();
	}

	public int getCards() {
		return cards.get();
	}

	public ChipPower getChipPower() {
		return chipPower.get();
	}

	public Map<String, String> getExtra() {
		return extra.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getDeviceState())
				.append(getMedia())
				.append(getRetainBin())
				.append(getSecurity())
				.append(getCards())
				.append(getChipPower())
				.append(getExtra())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Status3) {
			Status3 status3 = (Status3) obj;
			return new EqualsBuilder()
					.append(getDeviceState(), status3.getDeviceState())
					.append(getMedia(), status3.getMedia())
					.append(getRetainBin(), status3.getRetainBin())
					.append(getSecurity(), status3.getSecurity())
					.append(getCards(), status3.getCards())
					.append(getChipPower(), status3.getChipPower())
					.append(getExtra(), status3.getExtra())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("deviceState", getDeviceState())
				.append("media", getMedia())
				.append("retainBin", getRetainBin())
				.append("security", getSecurity())
				.append("cards", getCards())
				.append("chipPower", getChipPower())
				.append("extra", getExtra())
				.toString();
	}
}
