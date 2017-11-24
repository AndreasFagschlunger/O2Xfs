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
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.XfsExtra;

public class CashUnitCapabilities320 extends Struct {

	protected final USHORT number = new USHORT();
	protected final USHORT numPhysicalCUs = new USHORT();
	protected final Pointer physical = new Pointer();
	protected final BOOL retractNoteCountThresholds = new BOOL();
	protected final XfsExtra extra = new XfsExtra();

	protected CashUnitCapabilities320() {
		add(number);
		add(numPhysicalCUs);
		add(physical);
		add(retractNoteCountThresholds);
		add(extra);
	}

	public CashUnitCapabilities320(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CashUnitCapabilities320(CashUnitCapabilities320 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CashUnitCapabilities320 copy) {
		number.set(copy.getNumber());
		numPhysicalCUs.set(copy.getNumPhysicalCUs());
		physical.pointTo(new PhysicalCashUnitCapabilities320Array(copy.getPhysical()));
		retractNoteCountThresholds.set(copy.isRetractNoteCountThresholds());
		extra.set(copy.getExtra());
	}

	public int getNumber() {
		return number.get();
	}

	private int getNumPhysicalCUs() {
		return numPhysicalCUs.get();
	}

	public PhysicalCashUnitCapabilities320[] getPhysical() {
		return new PhysicalCashUnitCapabilities320Array(physical, getNumPhysicalCUs()).get();
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
		if (obj instanceof CashUnitCapabilities320) {
			CashUnitCapabilities320 cashUnitCapabilities320 = (CashUnitCapabilities320) obj;
			return new EqualsBuilder().append(getNumber(), cashUnitCapabilities320.getNumber()).append(getNumPhysicalCUs(), cashUnitCapabilities320.getNumPhysicalCUs())
					.append(getPhysical(), cashUnitCapabilities320.getPhysical()).append(isRetractNoteCountThresholds(), cashUnitCapabilities320.isRetractNoteCountThresholds())
					.append(getExtra(), cashUnitCapabilities320.getExtra()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("number", getNumber()).append("numPhysicalCUs", getNumPhysicalCUs()).append("physical", getPhysical())
				.append("retractNoteCountThresholds", isRetractNoteCountThresholds()).append("extra", getExtra()).toString();
	}
}
