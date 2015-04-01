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

package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

/**
 * @author Andreas Fagschlunger
 *
 */
public enum IDCMedia implements XfsConstant {

	/**
	 * Media is present in the device, not in the entering position and not
	 * jammed.
	 */
	PRESENT(1L),

	/**
	 * Media is not present in the device and not at the entering position.
	 */
	NOTPRESENT(2L),

	/**
	 * Media is jammed in the device, operator intervention is required.
	 */
	JAMMED(3L),

	/**
	 * Capability to report media position is not supported by the device (e.g.,
	 * a typical swipe readerL).
	 */
	NOTSUPP(4L),

	/**
	 * The media state cannot be determined with the device in its current state
	 * (e.g., the value of fwDevice is WFS_IDC_DEVNODEVICE, WFS_IDC_DEVPOWEROFF,
	 * WFS_IDC_DEVOFFLINE, or WFS_IDC_DEVHWERROR).
	 */
	UNKNOWN(5L),

	/**
	 * Media is at the entry/exit slot of a motorized device.
	 */
	ENTERING(6L);

	private final long value;

	private IDCMedia(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}