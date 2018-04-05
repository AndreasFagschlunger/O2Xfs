/*
 * Copyright (c) 2018, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.xfs.ptr;

import at.o2xfs.xfs.XfsConstant;

public enum MediaControl implements XfsConstant {

	/*
	 * @since v3.00
	 */
	EJECT(0x0001),

	/*
	 * @since v3.00
	 */
	PERFORATE(0x0002),

	/*
	 * @since v3.00
	 */
	CUT(0x0004),

	/*
	 * @since v3.00
	 */
	SKIP(0x0008),

	/*
	 * @since v3.00
	 */
	FLUSH(0x0010),

	/*
	 * @since v3.00
	 */
	RETRACT(0x0020),

	/*
	 * @since v3.00
	 */
	STACK(0x0040),

	/*
	 * @since v3.00
	 */
	PARTIALCUT(0x0080),

	/*
	 * @since v3.00
	 */
	ALARM(0x0100),

	/*
	 * @since v3.00
	 */
	ATPFORWARD(0x0200),

	/*
	 * @since v3.00
	 */
	ATPBACKWARD(0x0400),

	/*
	 * @since v3.00
	 */
	TURNMEDIA(0x0800),

	/*
	 * @since v3.00
	 */
	STAMP(0x1000),

	/*
	 * @since v3.00
	 */
	PARK(0x2000),

	/*
	 * @since v3.10
	 */
	EXPEL(0x4000),

	/*
	 * @since v3.10
	 */
	EJECTTOTRANSPORT(0x8000),

	/*
	 * @since v3.30
	 */
	ROTATE180(0x00010000),

	/*
	 * @since v3.30
	 */
	CLEARBUFFER(0x00020000);

	private final long value;

	private MediaControl(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
