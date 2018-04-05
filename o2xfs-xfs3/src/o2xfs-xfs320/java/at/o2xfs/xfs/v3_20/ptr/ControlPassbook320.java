/*
 * Copyright (c) 2018, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.xfs.v3_20.ptr;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.ptr.ControlPassbookAction;
import at.o2xfs.xfs.win32.XfsWord;

public class ControlPassbook320 extends Struct {

	protected final XfsWord<ControlPassbookAction> action = new XfsWord<>(ControlPassbookAction.class);
	protected final USHORT count = new USHORT();

	protected ControlPassbook320() {
		add(action);
		add(count);
	}

	public ControlPassbook320(Pointer p) {
		this();
		assignBuffer(p);
	}

	public ControlPassbook320(ControlPassbook320 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(ControlPassbook320 copy) {
		action.set(copy.getAction());
		count.set(copy.getCount());
	}

	public ControlPassbookAction getAction() {
		return action.get();
	}

	public int getCount() {
		return count.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getAction()).append(getCount()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ControlPassbook320) {
			ControlPassbook320 controlPassbook320 = (ControlPassbook320) obj;
			return new EqualsBuilder()
					.append(getAction(), controlPassbook320.getAction())
					.append(getCount(), controlPassbook320.getCount())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("action", getAction()).append("count", getCount()).toString();
	}
}
