/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
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
	MEDIAJAM(-200L),
	NOMEDIA(-201L),
	MEDIARETAINED(-202L),
	RETAINBINFULL(-203L),
	INVALIDDATA(-204L),
	INVALIDMEDIA(-205L),
	FORMNOTFOUND(-206L),
	FORMINVALID(-207L),
	DATASYNTAX(-208L),

	/**
	 * The open of the shutter failed due to manipulation or hardware error.
	 * Operator intervention is required.
	 */
	SHUTTERFAIL(-209L),
	SECURITYFAIL(-210L),
	PROTOCOLNOTSUPP(-211L),
	ATRNOTOBTAINED(-212L),

	/**
	 * The key does not fit to the security module.
	 * 
	 * @since 3.00
	 */
	INVALIDKEY(-213L),

	/**
	 * {@link IDCWriteMethod} value is inconsistent with device capabilities.
	 * 
	 * @since 3.00
	 */
	WRITE_METHOD(-214L),
	CHIPPOWERNOTSUPP(-215L),

	/**
	 * The card that was inserted is too short. When this error occurs the card
	 * remains at the exit slot.
	 * 
	 * @since 3.00
	 */
	CARDTOOSHORT(-216L),

	/**
	 * The card that was inserted is too long. When this error occurs the card
	 * remains at the exit slot.
	 * 
	 * @since 3.00
	 */
	CARDTOOLONG(-217L),
	INVALID_PORT(-218L),
	POWERSAVETOOSHORT(-219L),
	POWERSAVEMEDIAPRESENT(-220L),
	CARDPRESENT(-221L),
	POSITIONINVALID(-222L);

	private final long value;

	private IDCError(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}