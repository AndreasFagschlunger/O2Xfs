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

public enum IdcError implements XfsConstant {

	/*
	 * @since v3.00
	 */
	MEDIAJAM(-200L),

	/*
	 * @since v3.00
	 */
	NOMEDIA(-201L),

	/*
	 * @since v3.00
	 */
	MEDIARETAINED(-202L),

	/*
	 * @since v3.00
	 */
	RETAINBINFULL(-203L),

	/*
	 * @since v3.00
	 */
	INVALIDDATA(-204L),

	/*
	 * @since v3.00
	 */
	INVALIDMEDIA(-205L),

	/*
	 * @since v3.00
	 */
	FORMNOTFOUND(-206L),

	/*
	 * @since v3.00
	 */
	FORMINVALID(-207L),

	/*
	 * @since v3.00
	 */
	DATASYNTAX(-208L),

	/*
	 * @since v3.00
	 */
	SHUTTERFAIL(-209L),

	/*
	 * @since v3.00
	 */
	SECURITYFAIL(-210L),

	/*
	 * @since v3.00
	 */
	PROTOCOLNOTSUPP(-211L),

	/*
	 * @since v3.00
	 */
	ATRNOTOBTAINED(-212L),

	/*
	 * @since v3.00
	 */
	INVALIDKEY(-213L),

	/*
	 * @since v3.00
	 */
	WRITE_METHOD(-214L),

	/*
	 * @since v3.00
	 */
	CHIPPOWERNOTSUPP(-215L),

	/*
	 * @since v3.00
	 */
	CARDTOOSHORT(-216L),

	/*
	 * @since v3.00
	 */
	CARDTOOLONG(-217L),

	/*
	 * @since v3.10
	 */
	INVALID_PORT(-218L),

	/*
	 * @since v3.10
	 */
	POWERSAVETOOSHORT(-219L),

	/*
	 * @since v3.10
	 */
	POWERSAVEMEDIAPRESENT(-220L),

	/*
	 * @since v3.20
	 */
	CARDPRESENT(-221L),

	/*
	 * @since v3.20
	 */
	POSITIONINVALID(-222L),

	/*
	 * @since v3.30
	 */
	INVALIDTERMINALDATA(-223L),

	/*
	 * @since v3.30
	 */
	INVALIDAIDDATA(-224L),

	/*
	 * @since v3.30
	 */
	INVALIDKEYDATA(-225L),

	/*
	 * @since v3.30
	 */
	READERNOTCONFIGURED(-226L),

	/*
	 * @since v3.30
	 */
	TRANSACTIONNOTINITIATED(-227L),

	/*
	 * @since v3.30
	 */
	COMMANDUNSUPP(-228L),

	/*
	 * @since v3.30
	 */
	SYNCHRONIZEUNSUPP(-229L);

	private final long value;

	private IdcError(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}