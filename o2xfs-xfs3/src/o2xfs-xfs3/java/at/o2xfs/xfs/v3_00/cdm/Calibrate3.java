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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;

public class Calibrate3 extends Struct {

	protected final USHORT number = new USHORT();
	protected final USHORT numOfBills = new USHORT();
	protected final Pointer position = new Pointer();

	protected Calibrate3() {
		add(number);
		add(numOfBills);
		add(position);
	}

	public Calibrate3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Calibrate3(Calibrate3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Calibrate3 copy) {
		number.set(copy.getNumber());
		numOfBills.set(copy.getNumOfBills());
		position.pointTo(new Pointer(copy.getPosition()));
	}

	public int getNumber() {
		return number.get();
	}

	public int getNumOfBills() {
		return numOfBills.get();
	}

	public ItemPosition3 getPosition() {
		return new ItemPosition3(new ItemPosition3(new Pointer(position)));
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getNumber()).append(getNumOfBills()).append(getPosition()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Calibrate3) {
			Calibrate3 calibrate3 = (Calibrate3) obj;
			return new EqualsBuilder().append(getNumber(), calibrate3.getNumber()).append(getNumOfBills(), calibrate3.getNumOfBills())
					.append(getPosition(), calibrate3.getPosition()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("number", getNumber()).append("numOfBills", getNumOfBills()).append("position", getPosition()).toString();
	}
}
