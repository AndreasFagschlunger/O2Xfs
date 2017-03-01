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

public enum CdmExecuteCommand implements XfsConstant {

	/*
	 * @since v3.00
	 */
	DENOMINATE(301L),

	/*
	 * @since v3.00
	 */
	DISPENSE(302L),

	/*
	 * @since v3.00
	 */
	PRESENT(303L),

	/*
	 * @since v3.00
	 */
	REJECT(304L),

	/*
	 * @since v3.00
	 */
	RETRACT(305L),

	/*
	 * @since v3.00
	 */
	OPEN_SHUTTER(307L),

	/*
	 * @since v3.00
	 */
	CLOSE_SHUTTER(308L),

	/*
	 * @since v3.00
	 */
	SET_TELLER_INFO(309L),

	/*
	 * @since v3.00
	 */
	SET_CASH_UNIT_INFO(310L),

	/*
	 * @since v3.00
	 */
	START_EXCHANGE(311L),

	/*
	 * @since v3.00
	 */
	END_EXCHANGE(312L),

	/*
	 * @since v3.00
	 */
	OPEN_SAFE_DOOR(313L),

	/*
	 * @since v3.00
	 */
	CALIBRATE_CASH_UNIT(315L),

	/*
	 * @since v3.00
	 */
	SET_MIX_TABLE(320L),

	/*
	 * @since v3.00
	 */
	RESET(321L),

	/*
	 * @since v3.00
	 */
	TEST_CASH_UNITS(322L),

	/*
	 * @since v3.00
	 */
	COUNT(323L),

	/*
	 * @since v3.10
	 */
	SET_GUIDANCE_LIGHT(324L),

	/*
	 * @since v3.10
	 */
	POWER_SAVE_CONTROL(325L),

	/*
	 * @since v3.10
	 */
	PREPARE_DISPENSE(326L),

	/*
	 * @since v3.30
	 */
	SET_BLACKLIST(327L),

	/*
	 * @since v3.30
	 */
	SYNCHRONIZE_COMMAND(328L);

	private final long value;

	private CdmExecuteCommand(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}