/*
 * Copyright (c) 2016, Andreas Fagschlunger. All rights reserved.
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

public enum CdmMessage implements XfsConstant {

	/*
	 * @since v3.00
	 */
	SRVE_CDM_SAFEDOOROPEN(301L),

	/*
	 * @since v3.00
	 */
	SRVE_CDM_SAFEDOORCLOSED(302L),

	/*
	 * @since v3.00
	 */
	USRE_CDM_CASHUNITTHRESHOLD(303L),

	/*
	 * @since v3.00
	 */
	SRVE_CDM_CASHUNITINFOCHANGED(304L),

	/*
	 * @since v3.00
	 */
	SRVE_CDM_TELLERINFOCHANGED(305L),

	/*
	 * @since v3.00
	 */
	EXEE_CDM_DELAYEDDISPENSE(306L),

	/*
	 * @since v3.00
	 */
	EXEE_CDM_STARTDISPENSE(307L),

	/*
	 * @since v3.00
	 */
	EXEE_CDM_CASHUNITERROR(308L),

	/*
	 * @since v3.00
	 */
	SRVE_CDM_ITEMSTAKEN(309L),

	/*
	 * @since v3.00
	 */
	EXEE_CDM_PARTIALDISPENSE(310L),

	/*
	 * @since v3.00
	 */
	EXEE_CDM_SUBDISPENSEOK(311L),

	/*
	 * @since v3.00
	 */
	SRVE_CDM_ITEMSPRESENTED(313L),

	/*
	 * @since v3.00
	 */
	SRVE_CDM_COUNTS_CHANGED(314L),

	/*
	 * @since v3.00
	 */
	EXEE_CDM_INCOMPLETEDISPENSE(315L),

	/*
	 * @since v3.00
	 */
	EXEE_CDM_NOTEERROR(316L),

	/*
	 * @since v3.00
	 */
	EXEE_CDM_MEDIADETECTED(317L),

	/*
	 * @since v3.00
	 */
	SRVE_CDM_MEDIADETECTED(317L),

	/*
	 * @since v3.10
	 */
	EXEE_CDM_INPUT_P6(318L),

	/*
	 * @since v3.10
	 */
	SRVE_CDM_DEVICEPOSITION(319L),

	/*
	 * @since v3.10
	 */
	SRVE_CDM_POWER_SAVE_CHANGE(320L),

	/*
	 * @since v3.30
	 */
	EXEE_CDM_INFO_AVAILABLE(321L),

	/*
	 * @since v3.30
	 */
	EXEE_CDM_INCOMPLETERETRACT(322L),

	/*
	 * @since v3.30
	 */
	SRVE_CDM_SHUTTERSTATUSCHANGED(323L);

	private final long value;

	private CdmMessage(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}