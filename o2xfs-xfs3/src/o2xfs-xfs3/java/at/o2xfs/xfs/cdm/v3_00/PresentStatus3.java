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

package at.o2xfs.xfs.cdm.v3_00;

import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.XfsExtra;
import at.o2xfs.xfs.cdm.PresentState;
import at.o2xfs.xfs.win32.XfsWord;

public class PresentStatus3 extends Struct {

	protected final Pointer denomination = new Pointer();
	protected final XfsWord<PresentState> presentState = new XfsWord<>(PresentState.class);
	protected final XfsExtra extra = new XfsExtra();

	protected PresentStatus3() {
		add(denomination);
		add(presentState);
		add(extra);
	}

	public PresentStatus3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public PresentStatus3(PresentStatus3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(PresentStatus3 copy) {
		denomination.pointTo(new Denomination3(copy.getDenomination()));
		presentState.set(copy.getPresentState());
		extra.set(copy.getExtra());
	}

	public Denomination3 getDenomination() {
		return new Denomination3(denomination);
	}

	public PresentState getPresentState() {
		return presentState.get();
	}

	public Map<String, String> getExtra() {
		return extra.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getDenomination()).append(getPresentState()).append(getExtra()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PresentStatus3) {
			PresentStatus3 presentStatus3 = (PresentStatus3) obj;
			return new EqualsBuilder().append(getDenomination(), presentStatus3.getDenomination()).append(getPresentState(), presentStatus3.getPresentState())
					.append(getExtra(), presentStatus3.getExtra()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("denomination", getDenomination()).append("presentState", getPresentState()).append("extra", getExtra()).toString();
	}
}