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

package at.o2xfs.xfs.v3_20.cim;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.cim.AntiFraudModule;
import at.o2xfs.xfs.cim.MixedMode;
import at.o2xfs.xfs.v3_10.cim.Status310;
import at.o2xfs.xfs.win32.XfsWord;

public class Status320 extends Status310 {

	protected final XfsWord<MixedMode> mixedMode = new XfsWord<>(MixedMode.class);
	protected final XfsWord<AntiFraudModule> antiFraudModule = new XfsWord<>(AntiFraudModule.class);

	protected Status320() {
		add(mixedMode);
		add(antiFraudModule);
	}

	public Status320(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Status320(Status320 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Status320 copy) {
		super.set(copy);
		mixedMode.set(copy.getMixedMode());
		antiFraudModule.set(copy.getAntiFraudModule());
	}

	public MixedMode getMixedMode() {
		return mixedMode.get();
	}

	public AntiFraudModule getAntiFraudModule() {
		return antiFraudModule.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(getMixedMode()).append(getAntiFraudModule()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Status320) {
			Status320 status320 = (Status320) obj;
			return new EqualsBuilder().appendSuper(super.equals(obj)).append(getMixedMode(), status320.getMixedMode())
					.append(getAntiFraudModule(), status320.getAntiFraudModule()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("mixedMode", getMixedMode()).append("antiFraudModule", getAntiFraudModule()).toString();
	}
}
