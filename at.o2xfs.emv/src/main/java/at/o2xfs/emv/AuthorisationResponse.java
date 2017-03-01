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

import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;

public abstract class AuthorisationResponse {

	private static final int ARC_LENGTH = 2;

	private final byte[] authorisationResponseCode;

	private final byte[] iccSystemRelatedData;

	public AuthorisationResponse(byte[] authorisationResponseCode) {
		this(authorisationResponseCode, Bytes.EMPTY);
	}

	public AuthorisationResponse(byte[] authorisationResponseCode,
			byte[] iccSystemRelatedData) {
		if (authorisationResponseCode.length != ARC_LENGTH) {
			throw new IllegalArgumentException(
					"Illegal Authorisation Response Code: "
							+ Hex.encode(authorisationResponseCode));
		}
		this.authorisationResponseCode = Bytes.copy(authorisationResponseCode);
		this.iccSystemRelatedData = Bytes.copy(iccSystemRelatedData);
	}

	/**
	 *
	 * @return true if Authorisation Response Code is "Online approved".
	 */
	public abstract boolean isOnlineApproved();

	public final byte[] getAuthorisationResponseCode() {
		return Bytes.copy(authorisationResponseCode);
	}

	public final byte[] getICCSystemRelatedData() {
		return Bytes.copy(iccSystemRelatedData);
	}
}