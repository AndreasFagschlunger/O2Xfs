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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;

public class ReplenishInfoResult320 extends Struct {

	protected final Pointer replenishTargets = new Pointer();

	protected ReplenishInfoResult320() {
		add(replenishTargets);
	}

	public ReplenishInfoResult320(Pointer p) {
		this();
		assignBuffer(p);
	}

	public ReplenishInfoResult320(ReplenishInfoResult320 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(ReplenishInfoResult320 copy) {
		replenishTargets.pointTo(new ReplenishInfoTargets(copy.getReplenishTargets()));
	}

	public ReplenishInfoTarget320[] getReplenishTargets() {
		return new ReplenishInfoTargets(replenishTargets).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getReplenishTargets()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ReplenishInfoResult320) {
			ReplenishInfoResult320 replenishInfoResult320 = (ReplenishInfoResult320) obj;
			return new EqualsBuilder().append(getReplenishTargets(), replenishInfoResult320.getReplenishTargets()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("replenishTargets", getReplenishTargets()).toString();
	}
}
