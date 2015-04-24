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

public enum PTRMedia implements XfsConstant {

	/**
	 * Media is in the print position, on the stacker or on the transport (i.e.
	 * a passbook in the parking station is not considered to be present). On
	 * devices with continuous paper supplies, this value is set when paper is
	 * under the print head. On devices with individual sheet supplies, this
	 * value is set when paper is successfully inserted/loaded.
	 */
	PRESENT(0L),

	/**
	 * Media is not in the print position or on the stacker.
	 */
	NOTPRESENT(1L),

	/**
	 * Media is jammed in the device.
	 */
	JAMMED(2L),

	/**
	 * The capability to report the state of the print media is not supported by
	 * the device.
	 */
	NOTSUPP(3L),

	/**
	 * The state of the print media cannot be determined with the device in its
	 * current state.
	 */
	UNKNOWN(4L),

	/**
	 * Media is at the entry/exit slot of the device.
	 */
	ENTERING(5L),

	/**
	 * Media was retracted during the reset operation.
	 */
	RETRACTED(6L);

	private final long value;

	private PTRMedia(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}

}