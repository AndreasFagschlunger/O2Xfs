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

package at.o2xfs.xfs.idc.v3_30;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;

public class AppData3_30 extends Struct {

	protected final Pointer aid = new Pointer();
	protected final Pointer kernelIdentifier = new Pointer();

	protected AppData3_30() {
		add(aid);
		add(kernelIdentifier);
	}

	public AppData3_30(Pointer p) {
		this();
		assignBuffer(p);
	}

	public AppData3_30(AppData3_30 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(AppData3_30 copy) {
		aid.pointTo(new HexData3_30(copy.getAID()));
		kernelIdentifier.pointTo(new HexData3_30(copy.getKernelIdentifier()));
	}

	public byte[] getAID() {
		return new HexData3_30(aid).getData();
	}

	public byte[] getKernelIdentifier() {
		return new HexData3_30(kernelIdentifier).getData();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getAID()).append(getKernelIdentifier()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AppData3_30) {
			AppData3_30 appData3_30 = (AppData3_30) obj;
			return new EqualsBuilder()
					.append(getAID(), appData3_30.getAID())
					.append(getKernelIdentifier(), appData3_30.getKernelIdentifier())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("aid", getAID())
				.append("kernelIdentifier", getKernelIdentifier())
				.toString();
	}
}