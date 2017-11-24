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

package at.o2xfs.xfs.v3_10.cim;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.XfsExtra;
import at.o2xfs.xfs.cim.Position;
import at.o2xfs.xfs.cim.RetractArea;
import at.o2xfs.xfs.cim.Usage;
import at.o2xfs.xfs.win32.XfsWord;
import at.o2xfs.xfs.win32.XfsWordBitmask;

public class PositionCapability310 extends Struct {

	protected final XfsWord<Position> position = new XfsWord<>(Position.class);
	protected final XfsWordBitmask<Usage> usage = new XfsWordBitmask<>(Usage.class);
	protected final BOOL shutterControl = new BOOL();
	protected final BOOL itemsTakenSensor = new BOOL();
	protected final BOOL itemsInsertedSensor = new BOOL();
	protected final XfsWordBitmask<RetractArea> retractAreas = new XfsWordBitmask<>(RetractArea.class);
	protected final XfsExtra extra = new XfsExtra();

	protected PositionCapability310() {
		add(position);
		add(usage);
		add(shutterControl);
		add(itemsTakenSensor);
		add(itemsInsertedSensor);
		add(retractAreas);
		add(extra);
	}

	public PositionCapability310(Pointer p) {
		this();
		assignBuffer(p);
	}

	public PositionCapability310(PositionCapability310 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(PositionCapability310 copy) {
		position.set(copy.getPosition());
		usage.set(copy.getUsage());
		shutterControl.set(copy.isShutterControl());
		itemsTakenSensor.set(copy.isItemsTakenSensor());
		itemsInsertedSensor.set(copy.isItemsInsertedSensor());
		retractAreas.set(copy.getRetractAreas());
		extra.set(copy.getExtra());
	}

	public Position getPosition() {
		return position.get();
	}

	public Set<Usage> getUsage() {
		return usage.get();
	}

	public boolean isShutterControl() {
		return shutterControl.get();
	}

	public boolean isItemsTakenSensor() {
		return itemsTakenSensor.get();
	}

	public boolean isItemsInsertedSensor() {
		return itemsInsertedSensor.get();
	}

	public Set<RetractArea> getRetractAreas() {
		return retractAreas.get();
	}

	public Map<String, String> getExtra() {
		return extra.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getPosition()).append(getUsage()).append(isShutterControl()).append(isItemsTakenSensor()).append(isItemsInsertedSensor())
				.append(getRetractAreas()).append(getExtra()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PositionCapability310) {
			PositionCapability310 posCaps310 = (PositionCapability310) obj;
			return new EqualsBuilder().append(getPosition(), posCaps310.getPosition()).append(getUsage(), posCaps310.getUsage())
					.append(isShutterControl(), posCaps310.isShutterControl()).append(isItemsTakenSensor(), posCaps310.isItemsTakenSensor())
					.append(isItemsInsertedSensor(), posCaps310.isItemsInsertedSensor()).append(getRetractAreas(), posCaps310.getRetractAreas())
					.append(getExtra(), posCaps310.getExtra()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("position", getPosition()).append("usage", getUsage()).append("shutterControl", isShutterControl())
				.append("itemsTakenSensor", isItemsTakenSensor()).append("itemsInsertedSensor", isItemsInsertedSensor()).append("retractAreas", getRetractAreas())
				.append("extra", getExtra()).toString();
	}
}
