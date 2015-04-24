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
 *
 */
public enum IDCType implements XfsConstant {

	/**
	 * The ID card unit is a motor driven card unit.
	 */
	MOTOR(1L),

	/**
	 * The ID card unit is a swipe (pull-through) card unit .
	 */
	SWIPE(2L),

	/**
	 * The ID card unit is a dip card unit.
	 */
	DIP(3L),

	/**
	 * The ID card unit is a contactless card unit, i.e. no insertion of the
	 * card is required.
	 */
	CONTACTLESS(4L),

	/**
	 * The ID card unit is a latched dip card unit.
	 *
	 * @since 3.10
	 */
	LATCHEDDIP(5L),

	/**
	 * The ID card unit is dedicated to a permanently housed chip card (no user
	 * interaction is available with this type of card).
	 *
	 * @since 3.10
	 */
	PERMANENT(6L);

	private final long value;

	private IDCType(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}