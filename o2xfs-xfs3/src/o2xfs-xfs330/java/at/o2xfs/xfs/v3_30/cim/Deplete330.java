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
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.USHORT;

public class Deplete330 extends Struct {

	protected final Pointer depleteSources = new Pointer();
	protected final USHORT numberTarget = new USHORT();

	protected Deplete330() {
		add(depleteSources);
		add(numberTarget);
	}

	public Deplete330(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Deplete330(Deplete330 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Deplete330 copy) {
		depleteSources.pointTo(new DepleteSources(copy.getDepleteSources()));
		numberTarget.set(copy.getNumberTarget());
	}

	public DepleteSource330[] getDepleteSources() {
		return new DepleteSources(depleteSources).get();
	}

	public int getNumberTarget() {
		return numberTarget.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getDepleteSources()).append(getNumberTarget()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Deplete330) {
			Deplete330 deplete330 = (Deplete330) obj;
			return new EqualsBuilder().append(getDepleteSources(), deplete330.getDepleteSources()).append(getNumberTarget(), deplete330.getNumberTarget()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("depleteSources", getDepleteSources()).append("numberTarget", getNumberTarget()).toString();
	}
}
