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

package at.o2xfs.xfs.v3_00.cim;

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
import at.o2xfs.xfs.cim.RetractAction;
import at.o2xfs.xfs.cim.ExchangeType;
import at.o2xfs.xfs.cim.Position;
import at.o2xfs.xfs.cim.RetractArea;
import at.o2xfs.xfs.cim.Type;
import at.o2xfs.xfs.win32.XfsWord;
import at.o2xfs.xfs.win32.XfsWordBitmask;

public class Capabilities3 extends Struct {

	protected final XfsWord<XfsServiceClass> serviceClass = new XfsWord<>(XfsServiceClass.class);
	protected final XfsWord<Type> type = new XfsWord<>(Type.class);
	protected final WORD maxCashInItems = new WORD();
	protected final BOOL compound = new BOOL();
	protected final BOOL shutter = new BOOL();
	protected final BOOL shutterControl = new BOOL();
	protected final BOOL safeDoor = new BOOL();
	protected final BOOL cashBox = new BOOL();
	protected final BOOL refill = new BOOL();
	protected final WORD intermediateStacker = new WORD();
	protected final BOOL itemsTakenSensor = new BOOL();
	protected final BOOL itemsInsertedSensor = new BOOL();
	protected final XfsWordBitmask<Position> positions = new XfsWordBitmask<>(Position.class);
	protected final XfsWordBitmask<ExchangeType> exchangeType = new XfsWordBitmask<>(ExchangeType.class);
	protected final XfsWordBitmask<RetractArea> retractAreas = new XfsWordBitmask<>(RetractArea.class);
	protected final XfsWordBitmask<RetractAction> retractTransportActions = new XfsWordBitmask<>(RetractAction.class);
	protected final XfsWordBitmask<RetractAction> retractStackerActions = new XfsWordBitmask<>(RetractAction.class);
	protected final XfsExtra extra = new XfsExtra();

	protected Capabilities3() {
		add(serviceClass);
		add(type);
		add(maxCashInItems);
		add(compound);
		add(shutter);
		add(shutterControl);
		add(safeDoor);
		add(cashBox);
		add(refill);
		add(intermediateStacker);
		add(itemsTakenSensor);
		add(itemsInsertedSensor);
		add(positions);
		add(exchangeType);
		add(retractAreas);
		add(retractTransportActions);
		add(retractStackerActions);
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
		maxCashInItems.set(copy.getMaxCashInItems());
		compound.set(copy.isCompound());
		shutter.set(copy.isShutter());
		shutterControl.set(copy.isShutterControl());
		safeDoor.set(copy.isSafeDoor());
		cashBox.set(copy.isCashBox());
		refill.set(copy.isRefill());
		intermediateStacker.set(copy.getIntermediateStacker());
		itemsTakenSensor.set(copy.isItemsTakenSensor());
		itemsInsertedSensor.set(copy.isItemsInsertedSensor());
		positions.set(copy.getPositions());
		exchangeType.set(copy.getExchangeType());
		retractAreas.set(copy.getRetractAreas());
		retractTransportActions.set(copy.getRetractTransportActions());
		retractStackerActions.set(copy.getRetractStackerActions());
		extra.set(copy.getExtra());
	}

	public XfsServiceClass getServiceClass() {
		return serviceClass.get();
	}

	public Type getType() {
		return type.get();
	}

	public int getMaxCashInItems() {
		return maxCashInItems.get();
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

	public boolean isSafeDoor() {
		return safeDoor.get();
	}

	public boolean isCashBox() {
		return cashBox.get();
	}

	public boolean isRefill() {
		return refill.get();
	}

	public int getIntermediateStacker() {
		return intermediateStacker.get();
	}

	public boolean isItemsTakenSensor() {
		return itemsTakenSensor.get();
	}

	public boolean isItemsInsertedSensor() {
		return itemsInsertedSensor.get();
	}

	public Set<Position> getPositions() {
		return positions.get();
	}

	public Set<ExchangeType> getExchangeType() {
		return exchangeType.get();
	}

	public Set<RetractArea> getRetractAreas() {
		return retractAreas.get();
	}

	public Set<RetractAction> getRetractTransportActions() {
		return retractTransportActions.get();
	}

	public Set<RetractAction> getRetractStackerActions() {
		return retractStackerActions.get();
	}

	public Map<String, String> getExtra() {
		return extra.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getServiceClass()).append(getType()).append(getMaxCashInItems()).append(isCompound()).append(isShutter()).append(isShutterControl())
				.append(isSafeDoor()).append(isCashBox()).append(isRefill()).append(getIntermediateStacker()).append(isItemsTakenSensor()).append(isItemsInsertedSensor())
				.append(getPositions()).append(getExchangeType()).append(getRetractAreas()).append(getRetractTransportActions()).append(getRetractStackerActions())
				.append(getExtra()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Capabilities3) {
			Capabilities3 capabilities3 = (Capabilities3) obj;
			return new EqualsBuilder().append(getServiceClass(), capabilities3.getServiceClass()).append(getType(), capabilities3.getType())
					.append(getMaxCashInItems(), capabilities3.getMaxCashInItems()).append(isCompound(), capabilities3.isCompound()).append(isShutter(), capabilities3.isShutter())
					.append(isShutterControl(), capabilities3.isShutterControl()).append(isSafeDoor(), capabilities3.isSafeDoor()).append(isCashBox(), capabilities3.isCashBox())
					.append(isRefill(), capabilities3.isRefill()).append(getIntermediateStacker(), capabilities3.getIntermediateStacker())
					.append(isItemsTakenSensor(), capabilities3.isItemsTakenSensor()).append(isItemsInsertedSensor(), capabilities3.isItemsInsertedSensor())
					.append(getPositions(), capabilities3.getPositions()).append(getExchangeType(), capabilities3.getExchangeType())
					.append(getRetractAreas(), capabilities3.getRetractAreas()).append(getRetractTransportActions(), capabilities3.getRetractTransportActions())
					.append(getRetractStackerActions(), capabilities3.getRetractStackerActions()).append(getExtra(), capabilities3.getExtra()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("serviceClass", getServiceClass()).append("type", getType()).append("maxCashInItems", getMaxCashInItems())
				.append("compound", isCompound()).append("shutter", isShutter()).append("shutterControl", isShutterControl()).append("safeDoor", isSafeDoor())
				.append("cashBox", isCashBox()).append("refill", isRefill()).append("intermediateStacker", getIntermediateStacker())
				.append("itemsTakenSensor", isItemsTakenSensor()).append("itemsInsertedSensor", isItemsInsertedSensor()).append("positions", getPositions())
				.append("exchangeType", getExchangeType()).append("retractAreas", getRetractAreas()).append("retractTransportActions", getRetractTransportActions())
				.append("retractStackerActions", getRetractStackerActions()).append("extra", getExtra()).toString();
	}
}
