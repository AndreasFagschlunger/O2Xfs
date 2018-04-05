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

package at.o2xfs.xfs.v3_00.ptr;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.ptr.PaperSource;
import at.o2xfs.xfs.ptr.PaperStatus;
import at.o2xfs.xfs.win32.XfsWord;

public class PaperThreshold3 extends Struct {

	protected final XfsWord<PaperSource> paperSource = new XfsWord<>(PaperSource.class);
	protected final XfsWord<PaperStatus> paperThreshold = new XfsWord<>(PaperStatus.class);

	protected PaperThreshold3() {
		add(paperSource);
		add(paperThreshold);
	}

	public PaperThreshold3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public PaperThreshold3(PaperThreshold3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(PaperThreshold3 copy) {
		paperSource.set(copy.getPaperSource());
		paperThreshold.set(copy.getPaperThreshold());
	}

	public PaperSource getPaperSource() {
		return paperSource.get();
	}

	public PaperStatus getPaperThreshold() {
		return paperThreshold.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getPaperSource()).append(getPaperThreshold()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PaperThreshold3) {
			PaperThreshold3 paperThreshold3 = (PaperThreshold3) obj;
			return new EqualsBuilder()
					.append(getPaperSource(), paperThreshold3.getPaperSource())
					.append(getPaperThreshold(), paperThreshold3.getPaperThreshold())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("paperSource", getPaperSource())
				.append("paperThreshold", getPaperThreshold())
				.toString();
	}
}
