/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.xfs.siu;

import at.o2xfs.win32.LPZZSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.XfsConstant;
import at.o2xfs.xfs.util.KeyValueMap;
import at.o2xfs.xfs.util.XfsConstants;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SIUPortEvent
		extends Struct {

	private final WORD portType = new WORD();
	private final WORD portIndex = new WORD();
	private final WORD portStatus = new WORD();
	private final LPZZSTR extra = new LPZZSTR();

	private SIUPortEvent() {
		add(portType);
		add(portIndex);
		add(portStatus);
		add(extra);
	}

	public SIUPortEvent(final SIUPortEvent event) {
		this();
		allocate();
		portType.set(event.portType);
		portIndex.set(event.portIndex);
		portStatus.set(event.portStatus);
		extra.pointTo(KeyValueMap.toZZString(event.getExtra()));
	}

	public SIUPortEvent(final Pointer p) {
		this();
		assignBuffer(p);
	}

	public SIUPortType getPortType() {
		return XfsConstants.valueOf(portType, SIUPortType.class);
	}

	public <E extends Enum<E> & XfsConstant> E getPortIndex(final Class<E> portIndexType) {
		return XfsConstants.valueOf(portIndex, portIndexType);
	}

	private <E extends Enum<E> & XfsConstant> E getPortIndex() {
		return XfsConstants.valueOf(portIndex, (Class<E>) getPortType().getPortIndexType());
	}

	public WORD getPortStatus() {
		return portStatus;
	}

	public Map<String, String> getExtra() {
		return KeyValueMap.from(extra);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("portType", getPortType())
										.append("portIndex", getPortIndex())
										.append("portStatus", getPortStatus())
										.append("extra", getExtra())
										.toString();
	}
}