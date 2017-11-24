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

package at.o2xfs.xfs.v3_10.cim;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;

public class P6CompareResult310 extends Struct {

	protected final USHORT count = new USHORT();
	protected final Pointer p6SignaturesIndex = new Pointer();

	protected P6CompareResult310() {
		add(count);
		add(p6SignaturesIndex);
	}

	public P6CompareResult310(Pointer p) {
		this();
		assignBuffer(p);
	}

	public P6CompareResult310(P6CompareResult310 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(P6CompareResult310 copy) {
		count.set(copy.getCount());
		p6SignaturesIndex.pointTo(new P6SignaturesIndex310Array(copy.getP6SignaturesIndex()));
	}

	private int getCount() {
		return count.get();
	}

	public P6SignaturesIndex310[] getP6SignaturesIndex() {
		return new P6SignaturesIndex310Array(p6SignaturesIndex, getCount()).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getCount()).append(getP6SignaturesIndex()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof P6CompareResult310) {
			P6CompareResult310 p6CompareResult310 = (P6CompareResult310) obj;
			return new EqualsBuilder().append(getCount(), p6CompareResult310.getCount()).append(getP6SignaturesIndex(), p6CompareResult310.getP6SignaturesIndex()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("count", getCount()).append("p6SignaturesIndex", getP6SignaturesIndex()).toString();
	}
}
