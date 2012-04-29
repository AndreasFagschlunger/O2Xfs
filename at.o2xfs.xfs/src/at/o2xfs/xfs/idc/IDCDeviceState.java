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

package at.o2xfs.xfs.idc;

import static at.o2xfs.xfs.XfsDeviceState.WFS_STAT_DEVBUSY;
import static at.o2xfs.xfs.XfsDeviceState.WFS_STAT_DEVFRAUDATTEMPT;
import static at.o2xfs.xfs.XfsDeviceState.WFS_STAT_DEVHWERROR;
import static at.o2xfs.xfs.XfsDeviceState.WFS_STAT_DEVNODEVICE;
import static at.o2xfs.xfs.XfsDeviceState.WFS_STAT_DEVOFFLINE;
import static at.o2xfs.xfs.XfsDeviceState.WFS_STAT_DEVONLINE;
import static at.o2xfs.xfs.XfsDeviceState.WFS_STAT_DEVPOWEROFF;
import static at.o2xfs.xfs.XfsDeviceState.WFS_STAT_DEVUSERERROR;
import at.o2xfs.xfs.XfsConstant;
import at.o2xfs.xfs.XfsDeviceState;

public enum IDCDeviceState implements XfsConstant {

	/**
	 * The device is present, powered on and online (i.e., operational, not busy
	 * processing a request and not in an error state).
	 */
	WFS_IDC_DEVONLINE(WFS_STAT_DEVONLINE),

	/**
	 * The device is present and powered on, but offline (not operational�e.g.,
	 * an operator has switched it offline).
	 */
	WFS_IDC_DEVOFFLINE(WFS_STAT_DEVOFFLINE),

	/**
	 * The device is present but powered off.
	 */
	WFS_IDC_DEVPOWEROFF(WFS_STAT_DEVPOWEROFF),

	/**
	 * The device is present and is busy processing an Execute request.
	 */
	WFS_IDC_DEVBUSY(WFS_STAT_DEVBUSY),

	/**
	 * There is no device connected.
	 */
	WFS_IDC_DEVNODEVICE(WFS_STAT_DEVNODEVICE),

	/**
	 * The device is present but inoperable due to a hardware fault that
	 * prevents it from being used.
	 */
	WFS_IDC_DEVHWERROR(WFS_STAT_DEVHWERROR),

	/**
	 * The device is present but a person is preventing proper device operation.
	 * The application should suspend the device operation or remove the device
	 * from service until the service provider generates a device state change
	 * event indicating the condition of the device has changed e.g.the error is
	 * removed (WFS_IDC_DEVONLINE) or a permanent error condition has occurred
	 * (WFS_IDC_DEVHWERROR).
	 */
	WFS_IDC_DEVUSERERROR(WFS_STAT_DEVUSERERROR),

	/**
	 * The device is present but has detected a fraud attempt.
	 * 
	 * @since 3.10
	 */
	WFS_IDC_DEVFRAUDATTEMPT(WFS_STAT_DEVFRAUDATTEMPT);

	private final long value;

	private IDCDeviceState(final XfsDeviceState deviceState) {
		value = deviceState.getValue();
	}

	@Override
	public long getValue() {
		return value;
	}
}
