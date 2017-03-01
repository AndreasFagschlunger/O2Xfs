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

package at.o2xfs.emv;

import at.o2xfs.common.Bit;
import at.o2xfs.common.Bytes;

public final class CryptogramInformationData {

	/**
	 * Advice Code: Service not allowed
	 */
	public static final int SERVICE_NOT_ALLOWED = 1;

	/**
	 * Advice Code: PIN Try Limit exceeded
	 */
	public static final int PIN_TRY_LIMIT_EXCEEDED = 2;

	/**
	 * Advice Code: Issuer authentication failed
	 */
	public static final int ISSUER_AUTHENTICATION_FAILED = 3;

	private final byte b;

	private CryptogramInformationData(byte b) {
		this.b = b;
	}

	public CryptogramType getCryptogramType() {
		return CryptogramType.valueOf(Bytes.toInt(b));
	}

	public boolean isAdviceRequired() {
		return Bit.isSet(b, Bit.B4);
	}

	public int getAdviceCode() {
		return b & 0x07;
	}

	public static final CryptogramInformationData parse(byte[] b) {
		return new CryptogramInformationData(b[0]);
	}
}