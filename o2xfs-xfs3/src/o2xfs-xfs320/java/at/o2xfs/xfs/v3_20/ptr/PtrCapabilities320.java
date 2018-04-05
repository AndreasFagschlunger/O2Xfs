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

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.ptr.CoercivityType;
import at.o2xfs.xfs.ptr.ControlPassbookAction;
import at.o2xfs.xfs.ptr.PrintSides;
import at.o2xfs.xfs.v3_10.ptr.PtrCapabilities310;
import at.o2xfs.xfs.win32.XfsWord;
import at.o2xfs.xfs.win32.XfsWordBitmask;

public class PtrCapabilities320 extends PtrCapabilities310 {

	protected final XfsWord<CoercivityType> coercivityType = new XfsWord<>(CoercivityType.class);
	protected final XfsWord<ControlPassbookAction> controlPassbook = new XfsWord<>(ControlPassbookAction.class);
	protected final XfsWordBitmask<PrintSides> printSides = new XfsWordBitmask<>(PrintSides.class);

	protected PtrCapabilities320() {
		add(coercivityType);
		add(controlPassbook);
		add(printSides);
	}

	public PtrCapabilities320(Pointer p) {
		this();
		assignBuffer(p);
	}

	public PtrCapabilities320(PtrCapabilities320 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(PtrCapabilities320 copy) {
		super.set(copy);
		coercivityType.set(copy.getCoercivityType());
		controlPassbook.set(copy.getControlPassbook());
		printSides.set(copy.getPrintSides());
	}

	public CoercivityType getCoercivityType() {
		return coercivityType.get();
	}

	public ControlPassbookAction getControlPassbook() {
		return controlPassbook.get();
	}

	public Set<PrintSides> getPrintSides() {
		return printSides.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.appendSuper(super.hashCode())
				.append(getCoercivityType())
				.append(getControlPassbook())
				.append(getPrintSides())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PtrCapabilities320) {
			PtrCapabilities320 ptrCapabilities320 = (PtrCapabilities320) obj;
			return new EqualsBuilder()
					.appendSuper(super.equals(obj))
					.append(getCoercivityType(), ptrCapabilities320.getCoercivityType())
					.append(getControlPassbook(), ptrCapabilities320.getControlPassbook())
					.append(getPrintSides(), ptrCapabilities320.getPrintSides())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.appendSuper(super.toString())
				.append("coercivityType", getCoercivityType())
				.append("controlPassbook", getControlPassbook())
				.append("printSides", getPrintSides())
				.toString();
	}
}
