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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.XfsExtra;
import at.o2xfs.xfs.cim.Acceptor;
import at.o2xfs.xfs.cim.BankNoteReader;
import at.o2xfs.xfs.cim.CimDeviceState;
import at.o2xfs.xfs.cim.IntermediateStacker;
import at.o2xfs.xfs.cim.SafeDoor;
import at.o2xfs.xfs.cim.StackerItems;
import at.o2xfs.xfs.win32.XfsWord;

public class Status3 extends Struct {

	protected final XfsWord<CimDeviceState> device = new XfsWord<>(CimDeviceState.class);
	protected final XfsWord<SafeDoor> safeDoor = new XfsWord<>(SafeDoor.class);
	protected final XfsWord<Acceptor> acceptor = new XfsWord<>(Acceptor.class);
	protected final XfsWord<IntermediateStacker> intermediateStacker = new XfsWord<>(IntermediateStacker.class);
	protected final XfsWord<StackerItems> stackerItems = new XfsWord<>(StackerItems.class);
	protected final XfsWord<BankNoteReader> banknoteReader = new XfsWord<>(BankNoteReader.class);
	protected final BOOL dropBox = new BOOL();
	protected final Pointer positions = new Pointer();
	protected final XfsExtra extra = new XfsExtra();

	protected Status3() {
		add(device);
		add(safeDoor);
		add(acceptor);
		add(intermediateStacker);
		add(stackerItems);
		add(banknoteReader);
		add(dropBox);
		add(positions);
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
		device.set(copy.getDevice());
		safeDoor.set(copy.getSafeDoor());
		acceptor.set(copy.getAcceptor());
		intermediateStacker.set(copy.getIntermediateStacker());
		stackerItems.set(copy.getStackerItems());
		banknoteReader.set(copy.getBanknoteReader());
		dropBox.set(copy.isDropBox());
		positions.pointTo(new Positions3(copy.getPositions()));
		extra.set(copy.getExtra());
	}

	public CimDeviceState getDevice() {
		return device.get();
	}

	public SafeDoor getSafeDoor() {
		return safeDoor.get();
	}

	public Acceptor getAcceptor() {
		return acceptor.get();
	}

	public IntermediateStacker getIntermediateStacker() {
		return intermediateStacker.get();
	}

	public StackerItems getStackerItems() {
		return stackerItems.get();
	}

	public BankNoteReader getBanknoteReader() {
		return banknoteReader.get();
	}

	public boolean isDropBox() {
		return dropBox.get();
	}

	public Inpos3[] getPositions() {
		return new Positions3(positions).get();
	}

	public Map<String, String> getExtra() {
		return extra.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getDevice()).append(getSafeDoor()).append(getAcceptor()).append(getIntermediateStacker()).append(getStackerItems())
				.append(getBanknoteReader()).append(isDropBox()).append(getPositions()).append(getExtra()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Status3) {
			Status3 status3 = (Status3) obj;
			return new EqualsBuilder().append(getDevice(), status3.getDevice()).append(getSafeDoor(), status3.getSafeDoor()).append(getAcceptor(), status3.getAcceptor())
					.append(getIntermediateStacker(), status3.getIntermediateStacker()).append(getStackerItems(), status3.getStackerItems())
					.append(getBanknoteReader(), status3.getBanknoteReader()).append(isDropBox(), status3.isDropBox()).append(getPositions(), status3.getPositions())
					.append(getExtra(), status3.getExtra()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("device", getDevice()).append("safeDoor", getSafeDoor()).append("acceptor", getAcceptor())
				.append("intermediateStacker", getIntermediateStacker()).append("stackerItems", getStackerItems()).append("banknoteReader", getBanknoteReader())
				.append("dropBox", isDropBox()).append("positions", getPositions()).append("extra", getExtra()).toString();
	}
}
