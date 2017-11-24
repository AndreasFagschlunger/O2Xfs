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

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cdm.Position;
import at.o2xfs.xfs.win32.XfsWord;

public class ItemPosition3 extends Struct {

	protected final USHORT number = new USHORT();
	protected final Pointer retractArea = new Pointer();
	protected final XfsWord<Position> outputPosition = new XfsWord<>(Position.class);

	protected ItemPosition3() {
		add(number);
		add(retractArea);
		add(outputPosition);
	}

	public ItemPosition3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public ItemPosition3(ItemPosition3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(ItemPosition3 copy) {
		number.set(copy.getNumber());
		Optional<Retract3> retractArea = copy.getRetractArea();
		if (retractArea.isPresent()) {
			this.retractArea.pointTo(retractArea.get());
		}
		outputPosition.set(copy.getOutputPosition());
	}

	public int getNumber() {
		return number.get();
	}

	public Optional<Retract3> getRetractArea() {
		Optional<Retract3> result = Optional.empty();
		if (!Pointer.NULL.equals(retractArea)) {
			result = Optional.of(new Retract3(new Retract3(retractArea)));
		}
		return result;
	}

	public Position getOutputPosition() {
		return outputPosition.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getNumber()).append(getRetractArea()).append(getOutputPosition()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemPosition3) {
			ItemPosition3 itemPosition3 = (ItemPosition3) obj;
			return new EqualsBuilder().append(getNumber(), itemPosition3.getNumber()).append(getRetractArea(), itemPosition3.getRetractArea())
					.append(getOutputPosition(), itemPosition3.getOutputPosition()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("number", getNumber()).append("retractArea", getRetractArea()).append("outputPosition", getOutputPosition()).toString();
	}
}
