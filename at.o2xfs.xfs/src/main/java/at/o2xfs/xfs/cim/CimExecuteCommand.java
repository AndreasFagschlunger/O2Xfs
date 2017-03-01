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

public enum CimExecuteCommand implements XfsConstant {

	/*
	 * @since v3.00
	 */
	CASH_IN_START(1301L),

	/*
	 * @since v3.00
	 */
	CASH_IN(1302L),

	/*
	 * @since v3.00
	 */
	CASH_IN_END(1303L),

	/*
	 * @since v3.00
	 */
	CASH_IN_ROLLBACK(1304L),

	/*
	 * @since v3.00
	 */
	RETRACT(1305L),

	/*
	 * @since v3.00
	 */
	OPEN_SHUTTER(1306L),

	/*
	 * @since v3.00
	 */
	CLOSE_SHUTTER(1307L),

	/*
	 * @since v3.00
	 */
	SET_TELLER_INFO(1308L),

	/*
	 * @since v3.00
	 */
	SET_CASH_UNIT_INFO(1309L),

	/*
	 * @since v3.00
	 */
	START_EXCHANGE(1310L),

	/*
	 * @since v3.00
	 */
	END_EXCHANGE(1311L),

	/*
	 * @since v3.00
	 */
	OPEN_SAFE_DOOR(1312L),

	/*
	 * @since v3.00
	 */
	RESET(1313L),

	/*
	 * @since v3.00
	 */
	CONFIGURE_CASH_IN_UNITS(1314L),

	/*
	 * @since v3.00
	 */
	CONFIGURE_NOTETYPES(1315L),

	/*
	 * @since v3.00
	 */
	CREATE_P6_SIGNATURE(1316L),

	/*
	 * @since v3.10
	 */
	SET_GUIDANCE_LIGHT(1317L),

	/*
	 * @since v3.10
	 */
	CONFIGURE_NOTE_READER(1318L),

	/*
	 * @since v3.10
	 */
	COMPARE_P6_SIGNATURE(1319L),

	/*
	 * @since v3.10
	 */
	POWER_SAVE_CONTROL(1320L),

	/*
	 * @since v3.20
	 */
	REPLENISH(1321L),

	/*
	 * @since v3.20
	 */
	SET_CASH_IN_LIMIT(1322L),

	/*
	 * @since v3.20
	 */
	CASH_UNIT_COUNT(1323L),

	/*
	 * @since v3.20
	 */
	DEVICE_LOCK_CONTROL(1324L),

	/*
	 * @since v3.20
	 */
	SET_MODE(1325L),

	/*
	 * @since v3.20
	 */
	PRESENT_MEDIA(1326L),

	/*
	 * @since v3.30
	 */
	DEPLETE(1327L),

	/*
	 * @since v3.30
	 */
	SET_BLACKLIST(1328L),

	/*
	 * @since v3.30
	 */
	SYNCHRONIZE_COMMAND(1329L);

	private final long value;

	private CimExecuteCommand(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}