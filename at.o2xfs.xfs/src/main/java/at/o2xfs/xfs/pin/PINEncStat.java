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

public enum PINEncStat implements XfsConstant {

	/**
	 * The encryption module is initialized and ready (at least one key is
	 * imported into the encryption module).
	 */
	WFS_PIN_ENCREADY(0L),

	/**
	 * The encryption module is not ready.
	 */
	WFS_PIN_ENCNOTREADY(1L),

	/**
	 * The encryption module is not initialized (no master key loaded).
	 */
	WFS_PIN_ENCNOTINITIALIZED(2L),

	/**
	 * The encryption module is busy (implies that the device is busy).
	 */
	WFS_PIN_ENCBUSY(3L),

	/**
	 * The encryption module state is undefined.
	 */
	WFS_PIN_ENCUNDEFINED(4L),

	/**
	 * The encryption module is initialized and master key (where required) and
	 * any other initial keys are loaded; ready to import other keys.
	 */
	WFS_PIN_ENCINITIALIZED(5L),

	/**
	 * TODO
	 * 
	 * @since 3.10
	 */
	WFS_PIN_ENCPINTAMPERED(6L);

	private final long value;

	private PINEncStat(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}