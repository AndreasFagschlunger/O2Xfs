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

package at.o2xfs.xfs.pin;

import at.o2xfs.xfs.XfsConstant;

public enum PINError implements XfsConstant {

	WFS_ERR_PIN_KEYNOTFOUND(-400L),
	WFS_ERR_PIN_MODENOTSUPPORTED(-401L),
	WFS_ERR_PIN_ACCESSDENIED(-402L),
	WFS_ERR_PIN_INVALIDID(-403L),
	WFS_ERR_PIN_DUPLICATEKEY(-404L),
	WFS_ERR_PIN_KEYNOVALUE(-406L),
	WFS_ERR_PIN_USEVIOLATION(-407L),
	WFS_ERR_PIN_NOPIN(-408L),
	WFS_ERR_PIN_INVALIDKEYLENGTH(-409L),

	/**
	 * At least one of the specified function keys or FDKs is invalid.
	 */
	WFS_ERR_PIN_KEYINVALID(-410L),

	/**
	 * At least one of the specified function keys or FDKs is not supported by
	 * the service provider.
	 */
	WFS_ERR_PIN_KEYNOTSUPPORTED(-411L),

	/**
	 * There are no active function keys specified.
	 */
	WFS_ERR_PIN_NOACTIVEKEYS(-412L),
	WFS_ERR_PIN_INVALIDKEY(-413L),
	WFS_ERR_PIN_NOTERMINATEKEYS(-414L),
	WFS_ERR_PIN_MINIMUMLENGTH(-415L),
	WFS_ERR_PIN_PROTOCOLNOTSUPP(-416L),
	WFS_ERR_PIN_INVALIDDATA(-417L),
	WFS_ERR_PIN_NOTALLOWED(-418L),

	/**
	 * @since 3.00
	 */
	WFS_ERR_PIN_NOKEYRAM(-419L),

	/**
	 * @since 3.00
	 */
	WFS_ERR_PIN_NOCHIPTRANSACTIVE(-420L),

	/**
	 * @since 3.00
	 */
	WFS_ERR_PIN_ALGORITHMNOTSUPP(-421L),

	/**
	 * @since 3.00
	 */
	WFS_ERR_PIN_FORMATNOTSUPP(-422L),

	/**
	 * @since 3.00
	 */
	WFS_ERR_PIN_HSMSTATEINVALID(-423L),

	/**
	 * @since 3.00
	 */
	WFS_ERR_PIN_MACINVALID(-424L),

	/**
	 * @since 3.00
	 */
	WFS_ERR_PIN_PROTINVALID(-425L),

	/**
	 * @since 3.00
	 */
	WFS_ERR_PIN_FORMATINVALID(-426L),

	/**
	 * @since 3.00
	 */
	WFS_ERR_PIN_CONTENTINVALID(-427L),

	/**
	 * @since 3.00
	 */
	WFS_ERR_PIN_SIG_NOT_SUPP(-429L),

	/**
	 * @since 3.00
	 */
	WFS_ERR_PIN_INVALID_MOD_LEN(-431L),

	/**
	 * @since 3.00
	 */
	WFS_ERR_PIN_INVALIDCERTSTATE(-432L),

	/**
	 * @since 3.00
	 */
	WFS_ERR_PIN_KEY_GENERATION_ERROR(-433L),

	/**
	 * @since 3.00
	 */
	WFS_ERR_PIN_EMV_VERIFY_FAILED(-434L),

	/**
	 * @since 3.00
	 */
	WFS_ERR_PIN_RANDOMINVALID(-435L),

	/**
	 * @since 3.00
	 */
	WFS_ERR_PIN_SIGNATUREINVALID(-436L),

	/**
	 * @since 3.00
	 */
	WFS_ERR_PIN_SNSCDINVALID(-437L),

	/**
	 * @since 3.00
	 */
	WFS_ERR_PIN_NORSAKEYPAIR(-438L),

	/**
	 * @since 3.10
	 */
	WFS_ERR_PIN_INVALID_PORT(-439L),

	/**
	 * @since 3.10
	 */
	WFS_ERR_PIN_POWERSAVETOOSHORT(-440L);

	private final long value;

	private PINError(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}