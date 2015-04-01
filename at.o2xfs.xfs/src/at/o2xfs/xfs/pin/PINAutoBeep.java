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

package at.o2xfs.xfs.pin;

import at.o2xfs.xfs.XfsConstant;

public enum PINAutoBeep implements XfsConstant {

	/**
	 * Automatic beep tone on active key key-press is supported. If this flag is
	 * not set then automatic beeping for active keys is not supported.
	 * 
	 * @since 3.10
	 */
	WFS_PIN_BEEP_ACTIVE_AVAILABLE(0x0001L),

	/**
	 * Automatic beeping for active keys can be controlled (i.e. turned on and
	 * off) by the application. If this flag is not set then automatic beeping
	 * for active keys cannot be controlled by an application.
	 * 
	 * @since 3.10
	 */
	WFS_PIN_BEEP_ACTIVE_SELECTABLE(0x0002L),

	/**
	 * Automatic beep tone on in-active key keypress is supported. If this flag
	 * is not set then automatic beeping for in-active keys is not supported.
	 * 
	 * @since 3.10
	 */
	WFS_PIN_BEEP_INACTIVE_AVAILABLE(0x0004L),

	/**
	 * Automatic beeping for in-active keys can be controlled (i.e. turned on
	 * and off) by the application. If this flag is not set then automatic
	 * beeping for in-active keys cannot be controlled by an application.
	 * 
	 * @since 3.10
	 */
	WFS_PIN_BEEP_INACTIVE_SELECTABLE(0x0008L);

	private final long value;

	private PINAutoBeep(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}