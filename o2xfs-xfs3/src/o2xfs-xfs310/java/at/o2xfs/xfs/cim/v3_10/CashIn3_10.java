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

package at.o2xfs.xfs.cim.v3_10;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.ULONG;
import at.o2xfs.xfs.cdm.CdmType;
import at.o2xfs.xfs.cim.v3_00.CashIn3;
import at.o2xfs.xfs.cim.v3_00.NoteIDs;
import at.o2xfs.xfs.win32.XfsWord;

public class CashIn3_10 extends CashIn3 {

	protected final Pointer noteIDs = new Pointer();
	protected final XfsWord<CdmType> cdmType = new XfsWord<>(CdmType.class);
	protected final LPSTR cashUnitName = new LPSTR();
	protected final ULONG initialCount = new ULONG();
	protected final ULONG dispensedCount = new ULONG();
	protected final ULONG presentedCount = new ULONG();
	protected final ULONG retractedCount = new ULONG();
	protected final ULONG rejectCount = new ULONG();
	protected final ULONG minimum = new ULONG();

	protected CashIn3_10() {
		add(noteIDs);
		add(cdmType);
		add(cashUnitName);
		add(initialCount);
		add(dispensedCount);
		add(presentedCount);
		add(retractedCount);
		add(rejectCount);
		add(minimum);
	}

	public CashIn3_10(Pointer p) {
		this();
		assignBuffer(p);
	}

	public CashIn3_10(CashIn3_10 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(CashIn3_10 copy) {
		super.set(copy);
		noteIDs.pointTo(new NoteIDs(copy.getNoteIDs()));
		cdmType.set(copy.getCdmType());
		cashUnitName.set(copy.getCashUnitName());
		initialCount.set(copy.getInitialCount());
		dispensedCount.set(copy.getDispensedCount());
		presentedCount.set(copy.getPresentedCount());
		retractedCount.set(copy.getRetractedCount());
		rejectCount.set(copy.getRejectCount());
		minimum.set(copy.getMinimum());
	}

	public int[] getNoteIDs() {
		return new NoteIDs(noteIDs).get();
	}

	public CdmType getCdmType() {
		return cdmType.get();
	}

	public String getCashUnitName() {
		return cashUnitName.get();
	}

	public long getInitialCount() {
		return initialCount.get();
	}

	public long getDispensedCount() {
		return dispensedCount.get();
	}

	public long getPresentedCount() {
		return presentedCount.get();
	}

	public long getRetractedCount() {
		return retractedCount.get();
	}

	public long getRejectCount() {
		return rejectCount.get();
	}

	public long getMinimum() {
		return minimum.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(getNoteIDs()).append(getCdmType()).append(getCashUnitName()).append(getInitialCount())
				.append(getDispensedCount()).append(getPresentedCount()).append(getRetractedCount()).append(getRejectCount()).append(getMinimum()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CashIn3_10) {
			CashIn3_10 cashIn3_10 = (CashIn3_10) obj;
			return new EqualsBuilder().appendSuper(super.equals(obj)).append(getNoteIDs(), cashIn3_10.getNoteIDs()).append(getCdmType(), cashIn3_10.getCdmType())
					.append(getCashUnitName(), cashIn3_10.getCashUnitName()).append(getInitialCount(), cashIn3_10.getInitialCount())
					.append(getDispensedCount(), cashIn3_10.getDispensedCount()).append(getPresentedCount(), cashIn3_10.getPresentedCount())
					.append(getRetractedCount(), cashIn3_10.getRetractedCount()).append(getRejectCount(), cashIn3_10.getRejectCount()).append(getMinimum(), cashIn3_10.getMinimum())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("noteIDs", getNoteIDs()).append("cdmType", getCdmType()).append("cashUnitName", getCashUnitName())
				.append("initialCount", getInitialCount()).append("dispensedCount", getDispensedCount()).append("presentedCount", getPresentedCount())
				.append("retractedCount", getRetractedCount()).append("rejectCount", getRejectCount()).append("minimum", getMinimum()).toString();
	}
}