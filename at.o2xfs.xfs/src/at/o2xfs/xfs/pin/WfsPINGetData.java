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

package at.o2xfs.xfs.pin;

import at.o2xfs.win32.BOOL;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.util.Bitmask;
import at.o2xfs.xfs.util.XfsConstants;

import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WfsPINGetData
		extends Struct {

	/**
	 * Specifies the maximum number of digits which can be returned to the
	 * application in the data buffer.
	 */
	private USHORT maxLen = new USHORT();

	/**
	 * If {@link #autoEnd} is set to true, the service provider terminates the
	 * command when the maximum number of digits are entered. Otherwise, the
	 * input is terminated by the user using one of the termination keys. When {@link #maxLen} is reached, the service
	 * provider will disable all numeric
	 * keys. {@link #autoEnd} is ignored when {@link #maxLen} is set to 0.
	 */
	private BOOL autoEnd = new BOOL();

	/**
	 * Specifies those FDKs which are active during the execution of the
	 * command.
	 */
	private ULONG activeFDKs = new ULONG();

	/**
	 * Specifies those (other) Function Keys which are active during the
	 * execution of the command.
	 */
	private ULONG activeKeys = new ULONG();

	/**
	 * Specifies those FDKs which must terminate the execution of the command.
	 */
	private ULONG terminateFDKs = new ULONG();

	/**
	 * Specifies those (other) Function Keys which must terminate the execution
	 * of the command.
	 */
	private ULONG terminateKeys = new ULONG();

	public WfsPINGetData() {
		add(maxLen);
		add(autoEnd);
		add(activeFDKs);
		add(activeKeys);
		add(terminateFDKs);
		add(terminateKeys);
	}

	public int getMaxLen() {
		return maxLen.intValue();
	}

	public void setMaxLen(final int maxLen) {
		this.maxLen.set(maxLen);
	}

	public boolean isAutoEnd() {
		return autoEnd.booleanValue();
	}

	public void setAutoEnd(final boolean autoEnd) {
		this.autoEnd.set(autoEnd);
	}

	public Set<PINFDK> getActiveFDKs() {
		return XfsConstants.of(activeFDKs, PINFDK.class);
	}

	public void setActiveFDKs(final Set<PINFDK> activeFDKs) {
		this.activeFDKs.set(Bitmask.of(activeFDKs));
	}

	public Set<PINFK> getActiveKeys() {
		return XfsConstants.of(activeKeys, PINFK.class);
	}

	public void setActiveKeys(Set<PINFK> activeKeys) {
		this.activeKeys.set(Bitmask.of(activeKeys));
	}

	public Set<PINFDK> getTerminateFDKs() {
		return XfsConstants.of(terminateFDKs, PINFDK.class);
	}

	public void setTerminateFDKs(Set<PINFDK> terminateFDKs) {
		this.terminateFDKs.set(Bitmask.of(terminateFDKs));
	}

	public Set<PINFK> getTerminateKeys() {
		return XfsConstants.of(terminateKeys, PINFK.class);
	}

	public void setTerminateKeys(Set<PINFK> terminateKeys) {
		this.terminateKeys.set(Bitmask.of(terminateKeys));
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("maxLen", getMaxLen())
										.append("autoEnd", isAutoEnd())
										.append("activeFDKs", getActiveFDKs())
										.append("activeKeys", getActiveKeys())
										.append("terminateFDKs", getTerminateFDKs())
										.append("terminateKeys", getTerminateKeys())
										.toString();
	}
}