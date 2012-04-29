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

import at.o2xfs.xfs.XfsConstant;

public enum IDCTrack implements XfsConstant {

	/**
	 * The ID card unit can not access any track.
	 */
	WFS_IDC_NOTSUPP(0x0000L),

	/**
	 * The ID card unit can access track 1.
	 */
	WFS_IDC_TRACK1(0x0001L),

	/**
	 * The ID card unit can access track 2.
	 */
	WFS_IDC_TRACK2(0x0002L),

	/**
	 * The ID card unit can access track 3.
	 */
	WFS_IDC_TRACK3(0x0004L),

	/**
	 * 
	 */
	WFS_IDC_CHIP(0x0008L),

	/**
	 * 
	 */
	WFS_IDC_SECURITY(0x0010L),

	/**
	 * If the IDC Flux Sensor is programmable it will be disabled in order to
	 * allow chip data to be read on cards which have no magnetic stripes.
	 * 
	 * @since v3.00
	 */
	WFS_IDC_FLUXINACTIVE(0x0020L),

	/**
	 * The ID card unit can access the Swedish Watermark track.
	 * 
	 * @since v3.00
	 */
	WFS_IDC_TRACK_WM(0x8000L),

	/**
	 * The ID card unit can access the front track 1. In some countries this
	 * track is known as JIS II track.
	 * 
	 * @since v3.10
	 */
	WFS_IDC_FRONT_TRACK_1(0x0080L),

	/**
	 * The ID card unit can read the front image of a card.
	 * 
	 * @since v3.10
	 */
	WFS_IDC_FRONTIMAGE(0x0100L),

	/**
	 * The ID card unit can read the back image of a card.
	 * 
	 * @since v3.10
	 */
	WFS_IDC_BACKIMAGE(0x0200L);

	private final long value;

	private IDCTrack(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
