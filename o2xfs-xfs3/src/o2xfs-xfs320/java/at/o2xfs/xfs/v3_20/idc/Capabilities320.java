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

package at.o2xfs.xfs.v3_20.idc;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.v3_10.idc.Capabilities310;

public class Capabilities320 extends Capabilities310 {

	protected final USHORT parkingStations = new USHORT();
	protected final BOOL antiFraudModule = new BOOL();

	protected Capabilities320() {
		add(parkingStations);
		add(antiFraudModule);
	}

	public Capabilities320(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Capabilities320(Capabilities320 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Capabilities320 copy) {
		super.set(copy);
		parkingStations.set(copy.getParkingStations());
		antiFraudModule.set(copy.isAntiFraudModule());
	}

	public int getParkingStations() {
		return parkingStations.get();
	}

	public boolean isAntiFraudModule() {
		return antiFraudModule.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.appendSuper(super.hashCode())
				.append(getParkingStations())
				.append(isAntiFraudModule())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Capabilities320) {
			Capabilities320 capabilities320 = (Capabilities320) obj;
			return new EqualsBuilder()
					.appendSuper(super.equals(obj))
					.append(getParkingStations(), capabilities320.getParkingStations())
					.append(isAntiFraudModule(), capabilities320.isAntiFraudModule())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.appendSuper(super.toString())
				.append("parkingStations", getParkingStations())
				.append("antiFraudModule", isAntiFraudModule())
				.toString();
	}
}
