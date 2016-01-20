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

public enum PINMessage implements XfsConstant {

	/**
	 * A key has been pressed at the PIN pad.
	 */
	WFS_EXEE_PIN_KEY(401L),

	/**
	 * The encryption module is now initialized.
	 */
	WFS_SRVE_PIN_INITIALIZED(402L),

	/**
	 * An error occured accessing an encryption key.
	 */
	WFS_SRVE_PIN_ILLEGAL_KEY_ACCESS(403L),

	WFS_SRVE_PIN_OPT_REQUIRED(404),
	WFS_SRVE_PIN_HSM_TDATA_CHANGED(405),
	WFS_SRVE_PIN_CERTIFICATE_CHANGE(406),
	WFS_SRVE_PIN_HSM_CHANGED(407),

	/**
	 * @since 3.10
	 */
	WFS_EXEE_PIN_ENTERDATA(408),
	WFS_SRVE_PIN_DEVICEPOSITION(409),
	WFS_SRVE_PIN_POWER_SAVE_CHANGE(410);

	private final long value;

	private PINMessage(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}

}