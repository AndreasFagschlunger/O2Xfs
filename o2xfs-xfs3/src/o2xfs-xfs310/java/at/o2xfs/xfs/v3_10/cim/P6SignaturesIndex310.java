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

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.USHORT;

public class P6SignaturesIndex310 extends Struct {

	protected final USHORT index = new USHORT();
	protected final USHORT confidenceLevel = new USHORT();
	protected final ULONG length = new ULONG();
	protected final Pointer comparisonData = new Pointer();

	protected P6SignaturesIndex310() {
		add(index);
		add(confidenceLevel);
		add(length);
		add(comparisonData);
	}

	public P6SignaturesIndex310(Pointer p) {
		this();
		assignBuffer(p);
	}

	public P6SignaturesIndex310(P6SignaturesIndex310 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(P6SignaturesIndex310 copy) {
		index.set(copy.getIndex());
		confidenceLevel.set(copy.getConfidenceLevel());
		length.set(copy.getLength());
		if (copy.getComparisonData().isPresent()) {
			comparisonData.pointTo(new ByteArray(copy.getComparisonData().get()));
		}
	}

	public int getIndex() {
		return index.get();
	}

	public int getConfidenceLevel() {
		return confidenceLevel.get();
	}

	private int getLength() {
		return length.intValue();
	}

	public Optional<byte[]> getComparisonData() {
		Optional<byte[]> result = Optional.empty();
		if (getLength() > 0) {
			result = Optional.of(comparisonData.buffer(getLength()).get());
		}
		return result;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getIndex()).append(getConfidenceLevel()).append(getLength()).append(getComparisonData()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof P6SignaturesIndex310) {
			P6SignaturesIndex310 p6SignaturesIndex310 = (P6SignaturesIndex310) obj;
			return new EqualsBuilder().append(getIndex(), p6SignaturesIndex310.getIndex()).append(getConfidenceLevel(), p6SignaturesIndex310.getConfidenceLevel())
					.append(getLength(), p6SignaturesIndex310.getLength()).append(getComparisonData().orElse(null), p6SignaturesIndex310.getComparisonData().orElse(null))
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("index", getIndex()).append("confidenceLevel", getConfidenceLevel()).append("length", getLength())
				.append("comparisonData", getComparisonData()).toString();
	}
}
