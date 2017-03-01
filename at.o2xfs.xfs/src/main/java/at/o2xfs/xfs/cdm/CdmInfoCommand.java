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

package at.o2xfs.xfs.cdm;

import at.o2xfs.xfs.XfsConstant;

public enum CdmInfoCommand implements XfsConstant {

	/*
	 * @since v3.00
	 */
	STATUS(301L),

	/*
	 * @since v3.00
	 */
	CAPABILITIES(302L),

	/*
	 * @since v3.00
	 */
	CASH_UNIT_INFO(303L),

	/*
	 * @since v3.00
	 */
	TELLER_INFO(304L),

	/*
	 * @since v3.00
	 */
	CURRENCY_EXP(306L),

	/*
	 * @since v3.00
	 */
	MIX_TYPES(307L),

	/*
	 * @since v3.00
	 */
	MIX_TABLE(308L),

	/*
	 * @since v3.00
	 */
	PRESENT_STATUS(309L),

	/*
	 * @since v3.30
	 */
	GET_ITEM_INFO(310L),

	/*
	 * @since v3.30
	 */
	GET_BLACKLIST(311L),

	/*
	 * @since v3.30
	 */
	GET_ALL_ITEMS_INFO(312L);

	private final long value;

	private CdmInfoCommand(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}