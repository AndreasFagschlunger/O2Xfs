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

/**
 * @author Andreas Fagschlunger
 */
public enum XfsDeviceState implements XfsConstant {

	/**
	 * The device is present, powered on and online (i.e., operational, not busy
	 * processing a request and not in an error state).
	 */
	ONLINE(0L),

	/**
	 * The device is present and powered on, but offline (not operational�e.g.,
	 * an operator has switched it offline).
	 */
	OFFLINE(1L),

	/**
	 * The device is present but powered off.
	 */
	POWEROFF(2L),

	/**
	 * There is no device connected.
	 */
	NODEVICE(3L),

	/**
	 * The device is present but inoperable due to a hardware fault that
	 * prevents it from being used.
	 */
	HWERROR(4L),

	/**
	 * The device is present but a person is preventing proper device operation.
	 * The application should suspend the device operation or remove the device
	 * from service until the service provider generates a device state change
	 * event indicating the condition of the device has changed e.g.the error is
	 * removed ({@link #ONLINE}) or a permanent error condition has occurred ( {@link #HWERROR}).
	 */
	USERERROR(5L),

	/**
	 * The device is present and is busy processing an Execute request.
	 */
	BUSY(6L),

	/**
	 * Some devices are capable of identifying a malicious physical attack which
	 * attempts to defraud valuable information or media. In this circumstance,
	 * this status code is returned to indicate the device is inoperable because
	 * a person attempted a fraudulent act on the device.
	 */
	FRAUDATTEMPT(7L),

	/**
	 * The device has detected a potential fraud attempt and is capable of
	 * remaining in service. In this case the application should make the
	 * decision as to whether to take the device offline.
	 * 
	 * @since 3.20
	 */
	POTENTIALFRAUD(8L);

	private final long value;

	private XfsDeviceState(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}

}