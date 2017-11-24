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

package at.o2xfs.xfs.cdm.v3_30;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.cdm.IncompleteRetractReason;
import at.o2xfs.xfs.cdm.v3_20.ItemNumberList3_20;
import at.o2xfs.xfs.win32.XfsWord;

public class IncompleteRetract3_30 extends Struct {

	protected final ItemNumberList3_20 itemNumberList = new ItemNumberList3_20();
	protected final XfsWord<IncompleteRetractReason> reason = new XfsWord<>(IncompleteRetractReason.class);

	protected IncompleteRetract3_30() {
		add(itemNumberList);
		add(reason);
	}

	public IncompleteRetract3_30(Pointer p) {
		this();
		assignBuffer(p);
	}

	public IncompleteRetract3_30(IncompleteRetract3_30 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(IncompleteRetract3_30 copy) {
		itemNumberList.set(copy.getItemNumberList());
		reason.set(copy.getReason());
	}

	public ItemNumberList3_20 getItemNumberList() {
		return new ItemNumberList3_20(itemNumberList);
	}

	public IncompleteRetractReason getReason() {
		return reason.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getItemNumberList()).append(getReason()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IncompleteRetract3_30) {
			IncompleteRetract3_30 incompleteRetract = (IncompleteRetract3_30) obj;
			return new EqualsBuilder().append(getItemNumberList(), incompleteRetract.getItemNumberList()).append(getReason(), incompleteRetract.getReason()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("itemNumberList", getItemNumberList()).append("reason", getReason()).toString();
	}
}