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

public enum CimMessage implements XfsConstant {

	/*
	 * @since v3.00
	 */
	SRVE_SAFEDOOROPEN(1301L),

	/*
	 * @since v3.00
	 */
	SRVE_SAFEDOORCLOSED(1302L),

	/*
	 * @since v3.00
	 */
	USRE_CASHUNITTHRESHOLD(1303L),

	/*
	 * @since v3.00
	 */
	SRVE_CASHUNITINFOCHANGED(1304L),

	/*
	 * @since v3.00
	 */
	SRVE_TELLERINFOCHANGED(1305L),

	/*
	 * @since v3.00
	 */
	EXEE_CASHUNITERROR(1306L),

	/*
	 * @since v3.00
	 */
	SRVE_ITEMSTAKEN(1307L),

	/*
	 * @since v3.00
	 */
	SRVE_COUNTS_CHANGED(1308L),

	/*
	 * @since v3.00
	 */
	EXEE_INPUTREFUSE(1309L),

	/*
	 * @since v3.00
	 */
	SRVE_ITEMSPRESENTED(1310L),

	/*
	 * @since v3.00
	 */
	SRVE_ITEMSINSERTED(1311L),

	/*
	 * @since v3.00
	 */
	EXEE_NOTEERROR(1312L),

	/*
	 * @since v3.00
	 */
	EXEE_SUBCASHIN(1313L),

	/*
	 * @since v3.00
	 */
	SRVE_MEDIADETECTED(1314L),

	/*
	 * @since v3.00
	 */
	EXEE_INPUT_P6(1315L),

	/*
	 * @since v3.10
	 */
	EXEE_INFO_AVAILABLE(1316L),

	/*
	 * @since v3.10
	 */
	EXEE_INSERTITEMS(1317L),

	/*
	 * @since v3.10
	 */
	SRVE_DEVICEPOSITION(1318L),

	/*
	 * @since v3.10
	 */
	SRVE_POWER_SAVE_CHANGE(1319L),

	/*
	 * @since v3.20
	 */
	EXEE_INCOMPLETEREPLENISH(1320L),

	/*
	 * @since v3.30
	 */
	EXEE_INCOMPLETEDEPLETE(1321L),

	/*
	 * @since v3.30
	 */
	SRVE_SHUTTERSTATUSCHANGED(1322L);

	private final long value;

	private CimMessage(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}