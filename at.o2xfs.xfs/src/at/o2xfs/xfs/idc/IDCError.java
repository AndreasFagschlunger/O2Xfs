/*
 * Copyright (c) 2012, Andreas Fagschlunger. All rights reserved.
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

public enum IDCError implements XfsConstant {
	WFS_ERR_IDC_MEDIAJAM(-200L),
	WFS_ERR_IDC_NOMEDIA(-201L),
	WFS_ERR_IDC_MEDIARETAINED(-202L),
	WFS_ERR_IDC_RETAINBINFULL(-203L),
	WFS_ERR_IDC_INVALIDDATA(-204L),
	WFS_ERR_IDC_INVALIDMEDIA(-205L),
	WFS_ERR_IDC_FORMNOTFOUND(-206L),
	WFS_ERR_IDC_FORMINVALID(-207L),
	WFS_ERR_IDC_DATASYNTAX(-208L),

	/**
	 * The open of the shutter failed due to manipulation or hardware error.
	 * Operator intervention is required.
	 */
	WFS_ERR_IDC_SHUTTERFAIL(-209L),
	WFS_ERR_IDC_SECURITYFAIL(-210L),
	WFS_ERR_IDC_PROTOCOLNOTSUPP(-211L),
	WFS_ERR_IDC_ATRNOTOBTAINED(-212L),

	/**
	 * The key does not fit to the security module.
	 * 
	 * @since 3.00
	 */
	WFS_ERR_IDC_INVALIDKEY(-213L),

	/**
	 * {@link IDCWriteMethod} value is inconsistent with device capabilities.
	 * 
	 * @since 3.00
	 */
	WFS_ERR_IDC_WRITE_METHOD(-214L),
	WFS_ERR_IDC_CHIPPOWERNOTSUPP(-215L),

	/**
	 * The card that was inserted is too short. When this error occurs the card
	 * remains at the exit slot.
	 * 
	 * @since 3.00
	 */
	WFS_ERR_IDC_CARDTOOSHORT(-216L),

	/**
	 * The card that was inserted is too long. When this error occurs the card
	 * remains at the exit slot.
	 * 
	 * @since 3.00
	 */
	WFS_ERR_IDC_CARDTOOLONG(-217L),
	WFS_ERR_IDC_INVALID_PORT(-218L),
	WFS_ERR_IDC_POWERSAVETOOSHORT(-219L),
	WFS_ERR_IDC_POWERSAVEMEDIAPRESENT(-220L),
	WFS_ERR_IDC_CARDPRESENT(-221L),
	WFS_ERR_IDC_POSITIONINVALID(-222L);

	private final long value;

	private IDCError(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
