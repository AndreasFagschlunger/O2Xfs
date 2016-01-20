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

public enum IDCEjectPosition implements XfsConstant {

	/**
	 * The card will be transferred to the exit slot from where the user can
	 * remove it. In the case of a latched dip the card will be unlatched,
	 * enabling removal.
	 *
	 * @since 3.10
	 */
	EXITPOSITION(0x0001L),

	/**
	 * The card will be transferred to the transport just behind the exit slot.
	 * If a card is already at this position then WFS_SUCCESS will be returned.
	 * Another WFS_CMD_IDC_EJECT_CARD command is required with the
	 * wEjectPosition set to EXITPOSITION in order to present the card
	 * to the user for removal.
	 *
	 * @since 3.10
	 */
	TRANSPORTPOSITION(0x0002L);

	private final long value;

	private IDCEjectPosition(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}