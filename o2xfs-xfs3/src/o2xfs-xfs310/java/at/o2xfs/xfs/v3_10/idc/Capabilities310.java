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

package at.o2xfs.xfs.v3_10.idc;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.idc.DIPMode;
import at.o2xfs.xfs.idc.GuidLight;
import at.o2xfs.xfs.idc.MemoryChipProtocol;
import at.o2xfs.xfs.v3_00.idc.Capabilities3;
import at.o2xfs.xfs.win32.XfsBitmaskArray;
import at.o2xfs.xfs.win32.XfsWord;

public class Capabilities310 extends Capabilities3 {

	private static final int GUIDLIGHTS_SIZE = 32;

	protected final XfsWord<DIPMode> dipMode = new XfsWord<>(DIPMode.class);
	protected final Pointer memoryChipProtocols = new Pointer();
	protected final XfsBitmaskArray<GuidLight> guidLights = new XfsBitmaskArray<>(GUIDLIGHTS_SIZE, GuidLight.class);
	protected final WORD ejectPosition = new WORD();
	protected final BOOL powerSaveControl = new BOOL();

	protected Capabilities310() {
		add(dipMode);
		add(memoryChipProtocols);
		add(guidLights);
		add(ejectPosition);
		add(powerSaveControl);
	}

	public Capabilities310(Pointer p) {
		this();
		assignBuffer(p);
	}

	public Capabilities310(Capabilities310 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(Capabilities310 copy) {
		super.set(copy);
		dipMode.set(copy.getDIPMode());
		Optional<MemoryChipProtocol[]> memoryChipProtocols = copy.getMemoryChipProtocols();
		if (memoryChipProtocols.isPresent()) {
			this.memoryChipProtocols.pointTo(new MemoryChipProtocols(memoryChipProtocols.get()));
		}
		guidLights.set(copy.getGuidLights());
		ejectPosition.set(copy.getEjectPosition());
		powerSaveControl.set(copy.isPowerSaveControl());
	}

	public DIPMode getDIPMode() {
		return dipMode.get();
	}

	public Optional<MemoryChipProtocol[]> getMemoryChipProtocols() {
		Optional<MemoryChipProtocol[]> result = Optional.empty();
		if (!Pointer.NULL.equals(memoryChipProtocols)) {
			result = Optional.of(new MemoryChipProtocols(memoryChipProtocols).get());
		}
		return result;
	}

	public List<Set<GuidLight>> getGuidLights() {
		return guidLights.get();
	}

	public int getEjectPosition() {
		return ejectPosition.get();
	}

	public boolean isPowerSaveControl() {
		return powerSaveControl.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.appendSuper(super.hashCode())
				.append(getDIPMode())
				.append(getMemoryChipProtocols())
				.append(getGuidLights())
				.append(getEjectPosition())
				.append(isPowerSaveControl())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Capabilities310) {
			Capabilities310 capabilities310 = (Capabilities310) obj;
			return new EqualsBuilder()
					.appendSuper(super.equals(obj))
					.append(getDIPMode(), capabilities310.getDIPMode())
					.append(getMemoryChipProtocols().orElse(null),
							capabilities310.getMemoryChipProtocols().orElse(null))
					.append(getGuidLights(), capabilities310.getGuidLights())
					.append(getEjectPosition(), capabilities310.getEjectPosition())
					.append(isPowerSaveControl(), capabilities310.isPowerSaveControl())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.appendSuper(super.toString())
				.append("dipMode", getDIPMode())
				.append("memoryChipProtocols", getMemoryChipProtocols())
				.append("guidLights", getGuidLights())
				.append("ejectPosition", getEjectPosition())
				.append("powerSaveControl", isPowerSaveControl())
				.toString();
	}
}
