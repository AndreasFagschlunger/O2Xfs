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

public enum PtrExecuteCommand implements XfsConstant {

	/*
	 * @since v3.00
	 */
	CONTROL_MEDIA(101L),

	/*
	 * @since v3.00
	 */
	PRINT_FORM(102L),

	/*
	 * @since v3.00
	 */
	READ_FORM(103L),

	/*
	 * @since v3.00
	 */
	RAW_DATA(104L),

	/*
	 * @since v3.00
	 */
	MEDIA_EXTENTS(105L),

	/*
	 * @since v3.00
	 */
	RESET_COUNT(106L),

	/*
	 * @since v3.00
	 */
	READ_IMAGE(107L),

	/*
	 * @since v3.00
	 */
	RESET(108L),

	/*
	 * @since v3.00
	 */
	RETRACT_MEDIA(109L),

	/*
	 * @since v3.00
	 */
	DISPENSE_PAPER(110L),

	/*
	 * @since v3.10
	 */
	SET_GUIDANCE_LIGHT(111L),

	/*
	 * @since v3.10
	 */
	PRINT_RAW_FILE(112L),

	/*
	 * @since v3.10
	 */
	LOAD_DEFINITION(113L),

	/*
	 * @since v3.10
	 */
	SUPPLY_REPLENISH(114L),

	/*
	 * @since v3.10
	 */
	POWER_SAVE_CONTROL(115L),

	/*
	 * @since v3.20
	 */
	CONTROL_PASSBOOK(116L),

	/*
	 * @since v3.30
	 */
	SET_BLACK_MARK_MODE(117L),

	/*
	 * @since v3.30
	 */
	SYNCHRONIZE_COMMAND(118L);

	private final long value;

	private PtrExecuteCommand(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
