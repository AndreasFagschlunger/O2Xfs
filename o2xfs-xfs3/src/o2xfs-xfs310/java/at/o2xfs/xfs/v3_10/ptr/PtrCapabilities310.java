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

package at.o2xfs.xfs.v3_10.ptr;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.ptr.GuidLight;
import at.o2xfs.xfs.ptr.PtrConstant;
import at.o2xfs.xfs.v3_00.ptr.PtrCapabilities3;
import at.o2xfs.xfs.win32.XfsBitmaskArray;

public class PtrCapabilities310 extends PtrCapabilities3 {

	protected final XfsBitmaskArray<GuidLight> guidLights = new XfsBitmaskArray<>(PtrConstant.GUIDLIGHTS_SIZE,
			GuidLight.class);
	protected final LPSTR windowsPrinter = new LPSTR();
	protected final BOOL mediaPresented = new BOOL();
	protected final USHORT autoRetractPeriod = new USHORT();
	protected final BOOL retractToTransport = new BOOL();
	protected final BOOL powerSaveControl = new BOOL();

	protected PtrCapabilities310() {
		add(guidLights);
		add(windowsPrinter);
		add(mediaPresented);
		add(autoRetractPeriod);
		add(retractToTransport);
		add(powerSaveControl);
	}

	public PtrCapabilities310(Pointer p) {
		this();
		assignBuffer(p);
	}

	public PtrCapabilities310(PtrCapabilities310 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(PtrCapabilities310 copy) {
		super.set(copy);
		guidLights.set(copy.getGuidLights());
		Optional<String> windowsPrinter = copy.getWindowsPrinter();
		if (windowsPrinter.isPresent()) {
			this.windowsPrinter.set(windowsPrinter.get());
		}
		mediaPresented.set(copy.isMediaPresented());
		autoRetractPeriod.set(copy.getAutoRetractPeriod());
		retractToTransport.set(copy.isRetractToTransport());
		powerSaveControl.set(copy.isPowerSaveControl());
	}

	public List<Set<GuidLight>> getGuidLights() {
		return guidLights.get();
	}

	public Optional<String> getWindowsPrinter() {
		Optional<String> result = Optional.empty();
		if (!Pointer.NULL.equals(windowsPrinter)) {
			result = Optional.of(windowsPrinter.get());
		}
		return result;
	}

	public boolean isMediaPresented() {
		return mediaPresented.get();
	}

	public int getAutoRetractPeriod() {
		return autoRetractPeriod.get();
	}

	public boolean isRetractToTransport() {
		return retractToTransport.get();
	}

	public boolean isPowerSaveControl() {
		return powerSaveControl.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.appendSuper(super.hashCode())
				.append(getGuidLights())
				.append(getWindowsPrinter())
				.append(isMediaPresented())
				.append(getAutoRetractPeriod())
				.append(isRetractToTransport())
				.append(isPowerSaveControl())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PtrCapabilities310) {
			PtrCapabilities310 ptrCapabilities310 = (PtrCapabilities310) obj;
			return new EqualsBuilder()
					.appendSuper(super.equals(obj))
					.append(getGuidLights(), ptrCapabilities310.getGuidLights())
					.append(getWindowsPrinter(), ptrCapabilities310.getWindowsPrinter())
					.append(isMediaPresented(), ptrCapabilities310.isMediaPresented())
					.append(getAutoRetractPeriod(), ptrCapabilities310.getAutoRetractPeriod())
					.append(isRetractToTransport(), ptrCapabilities310.isRetractToTransport())
					.append(isPowerSaveControl(), ptrCapabilities310.isPowerSaveControl())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.appendSuper(super.toString())
				.append("guidLights", getGuidLights())
				.append("windowsPrinter", getWindowsPrinter())
				.append("mediaPresented", isMediaPresented())
				.append("autoRetractPeriod", getAutoRetractPeriod())
				.append("retractToTransport", isRetractToTransport())
				.append("powerSaveControl", isPowerSaveControl())
				.toString();
	}
}
