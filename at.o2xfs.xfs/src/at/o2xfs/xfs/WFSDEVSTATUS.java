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

package at.o2xfs.xfs;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ZSTR;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Andreas Fagschlunger
 */
public class WFSDEVSTATUS
		extends Struct {

	/**
	 * Pointer to the physical service name of the service that changed its
	 * state.
	 */
	private final LPSTR physicalName = new LPSTR();

	/**
	 * Pointer to the name of the workstation in which the logical service name
	 * is defined.
	 */
	private final LPSTR workstationName = new LPSTR();

	/**
	 * Specifies the new state of the physical device managed by the service as
	 * one of the following: {@link XfsDeviceState}
	 */
	private final XfsWord<XfsDeviceState> state = new XfsWord<XfsDeviceState>(XfsDeviceState.class);

	public WFSDEVSTATUS() {
		add(physicalName);
		add(workstationName);
		add(state);
	}

	public WFSDEVSTATUS(final Pointer p) {
		this();
		assignBuffer(p.buffer(getSize()));
	}

	public String getPhysicalName() {
		return physicalName.toString();
	}

	public void setPhysicalName(final String physicalName) {
		this.physicalName.pointTo(new ZSTR(physicalName));
	}

	public String getWorkstationName() {
		return workstationName.toString();
	}

	public void setWorkstationName(final String workstationName) {
		this.workstationName.pointTo(new ZSTR(workstationName));
	}

	public XfsDeviceState getState() {
		return state.get();
	}

	public void setState(final XfsDeviceState aState) {
		state.set(aState);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("physicalName", getPhysicalName())
										.append("workstationName", getWorkstationName())
										.append("state", getState())
										.toString();
	}
}