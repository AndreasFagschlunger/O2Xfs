/*
 * Copyright (c) 2012, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.xfs.ttu;

import at.o2xfs.xfs.XfsConstant;
import at.o2xfs.xfs.XfsDeviceState;

public enum TTUDevice implements XfsConstant {

	/**
	 * The device is on-line. The device is present and operational (i.e. not
	 * busy processing a request and not having a hardware error).
	 */
	WFS_TTU_DEVONLINE(XfsDeviceState.WFS_STAT_DEVONLINE),

	/**
	 * The device is off-line. The device is present and powered on but it is
	 * not operational (e.g. a switch may have been used to change it to an
	 * off-line state).
	 */
	WFS_TTU_DEVOFFLINE(XfsDeviceState.WFS_STAT_DEVOFFLINE),

	/**
	 * The device is powered off. The device is present, but is currently
	 * powered off.
	 */
	WFS_TTU_DEVPOWEROFF(XfsDeviceState.WFS_STAT_DEVPOWEROFF),

	/**
	 * The device is busy processing a request. The device is present and an
	 * EXECUTE request is currently being processed.
	 */
	WFS_TTU_DEVBUSY(XfsDeviceState.WFS_STAT_DEVBUSY),

	/**
	 * There is no device connected.
	 */
	WFS_TTU_DEVNODEVICE(XfsDeviceState.WFS_STAT_DEVNODEVICE),

	/**
	 * The device is inoperable due to a hardware error. The device is present
	 * but a hardware fault prevents it from being used.
	 */
	WFS_TTU_DEVHWERROR(XfsDeviceState.WFS_STAT_DEVHWERROR),

	/**
	 * The device is present but a person is preventing proper operation. The
	 * application should suspend the device operation or remove the device from
	 * service until the service provider generates a device state change event
	 * indicating the condition of the device has changed i.e. the error is
	 * removed ({@link #WFS_TTU_DEVONLINE}) or a permanent error condition has
	 * occurred ({@link #WFS_TTU_DEVHWERROR}).
	 */
	WFS_TTU_DEVUSERERROR(XfsDeviceState.WFS_STAT_DEVUSERERROR);

	private final long value;

	private TTUDevice(final XfsDeviceState deviceState) {
		this.value = deviceState.getValue();
	}

	@Override
	public long getValue() {
		return value;
	}
}
