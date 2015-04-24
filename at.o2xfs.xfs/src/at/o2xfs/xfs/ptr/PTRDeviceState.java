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

package at.o2xfs.xfs.ptr;

import at.o2xfs.xfs.XfsConstant;
import at.o2xfs.xfs.XfsDeviceState;

public enum PTRDeviceState implements XfsConstant {

	/**
	 * The device is online (i.e. powered on and operable).
	 */
	ONLINE(XfsDeviceState.ONLINE),

	/**
	 * The device is offline (e.g. the operator has taken the device offline by
	 * turning a switch or pulling out the device).
	 */
	OFFLINE(XfsDeviceState.OFFLINE),

	/**
	 * The device is powered off or physically not connected.
	 */
	POWEROFF(XfsDeviceState.POWEROFF),

	/**
	 * There is no device intended to be there; e.g. this type of self service
	 * machine does not contain such a device or it is internally not
	 * configured.
	 */
	NODEVICE(XfsDeviceState.NODEVICE),

	/**
	 * The device is inoperable due to a hardware error.
	 */
	HWERROR(XfsDeviceState.HWERROR),

	/**
	 * The device is present but a person is preventing proper device operation.
	 */
	USERERROR(XfsDeviceState.USERERROR),

	/**
	 * The device is busy and unable to process an execute command at this time.
	 */
	BUSY(XfsDeviceState.BUSY),

	/**
	 * The device is present but is inoperable because it has detected a fraud
	 * attempt.
	 */
	FRAUDATTEMPT(XfsDeviceState.FRAUDATTEMPT),

	/**
	 * The device has detected a potential fraud attempt and is capable of
	 * remaining in service. In this case the application should make the
	 * decision as to whether to take the device offline.
	 */
	POTENTIALFRAUD(XfsDeviceState.POTENTIALFRAUD);

	private final long value;

	private PTRDeviceState(final XfsDeviceState deviceState) {
		this.value = deviceState.getValue();
	}

	@Override
	public long getValue() {
		return value;
	}

}