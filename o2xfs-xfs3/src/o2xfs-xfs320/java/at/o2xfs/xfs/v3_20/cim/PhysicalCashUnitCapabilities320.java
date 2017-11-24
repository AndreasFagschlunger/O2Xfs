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

package at.o2xfs.xfs.v3_20.cim;

import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.xfs.XfsExtra;

public class PhysicalCashUnitCapabilities320 extends Struct {

	protected final LPSTR physicalPositionName = new LPSTR();
	protected final ULONG maximum = new ULONG();
	protected final BOOL hardwareSensors = new BOOL();
	protected final XfsExtra extra = new XfsExtra();

	protected PhysicalCashUnitCapabilities320() {
		add(physicalPositionName);
		add(maximum);
		add(hardwareSensors);
		add(extra);
	}

	public PhysicalCashUnitCapabilities320(Pointer p) {
		this();
		assignBuffer(p);
	}

	public PhysicalCashUnitCapabilities320(PhysicalCashUnitCapabilities320 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(PhysicalCashUnitCapabilities320 copy) {
		physicalPositionName.set(copy.getPhysicalPositionName());
		maximum.set(copy.getMaximum());
		hardwareSensors.set(copy.isHardwareSensors());
		extra.set(copy.getExtra());
	}

	public String getPhysicalPositionName() {
		return physicalPositionName.get();
	}

	public long getMaximum() {
		return maximum.get();
	}

	public boolean isHardwareSensors() {
		return hardwareSensors.get();
	}

	public Map<String, String> getExtra() {
		return extra.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getPhysicalPositionName()).append(getMaximum()).append(isHardwareSensors()).append(getExtra()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PhysicalCashUnitCapabilities320) {
			PhysicalCashUnitCapabilities320 physicalCashUnitCapabilities320 = (PhysicalCashUnitCapabilities320) obj;
			return new EqualsBuilder().append(getPhysicalPositionName(), physicalCashUnitCapabilities320.getPhysicalPositionName())
					.append(getMaximum(), physicalCashUnitCapabilities320.getMaximum()).append(isHardwareSensors(), physicalCashUnitCapabilities320.isHardwareSensors())
					.append(getExtra(), physicalCashUnitCapabilities320.getExtra()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("physicalPositionName", getPhysicalPositionName()).append("maximum", getMaximum()).append("hardwareSensors", isHardwareSensors())
				.append("extra", getExtra()).toString();
	}
}
