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

package at.o2xfs.xfs.v3_00.cdm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.UShortArray;

public class MixRow3 extends Struct {

	protected final ULONG amount = new ULONG();
	protected final Pointer mixture = new Pointer();
	private final int numberOfColumns;

	protected MixRow3(int numberOfColumns) {
		add(amount);
		add(mixture);
		this.numberOfColumns = numberOfColumns;
	}

	public MixRow3(Pointer p, int numberOfColumns) {
		this(numberOfColumns);
		assignBuffer(p);
	}

	public MixRow3(MixRow3 copy) {
		this(copy.numberOfColumns);
		allocate();
		set(copy);
	}

	protected void set(MixRow3 copy) {
		amount.set(copy.getAmount());
		mixture.pointTo(new UShortArray(copy.getMixture()));
	}

	public long getAmount() {
		return amount.get();
	}

	public int[] getMixture() {
		return new UShortArray(mixture, numberOfColumns).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getAmount()).append(getMixture()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MixRow3) {
			MixRow3 mixRow3 = (MixRow3) obj;
			return new EqualsBuilder().append(getAmount(), mixRow3.getAmount()).append(getMixture(), mixRow3.getMixture()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("amount", getAmount()).append("mixture", getMixture()).toString();
	}
}
