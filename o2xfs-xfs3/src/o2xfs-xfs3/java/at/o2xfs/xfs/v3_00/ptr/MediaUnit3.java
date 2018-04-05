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
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.ptr.FormBaseUnit;
import at.o2xfs.xfs.win32.XfsWord;

public class MediaUnit3 extends Struct {

	protected final XfsWord<FormBaseUnit> base = new XfsWord<>(FormBaseUnit.class);
	protected final WORD unitX = new WORD();
	protected final WORD unitY = new WORD();

	protected MediaUnit3() {
		add(base);
		add(unitX);
		add(unitY);
	}

	public MediaUnit3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public MediaUnit3(MediaUnit3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(MediaUnit3 copy) {
		base.set(copy.getBase());
		unitX.set(copy.getUnitX());
		unitY.set(copy.getUnitY());
	}

	public FormBaseUnit getBase() {
		return base.get();
	}

	public int getUnitX() {
		return unitX.get();
	}

	public int getUnitY() {
		return unitY.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getBase()).append(getUnitX()).append(getUnitY()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MediaUnit3) {
			MediaUnit3 mediaUnit3 = (MediaUnit3) obj;
			return new EqualsBuilder()
					.append(getBase(), mediaUnit3.getBase())
					.append(getUnitX(), mediaUnit3.getUnitX())
					.append(getUnitY(), mediaUnit3.getUnitY())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("base", getBase())
				.append("unitX", getUnitX())
				.append("unitY", getUnitY())
				.toString();
	}
}
