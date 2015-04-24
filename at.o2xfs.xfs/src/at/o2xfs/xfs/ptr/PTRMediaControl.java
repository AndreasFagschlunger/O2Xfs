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

public enum PTRMediaControl implements XfsConstant {

	/**
	 * Device can eject media.
	 */
	EJECT(0x0001L),

	/**
	 * Device can perforate media.
	 */
	PERFORATE(0x0002L),

	/**
	 * Device can cut media.
	 */
	CUT(0x0004L),

	/**
	 * Device can skip to mark.
	 */
	SKIP(0x0008L),

	/**
	 * Device can be sent data that is buffered internally, and flushed to the
	 * printer on request.
	 */
	FLUSH(0x0010L),

	/**
	 * Device can retract media under application control.
	 */
	RETRACT(0x0020L),

	/**
	 * Device can stack media items before ejecting as a bundle.
	 */
	STACK(0x0040L),

	/**
	 * Device can partially cut the media.
	 */
	PARTIALCUT(0x0080L),

	/**
	 * Device can ring a bell, beep or otherwise sound an audible alarm.
	 */
	ALARM(0x0100L),

	/**
	 * Capability to turn one page forward.
	 */
	ATPFORWARD(0x0200L),

	/**
	 * Capability to turn one page backward.
	 */
	ATPBACKWARD(0x0400L),

	/**
	 * Device can turn inserted media.
	 */
	TURNMEDIA(0x0800L),

	/**
	 * Device can stamp on media.
	 */
	STAMP(0x1000L),

	/**
	 * Device can park a document into the parking station.
	 */
	PARK(0x2000L),

	/**
	 * Device can expel media out of the exit slot.
	 */
	EXPEL(0x4000L),

	/**
	 * Device can move media to a position on the transport just behind the exit
	 * slot.
	 */
	EJECTTOTRANSPORT(0x8000L);

	private final long value;

	private PTRMediaControl(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}