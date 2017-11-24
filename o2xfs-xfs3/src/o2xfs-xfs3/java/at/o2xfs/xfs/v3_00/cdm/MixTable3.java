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

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULongArray;
import at.o2xfs.win32.USHORT;

public class MixTable3 extends Struct {

	protected final USHORT mixNumber = new USHORT();
	protected final LPSTR name = new LPSTR();
	protected final USHORT rows = new USHORT();
	protected final USHORT cols = new USHORT();
	protected final Pointer mixHeader = new Pointer();
	protected final Pointer mixRows = new Pointer();

	protected MixTable3() {
		add(mixNumber);
		add(name);
		add(rows);
		add(cols);
		add(mixHeader);
		add(mixRows);
	}

	public MixTable3(Pointer p) {
		this();
		assignBuffer(p);
	}

	public MixTable3(MixTable3 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(MixTable3 copy) {
		mixNumber.set(copy.getMixNumber());
		name.set(copy.getName());
		rows.set(copy.getRows());
		cols.set(copy.getCols());
		mixHeader.pointTo(new ULongArray(copy.getMixHeader()));
		mixRows.pointTo(new MixRows3Array(copy.getMixRows(), getCols()));
	}

	public int getMixNumber() {
		return mixNumber.get();
	}

	public String getName() {
		return name.get();
	}

	public int getRows() {
		return rows.get();
	}

	public int getCols() {
		return cols.get();
	}

	public long[] getMixHeader() {
		return new ULongArray(mixHeader, getCols()).get();
	}

	public MixRow3[] getMixRows() {
		return new MixRows3Array(mixRows, getRows(), getCols()).get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getMixNumber()).append(getName()).append(getRows()).append(getCols()).append(getMixHeader()).append(getMixRows()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MixTable3) {
			MixTable3 mixTable3 = (MixTable3) obj;
			return new EqualsBuilder().append(getMixNumber(), mixTable3.getMixNumber()).append(getName(), mixTable3.getName()).append(getRows(), mixTable3.getRows())
					.append(getCols(), mixTable3.getCols()).append(getMixHeader(), mixTable3.getMixHeader()).append(getMixRows(), mixTable3.getMixRows()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("mixNumber", getMixNumber()).append("name", getName()).append("rows", getRows()).append("cols", getCols())
				.append("mixHeader", getMixHeader()).append("mixRows", getMixRows()).toString();
	}
}
