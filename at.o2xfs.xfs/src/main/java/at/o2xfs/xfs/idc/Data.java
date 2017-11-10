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

public enum Data implements XfsConstant {

	/*
	 * @since v3.00
	 */
	READLEVEL1('1'),

	/*
	 * @since v3.00
	 */
	READLEVEL2('2'),

	/*
	 * @since v3.00
	 */
	READLEVEL3('3'),

	/*
	 * @since v3.00
	 */
	READLEVEL4('4'),

	/*
	 * @since v3.00
	 */
	READLEVEL5('5'),

	/*
	 * @since v3.00
	 */
	BADREADLEVEL('6'),

	/*
	 * @since v3.00
	 */
	NODATA('7'),

	/*
	 * @since v3.00
	 */
	DATAINVAL('8'),

	/*
	 * @since v3.00
	 */
	HWERROR('9'),

	/*
	 * @since v3.00
	 */
	NOINIT('A');

	private final long value;

	private Data(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}