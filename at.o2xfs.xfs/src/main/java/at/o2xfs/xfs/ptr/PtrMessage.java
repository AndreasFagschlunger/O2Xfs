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

public enum PtrMessage implements XfsConstant {

	/*
	 * @since v3.00
	 */
	EXEE_PTR_NOMEDIA(101L),

	/*
	 * @since v3.00
	 */
	EXEE_PTR_MEDIAINSERTED(102L),

	/*
	 * @since v3.00
	 */
	EXEE_PTR_FIELDERROR(103L),

	/*
	 * @since v3.00
	 */
	EXEE_PTR_FIELDWARNING(104L),

	/*
	 * @since v3.00
	 */
	USRE_PTR_RETRACTBINTHRESHOLD(105L),

	/*
	 * @since v3.00
	 */
	SRVE_PTR_MEDIATAKEN(106L),

	/*
	 * @since v3.00
	 */
	USRE_PTR_PAPERTHRESHOLD(107L),

	/*
	 * @since v3.00
	 */
	USRE_PTR_TONERTHRESHOLD(108L),

	/*
	 * @since v3.00
	 */
	SRVE_PTR_MEDIAINSERTED(109L),

	/*
	 * @since v3.00
	 */
	USRE_PTR_LAMPTHRESHOLD(110L),

	/*
	 * @since v3.00
	 */
	USRE_PTR_INKTHRESHOLD(111L),

	/*
	 * @since v3.00
	 */
	SRVE_PTR_MEDIADETECTED(112L),

	/*
	 * @since v3.10
	 */
	SRVE_PTR_RETRACTBINSTATUS(113L),

	/*
	 * @since v3.10
	 */
	EXEE_PTR_MEDIAPRESENTED(114L),

	/*
	 * @since v3.10
	 */
	SRVE_PTR_DEFINITIONLOADED(115L),

	/*
	 * @since v3.10
	 */
	EXEE_PTR_MEDIAREJECTED(116L),

	/*
	 * @since v3.10
	 */
	SRVE_PTR_MEDIAPRESENTED(117L),

	/*
	 * @since v3.10
	 */
	SRVE_PTR_MEDIAAUTORETRACTED(118L),

	/*
	 * @since v3.10
	 */
	SRVE_PTR_DEVICEPOSITION(119L),

	/*
	 * @since v3.10
	 */
	SRVE_PTR_POWER_SAVE_CHANGE(120L);

	private final long value;

	private PtrMessage(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
