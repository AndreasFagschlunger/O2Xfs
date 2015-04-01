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

import at.o2xfs.xfs.XfsConstant;
import at.o2xfs.xfs.XfsDeviceState;

public enum PINDeviceState implements XfsConstant {

	/**
	 * @see XfsDeviceState#WFS_STAT_DEVONLINE
	 */
	ONLINE(XfsDeviceState.ONLINE),

	/**
	 * @see XfsDeviceState#XfsDeviceState
	 */
	OFFLINE(XfsDeviceState.OFFLINE),

	/**
	 * @see XfsDeviceState#XfsDeviceState
	 */
	POWEROFF(XfsDeviceState.POWEROFF),

	/**
	 * @see XfsDeviceState#XfsDeviceState
	 */
	BUSY(XfsDeviceState.BUSY),

	/**
	 * @see XfsDeviceState#XfsDeviceState
	 */
	NODEVICE(XfsDeviceState.NODEVICE),

	/**
	 * @see XfsDeviceState#XfsDeviceState
	 */
	HWERROR(XfsDeviceState.HWERROR),

	/**
	 * @see XfsDeviceState#XfsDeviceState
	 */
	USERERROR(XfsDeviceState.USERERROR),

	/**
	 * The device is present but has detected a fraud attempt.
	 * 
	 * @see XfsDeviceState#XfsDeviceState
	 * @since 3.10
	 */
	FRAUDATTEMPT(XfsDeviceState.FRAUDATTEMPT),

	/**
	 * {@link XfsDeviceState#POTENTIALFRAUD}
	 * 
	 * @since 3.20
	 */
	POTENTIALFRAUD(XfsDeviceState.POTENTIALFRAUD);

	private final long value;

	private PINDeviceState(final XfsDeviceState xfsDeviceState) {
		this.value = xfsDeviceState.getValue();
	}

	@Override
	public long getValue() {
		return value;
	}
}