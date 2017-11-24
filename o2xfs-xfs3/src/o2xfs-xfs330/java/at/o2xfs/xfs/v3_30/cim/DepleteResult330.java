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

package at.o2xfs.xfs.cim.v3_30;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;

public class DepleteResult3_30 extends Struct {

	protected final ULONG numberOfItemsReceived = new ULONG();
	protected final ULONG numberOfItemsRejected = new ULONG();
	protected final Pointer depleteSourceResults = new Pointer();

	protected DepleteResult3_30() {
		add(numberOfItemsReceived);
		add(numberOfItemsRejected);
		add(depleteSourceResults);
	}

	public DepleteResult3_30(Pointer p) {
		this();
		assignBuffer(p);
	}

	public DepleteResult3_30(DepleteResult3_30 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(DepleteResult3_30 copy) {
		numberOfItemsReceived.set(copy.getNumberOfItemsReceived());
		numberOfItemsRejected.set(copy.getNumberOfItemsRejected());
		depleteSourceResults.pointTo(new DepleteSourceResults(copy.getDepleteSourceResults()));
	}

	public long getNumberOfItemsReceived() {
		return numberOfItemsReceived.get();
	}

	public long getNumberOfItemsRejected() {
		return numberOfItemsRejected.get();
	}

	public DepleteSourceResult3_30[] getDepleteSourceResults() {
		return new DepleteSourceResults(depleteSourceResults).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getNumberOfItemsReceived()).append(getNumberOfItemsRejected()).append(getDepleteSourceResults()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DepleteResult3_30) {
			DepleteResult3_30 depleteResult3_30 = (DepleteResult3_30) obj;
			return new EqualsBuilder().append(getNumberOfItemsReceived(), depleteResult3_30.getNumberOfItemsReceived())
					.append(getNumberOfItemsRejected(), depleteResult3_30.getNumberOfItemsRejected()).append(getDepleteSourceResults(), depleteResult3_30.getDepleteSourceResults())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("numberOfItemsReceived", getNumberOfItemsReceived()).append("numberOfItemsRejected", getNumberOfItemsRejected())
				.append("depleteSourceResults", getDepleteSourceResults()).toString();
	}
}