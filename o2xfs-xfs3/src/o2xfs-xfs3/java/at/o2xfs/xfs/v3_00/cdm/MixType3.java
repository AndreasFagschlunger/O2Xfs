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

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.cdm.MixType;
import at.o2xfs.xfs.win32.XfsWord;

public class MixType3 extends Struct {

	protected final USHORT mixNumber = new USHORT();
	protected final XfsWord<MixType> mixType = new XfsWord<>(MixType.class);
	protected final USHORT subType = new USHORT();
	protected final LPSTR name = new LPSTR();

	protected MixType3() {
		add(mixNumber);
		add(mixType);
		add(subType);
		add(name);
	}

	public MixType3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public MixType3(MixType3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(MixType3 copy) {
		mixNumber.set(copy.getMixNumber());
		mixType.set(copy.getMixType());
		subType.set(copy.getSubType());
		name.set(copy.getName());
	}

	public int getMixNumber() {
		return mixNumber.get();
	}

	public MixType getMixType() {
		return mixType.get();
	}

	public int getSubType() {
		return subType.get();
	}

	public String getName() {
		return name.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getMixNumber()).append(getMixType()).append(getSubType()).append(getName()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MixType3) {
			MixType3 mixType3 = (MixType3) obj;
			return new EqualsBuilder().append(getMixNumber(), mixType3.getMixNumber()).append(getMixType(), mixType3.getMixType()).append(getSubType(), mixType3.getSubType())
					.append(getName(), mixType3.getName()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("mixNumber", getMixNumber()).append("mixType", getMixType()).append("subType", getSubType()).append("name", getName()).toString();
	}
}
