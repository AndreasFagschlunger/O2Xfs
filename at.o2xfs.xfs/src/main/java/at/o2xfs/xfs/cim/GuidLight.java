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

package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum GuidLight implements XfsConstant {

	/*
	 * @since v3.10
	 */
	NOT_AVAILABLE(0x00000000),

	/*
	 * @since v3.10
	 */
	OFF(0x00000001),

	/*
	 * @since v3.10
	 */
	SLOW_FLASH(0x00000004),

	/*
	 * @since v3.10
	 */
	MEDIUM_FLASH(0x00000008),

	/*
	 * @since v3.10
	 */
	QUICK_FLASH(0x00000010),

	/*
	 * @since v3.10
	 */
	CONTINUOUS(0x00000080),

	/*
	 * @since v3.10
	 */
	RED(0x00000100),

	/*
	 * @since v3.10
	 */
	GREEN(0x00000200),

	/*
	 * @since v3.10
	 */
	YELLOW(0x00000400),

	/*
	 * @since v3.10
	 */
	BLUE(0x00000800),

	/*
	 * @since v3.10
	 */
	CYAN(0x00001000),

	/*
	 * @since v3.10
	 */
	MAGENTA(0x00002000),

	/*
	 * @since v3.10
	 */
	WHITE(0x00004000),

	/*
	 * @since v3.30
	 */
	ENTRY(0x00100000),

	/*
	 * @since v3.30
	 */
	EXIT(0x00200000);

	private final long value;

	private GuidLight(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}