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

public enum IdcMessage implements XfsConstant {

	/*
	 * @since v3.00
	 */
	EXEE_INVALIDTRACKDATA(201L),

	/*
	 * @since v3.00
	 */
	EXEE_MEDIAINSERTED(203L),

	/*
	 * @since v3.00
	 */
	SRVE_MEDIAREMOVED(204L),

	/*
	 * @since v3.00
	 */
	SRVE_CARDACTION(205L),

	/*
	 * @since v3.00
	 */
	USRE_RETAINBINTHRESHOLD(206L),

	/*
	 * @since v3.00
	 */
	EXEE_INVALIDMEDIA(207L),

	/*
	 * @since v3.00
	 */
	EXEE_MEDIARETAINED(208L),

	/*
	 * @since v3.00
	 */
	SRVE_MEDIADETECTED(209L),

	/*
	 * @since v3.10
	 */
	SRVE_RETAINBININSERTED(210L),

	/*
	 * @since v3.10
	 */
	SRVE_RETAINBINREMOVED(211L),

	/*
	 * @since v3.10
	 */
	EXEE_INSERTCARD(212L),

	/*
	 * @since v3.10
	 */
	SRVE_DEVICEPOSITION(213L),

	/*
	 * @since v3.10
	 */
	SRVE_POWER_SAVE_CHANGE(214L),

	/*
	 * @since v3.20
	 */
	EXEE_TRACKDETECTED(215L),

	/*
	 * @since v3.30
	 */
	EXEE_EMVCLESSREADSTATUS(216L);

	private final long value;

	private IdcMessage(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}