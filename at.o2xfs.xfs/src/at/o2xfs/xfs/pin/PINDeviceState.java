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

package at.o2xfs.xfs.pin;

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

public enum PINDeviceState implements XfsConstant {

	/**
	 * @see XfsDeviceState#WFS_STAT_DEVONLINE
	 */
	WFS_PIN_DEVONLINE(WFS_STAT_DEVONLINE),

	/**
	 * @see XfsDeviceState#WFS_STAT_DEVOFFLINE
	 */
	WFS_PIN_DEVOFFLINE(WFS_STAT_DEVOFFLINE),

	/**
	 * @see XfsDeviceState#WFS_STAT_DEVPOWEROFF
	 */
	WFS_PIN_DEVPOWEROFF(WFS_STAT_DEVPOWEROFF),

	/**
	 * @see XfsDeviceState#WFS_STAT_DEVBUSY
	 */
	WFS_PIN_DEVBUSY(WFS_STAT_DEVBUSY),

	/**
	 * @see XfsDeviceState#WFS_STAT_DEVNODEVICE
	 */
	WFS_PIN_DEVNODEVICE(WFS_STAT_DEVNODEVICE),

	/**
	 * @see XfsDeviceState#WFS_STAT_DEVHWERROR
	 */
	WFS_PIN_DEVHWERROR(WFS_STAT_DEVHWERROR),

	/**
	 * @see XfsDeviceState#WFS_STAT_DEVUSERERROR
	 */
	WFS_PIN_DEVUSERERROR(WFS_STAT_DEVUSERERROR),

	/**
	 * The device is present but has detected a fraud attempt.
	 * 
	 * @see XfsDeviceState#WFS_STAT_DEVFRAUDATTEMPT
	 * @since 3.10
	 */
	WFS_PIN_DEVFRAUDATTEMPT(WFS_STAT_DEVFRAUDATTEMPT);

	private final long value;

	private PINDeviceState(final XfsDeviceState xfsDeviceState) {
		this.value = xfsDeviceState.getValue();
	}

	@Override
	public long getValue() {
		return value;
	}
}
