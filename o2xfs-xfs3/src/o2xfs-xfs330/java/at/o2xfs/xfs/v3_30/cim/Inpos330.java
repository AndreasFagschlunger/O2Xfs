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

package at.o2xfs.xfs.v3_30.cim;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.cim.JammedShutterPosition;
import at.o2xfs.xfs.v3_00.cim.Inpos3;
import at.o2xfs.xfs.win32.XfsWord;

public class Inpos330 extends Inpos3 {

	protected final XfsWord<JammedShutterPosition> jammedShutterPosition = new XfsWord<>(JammedShutterPosition.class);

	protected Inpos330() {
		add(jammedShutterPosition);
	}

	public Inpos330(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Inpos330(Inpos330 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Inpos330 copy) {
		super.set(copy);
		jammedShutterPosition.set(copy.getJammedShutterPosition());
	}

	public JammedShutterPosition getJammedShutterPosition() {
		return jammedShutterPosition.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(getJammedShutterPosition()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Inpos330) {
			Inpos330 inpos330 = (Inpos330) obj;
			return new EqualsBuilder().appendSuper(super.equals(obj)).append(getJammedShutterPosition(), inpos330.getJammedShutterPosition()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("jammedShutterPosition", getJammedShutterPosition()).toString();
	}
}
