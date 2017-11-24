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

package at.o2xfs.xfs.v3_00.cdm;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.XfsExtra;
import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.cdm.CdmType;
import at.o2xfs.xfs.cdm.ExchangeType;
import at.o2xfs.xfs.cdm.MoveItems;
import at.o2xfs.xfs.cdm.Position;
import at.o2xfs.xfs.cdm.RetractArea;
import at.o2xfs.xfs.cdm.RetractStackerActions;
import at.o2xfs.xfs.win32.XfsWord;
import at.o2xfs.xfs.win32.XfsWordBitmask;

public class CdmCaps3 extends Struct {

	protected final XfsWord<XfsServiceClass> serviceClass = new XfsWord<>(XfsServiceClass.class);
	protected final XfsWord<CdmType> type = new XfsWord<>(CdmType.class);
	protected final WORD maxDispenseItems = new WORD();
	protected final BOOL compound = new BOOL();
	protected final BOOL shutter = new BOOL();
	protected final BOOL shutterControl = new BOOL();
	protected final XfsWordBitmask<RetractArea> retractAreas = new XfsWordBitmask<>(RetractArea.class);
	protected final XfsWordBitmask<RetractStackerActions> retractTransportActions = new XfsWordBitmask<>(RetractStackerActions.class);
	protected final XfsWordBitmask<RetractStackerActions> retractStackerActions = new XfsWordBitmask<>(RetractStackerActions.class);
	protected final BOOL safeDoor = new BOOL();
	protected final BOOL cashBox = new BOOL();
	protected final BOOL intermediateStacker = new BOOL();
	protected final BOOL itemsTakenSensor = new BOOL();
	protected final XfsWordBitmask<Position> positions = new XfsWordBitmask<>(Position.class);
	protected final XfsWordBitmask<MoveItems> moveItems = new XfsWordBitmask<>(MoveItems.class);
	protected final XfsWordBitmask<ExchangeType> exchangeType = new XfsWordBitmask<>(ExchangeType.class);
	protected final XfsExtra extra = new XfsExtra();

	protected CdmCaps3() {
		add(serviceClass);
		add(type);
		add(maxDispenseItems);
		add(compound);
		add(shutter);
		add(shutterControl);
		add(retractAreas);
		add(retractTransportActions);
		add(retractStackerActions);
		add(safeDoor);
		add(cashBox);
		add(intermediateStacker);
		add(itemsTakenSensor);
		add(positions);
		add(moveItems);
		add(exchangeType);
		add(extra);
	}

	public CdmCaps3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CdmCaps3(CdmCaps3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CdmCaps3 copy) {
		serviceClass.set(copy.getServiceClass());
		type.set(copy.getType());
		maxDispenseItems.set(copy.getMaxDispenseItems());
		compound.set(copy.isCompound());
		shutter.set(copy.isShutter());
		shutterControl.set(copy.isShutterControl());
		retractAreas.set(copy.getRetractAreas());
		retractTransportActions.set(copy.getRetractTransportActions());
		retractStackerActions.set(copy.getRetractStackerActions());
		safeDoor.set(copy.isSafeDoor());
		cashBox.set(copy.isCashBox());
		intermediateStacker.set(copy.isIntermediateStacker());
		itemsTakenSensor.set(copy.isItemsTakenSensor());
		positions.set(copy.getPositions());
		moveItems.set(copy.getMoveItems());
		exchangeType.set(copy.getExchangeType());
		extra.set(copy.getExtra());
	}

	public XfsServiceClass getServiceClass() {
		return serviceClass.get();
	}

	public CdmType getType() {
		return type.get();
	}

	public int getMaxDispenseItems() {
		return maxDispenseItems.get();
	}

	public boolean isCompound() {
		return compound.get();
	}

	public boolean isShutter() {
		return shutter.get();
	}

	public boolean isShutterControl() {
		return shutterControl.get();
	}

	public Set<RetractArea> getRetractAreas() {
		return retractAreas.get();
	}

	public Set<RetractStackerActions> getRetractTransportActions() {
		return retractTransportActions.get();
	}

	public Set<RetractStackerActions> getRetractStackerActions() {
		return retractStackerActions.get();
	}

	public boolean isSafeDoor() {
		return safeDoor.get();
	}

	public boolean isCashBox() {
		return cashBox.get();
	}

	public boolean isIntermediateStacker() {
		return intermediateStacker.get();
	}

	public boolean isItemsTakenSensor() {
		return itemsTakenSensor.get();
	}

	public Set<Position> getPositions() {
		return positions.get();
	}

	public Set<MoveItems> getMoveItems() {
		return moveItems.get();
	}

	public Set<ExchangeType> getExchangeType() {
		return exchangeType.get();
	}

	public Map<String, String> getExtra() {
		return extra.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getServiceClass()).append(getType()).append(getMaxDispenseItems()).append(isCompound()).append(isShutter()).append(isShutterControl())
				.append(getRetractAreas()).append(getRetractTransportActions()).append(getRetractStackerActions()).append(isSafeDoor()).append(isCashBox())
				.append(isIntermediateStacker()).append(isItemsTakenSensor()).append(getPositions()).append(getMoveItems()).append(getExchangeType()).append(getExtra())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CdmCaps3) {
			CdmCaps3 cdmCaps3 = (CdmCaps3) obj;
			return new EqualsBuilder().append(getServiceClass(), cdmCaps3.getServiceClass()).append(getType(), cdmCaps3.getType())
					.append(getMaxDispenseItems(), cdmCaps3.getMaxDispenseItems()).append(isCompound(), cdmCaps3.isCompound()).append(isShutter(), cdmCaps3.isShutter())
					.append(isShutterControl(), cdmCaps3.isShutterControl()).append(getRetractAreas(), cdmCaps3.getRetractAreas())
					.append(getRetractTransportActions(), cdmCaps3.getRetractTransportActions()).append(getRetractStackerActions(), cdmCaps3.getRetractStackerActions())
					.append(isSafeDoor(), cdmCaps3.isSafeDoor()).append(isCashBox(), cdmCaps3.isCashBox()).append(isIntermediateStacker(), cdmCaps3.isIntermediateStacker())
					.append(isItemsTakenSensor(), cdmCaps3.isItemsTakenSensor()).append(getPositions(), cdmCaps3.getPositions()).append(getMoveItems(), cdmCaps3.getMoveItems())
					.append(getExchangeType(), cdmCaps3.getExchangeType()).append(getExtra(), cdmCaps3.getExtra()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("serviceClass", getServiceClass()).append("type", getType()).append("maxDispenseItems", getMaxDispenseItems())
				.append("compound", isCompound()).append("shutter", isShutter()).append("shutterControl", isShutterControl()).append("retractAreas", getRetractAreas())
				.append("retractTransportActions", getRetractTransportActions()).append("retractStackerActions", getRetractStackerActions()).append("safeDoor", isSafeDoor())
				.append("cashBox", isCashBox()).append("intermediateStacker", isIntermediateStacker()).append("itemsTakenSensor", isItemsTakenSensor())
				.append("positions", getPositions()).append("moveItems", getMoveItems()).append("exchangeType", getExchangeType()).append("extra", getExtra()).toString();
	}
}
