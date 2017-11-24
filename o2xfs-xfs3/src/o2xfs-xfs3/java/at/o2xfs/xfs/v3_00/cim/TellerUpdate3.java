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

package at.o2xfs.xfs.v3_00.cim;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.cim.Action;
import at.o2xfs.xfs.win32.XfsWord;

public class TellerUpdate3 extends Struct {

	protected final XfsWord<Action> action = new XfsWord<>(Action.class);
	protected final Pointer tellerDetails = new Pointer();

	protected TellerUpdate3() {
		add(action);
		add(tellerDetails);
	}

	public TellerUpdate3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public TellerUpdate3(TellerUpdate3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(TellerUpdate3 copy) {
		action.set(copy.getAction());
		tellerDetails.pointTo(new TellerDetails3(copy.getTellerDetails()));
	}

	public Action getAction() {
		return action.get();
	}

	public TellerDetails3 getTellerDetails() {
		return new TellerDetails3(tellerDetails);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getAction()).append(getTellerDetails()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TellerUpdate3) {
			TellerUpdate3 tellerUpdate3 = (TellerUpdate3) obj;
			return new EqualsBuilder().append(getAction(), tellerUpdate3.getAction()).append(getTellerDetails(), tellerUpdate3.getTellerDetails()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("action", getAction()).append("tellerDetails", getTellerDetails()).toString();
	}
}
