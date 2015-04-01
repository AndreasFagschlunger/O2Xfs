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

public enum IDCChipState implements XfsConstant {

	/**
	 * The chip is present, powered on and online (i.e. operational, not busy
	 * processing a request and not in an error state).
	 *
	 * @since 3.00
	 */
	ONLINE(0L),

	/**
	 * The chip is present, but powered off (i.e. not contacted).
	 *
	 * @since 3.00
	 */
	POWEREDOFF(1L),

	/**
	 * The chip is present, powered on, and busy (unable to process an Execute
	 * command at this time).
	 *
	 * @since 3.00
	 */
	BUSY(2L),

	/**
	 * A card is currently present in the device, but has no chip.
	 *
	 * @since 3.00
	 */
	NODEVICE(3L),

	/**
	 * The chip is present, but inoperable due to a hardware error that prevents
	 * it from being used (e.g. MUTE, if there is an unresponsive card in the
	 * reader).
	 *
	 * @since 3.00
	 */
	HWERROR(4L),

	/**
	 * There is no card in the device
	 *
	 * @since 3.00
	 */
	NOCARD(5L),

	/**
	 * Capability to report the state of the chip is not supported by the ID
	 * card unit device.
	 *
	 * @since 3.00
	 */
	NOTSUPP(6L),

	/**
	 * The state of the chip cannot be determined with the device in its current
	 * state.
	 *
	 * @since 3.00
	 */
	UNKNOWN(7L);

	private final long value;

	private IDCChipState(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}