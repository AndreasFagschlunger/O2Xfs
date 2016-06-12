/*
 * Copyright (c) 2016, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.xfs.cim.v3_20;

import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.XfsExtra;

public class CashUnitCapabilities3_20 extends Struct {

	protected final USHORT number = new USHORT();
	protected final USHORT numPhysicalCUs = new USHORT();
	protected final Pointer physical = new Pointer();
	protected final BOOL retractNoteCountThresholds = new BOOL();
	protected final XfsExtra extra = new XfsExtra();

	protected CashUnitCapabilities3_20() {
		add(number);
		add(numPhysicalCUs);
		add(physical);
		add(retractNoteCountThresholds);
		add(extra);
	}

	public CashUnitCapabilities3_20(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CashUnitCapabilities3_20(CashUnitCapabilities3_20 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CashUnitCapabilities3_20 copy) {
		number.set(copy.getNumber());
		numPhysicalCUs.set(copy.getNumPhysicalCUs());
		physical.pointTo(new PhysicalCashUnitCapabilities3_20Array(copy.getPhysical()));
		retractNoteCountThresholds.set(copy.isRetractNoteCountThresholds());
		extra.set(copy.getExtra());
	}

	public int getNumber() {
		return number.get();
	}

	private int getNumPhysicalCUs() {
		return numPhysicalCUs.get();
	}

	public PhysicalCashUnitCapabilities3_20[] getPhysical() {
		return new PhysicalCashUnitCapabilities3_20Array(physical, getNumPhysicalCUs()).get();
	}

	public boolean isRetractNoteCountThresholds() {
		return retractNoteCountThresholds.get();
	}

	public Map<String, String> getExtra() {
		return extra.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getNumber()).append(getNumPhysicalCUs()).append(getPhysical()).append(isRetractNoteCountThresholds()).append(getExtra()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CashUnitCapabilities3_20) {
			CashUnitCapabilities3_20 cashUnitCapabilities3_20 = (CashUnitCapabilities3_20) obj;
			return new EqualsBuilder().append(getNumber(), cashUnitCapabilities3_20.getNumber()).append(getNumPhysicalCUs(), cashUnitCapabilities3_20.getNumPhysicalCUs())
					.append(getPhysical(), cashUnitCapabilities3_20.getPhysical()).append(isRetractNoteCountThresholds(), cashUnitCapabilities3_20.isRetractNoteCountThresholds())
					.append(getExtra(), cashUnitCapabilities3_20.getExtra()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("number", getNumber()).append("numPhysicalCUs", getNumPhysicalCUs()).append("physical", getPhysical())
				.append("retractNoteCountThresholds", isRetractNoteCountThresholds()).append("extra", getExtra()).toString();
	}
}