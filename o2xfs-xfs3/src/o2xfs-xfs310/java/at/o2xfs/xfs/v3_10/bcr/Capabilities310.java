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

package at.o2xfs.xfs.v3_10.bcr;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.XfsExtra;
import at.o2xfs.xfs.bcr.GuidLight;
import at.o2xfs.xfs.bcr.Symbologies;
import at.o2xfs.xfs.bcr.Symbology;
import at.o2xfs.xfs.win32.XfsBitmaskArray;

public class Capabilities310 extends Struct {

	private static final int GUIDLIGHTS_SIZE = 32;

	protected final WORD serviceClass = new WORD();
	protected final BOOL compound = new BOOL();
	protected final BOOL canFilterSymbologies = new BOOL();
	protected final Pointer symbologies = new Pointer();
	protected final XfsBitmaskArray<GuidLight> guidLights = new XfsBitmaskArray<>(GUIDLIGHTS_SIZE, GuidLight.class);
	protected final XfsExtra extra = new XfsExtra();
	protected final BOOL powerSaveControl = new BOOL();

	protected Capabilities310() {
		add(serviceClass);
		add(compound);
		add(canFilterSymbologies);
		add(symbologies);
		add(guidLights);
		add(extra);
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
		serviceClass.set(copy.getServiceClass());
		compound.set(copy.isCompound());
		canFilterSymbologies.set(copy.isCanFilterSymbologies());
		symbologies.pointTo(new Symbologies(copy.getSymbologies()));
		guidLights.set(copy.getGuidLights());
		extra.set(copy.getExtra());
		powerSaveControl.set(copy.isPowerSaveControl());
	}

	public int getServiceClass() {
		return serviceClass.get();
	}

	public boolean isCompound() {
		return compound.get();
	}

	public boolean isCanFilterSymbologies() {
		return canFilterSymbologies.get();
	}

	public Symbology[] getSymbologies() {
		return new Symbologies(symbologies).get();
	}

	public List<Set<GuidLight>> getGuidLights()

	{
		return guidLights.get();
	}

	public Map<String, String> getExtra() {
		return extra.get();
	}

	public boolean isPowerSaveControl() {
		return powerSaveControl.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getServiceClass()).append(isCompound()).append(isCanFilterSymbologies()).append(getSymbologies()).append(getGuidLights())
				.append(getExtra()).append(isPowerSaveControl()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Capabilities310) {
			Capabilities310 capabilities310 = (Capabilities310) obj;
			return new EqualsBuilder().append(getServiceClass(), capabilities310.getServiceClass()).append(isCompound(), capabilities310.isCompound())
					.append(isCanFilterSymbologies(), capabilities310.isCanFilterSymbologies()).append(getSymbologies(), capabilities310.getSymbologies())
					.append(getGuidLights(), capabilities310.getGuidLights()).append(getExtra(), capabilities310.getExtra())
					.append(isPowerSaveControl(), capabilities310.isPowerSaveControl()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("serviceClass", getServiceClass()).append("compound", isCompound()).append("canFilterSymbologies", isCanFilterSymbologies())
				.append("symbologies", getSymbologies()).append("guidLights", getGuidLights()).append("extra", getExtra()).append("powerSaveControl", isPowerSaveControl())
				.toString();
	}
}
