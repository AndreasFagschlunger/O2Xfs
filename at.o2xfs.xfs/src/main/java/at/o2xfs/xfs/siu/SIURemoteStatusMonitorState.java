/*
 * Copyright (c) 2017, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.xfs.siu;

import at.o2xfs.xfs.XfsConstant;

public enum SIURemoteStatusMonitorState implements XfsConstant {

	/**
	 * The status is not available.
	 */
	NOT_AVAILABLE(0x0000L),

	/**
	 * The green LED is on.
	 */
	GREEN_LED_ON(0x0001L),

	/**
	 * The green LED is off.
	 */
	GREEN_LED_OFF(0x0002L),

	/**
	 * The amber LED is on.
	 */
	AMBER_LED_ON(0x0004L),

	/**
	 * The amber LED is off.
	 */
	AMBER_LED_OFF(0x0008L),

	/**
	 * The red LED is on.
	 */
	RED_LED_ON(0x0010L),

	/**
	 * The red LED is off.
	 */
	RED_LED_OFF(0x0020L);

	private final long value;

	private SIURemoteStatusMonitorState(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}

}