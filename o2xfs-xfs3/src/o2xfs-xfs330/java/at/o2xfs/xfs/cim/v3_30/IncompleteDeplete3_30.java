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

package at.o2xfs.xfs.cim.v3_30;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;

public class IncompleteDeplete3_30 extends Struct {

	protected final Pointer deplete = new Pointer();

	protected IncompleteDeplete3_30() {
		add(deplete);
	}

	public IncompleteDeplete3_30(Pointer p) {
		this();
		assignBuffer(p);
	}

	public IncompleteDeplete3_30(IncompleteDeplete3_30 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(IncompleteDeplete3_30 copy) {
		deplete.pointTo(new DepleteResult3_30(copy.getDeplete()));
	}

	public DepleteResult3_30 getDeplete() {
		return new DepleteResult3_30(deplete);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getDeplete()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IncompleteDeplete3_30) {
			IncompleteDeplete3_30 incompleteDeplete3_30 = (IncompleteDeplete3_30) obj;
			return new EqualsBuilder().append(getDeplete(), incompleteDeplete3_30.getDeplete()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("deplete", getDeplete()).toString();
	}
}