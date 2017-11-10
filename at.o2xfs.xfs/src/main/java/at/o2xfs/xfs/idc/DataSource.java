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

package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum DataSource implements XfsConstant {
	/*
	 * @since v3.00
	 */
	NOTSUPP(0x0000),

	/*
	 * @since v3.00
	 */
	TRACK1(0x0001),

	/*
	 * @since v3.00
	 */
	TRACK2(0x0002),

	/*
	 * @since v3.00
	 */
	TRACK3(0x0004),

	/*
	 * @since v3.00
	 */
	CHIP(0x0008),

	/*
	 * @since v3.00
	 */
	SECURITY(0x0010),

	/*
	 * @since v3.00
	 */
	FLUXINACTIVE(0x0020),

	/*
	 * @since v3.00
	 */
	TRACK_WM(0x8000),

	/*
	 * @since v3.10
	 */
	MEMORY_CHIP(0x0040),

	/*
	 * @since v3.10
	 */
	FRONTIMAGE(0x0100),

	/*
	 * @since v3.10
	 */
	BACKIMAGE(0x0200),
	/*
	 * @since v3.10
	 */
	FRONT_TRACK_1(0x0080),

	/*
	 * @since v3.20
	 */
	TRACK1_JIS1(0x0400),

	/*
	 * @since v3.20
	 */
	TRACK3_JIS1(0x0800),

	/*
	 * @since v3.20
	 */
	DDI(0x4000);

	private final long value;

	private DataSource(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}