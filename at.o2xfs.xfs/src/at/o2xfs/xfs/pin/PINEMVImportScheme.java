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

public enum PINEMVImportScheme implements XfsConstant {

	/**
	 * A plain text CA public key is imported with no verification.
	 * 
	 * @since 3.10
	 */
	PLAIN_CA(1L),

	/**
	 * A plain text CA public key is imported using the EMV 2000 verification
	 * algorithm. See [Ref. 4].
	 * 
	 * @since 3.10
	 */
	CHKSUM_CA(2L),

	/**
	 * A CA public key is imported using the selfsign scheme defined in the
	 * Europay International, EPI CA Module Technical - Interface specification
	 * Version 1.4, [Ref. 5].
	 * 
	 * @since 3.10
	 */
	EPI_CA(3L),

	/**
	 * An Issuer public key is imported as defined in EMV 2000 Book II, [Ref.
	 * 4].
	 * 
	 * @since 3.10
	 */
	ISSUER(4L),

	/**
	 * An ICC public key is imported as defined in EMV 2000 Book II, [Ref. 4].
	 * 
	 * @since 3.10
	 */
	ICC(5L),

	/**
	 * An ICC PIN public key is imported as defined in EMV 2000 Book II, [Ref.
	 * 4].
	 * 
	 * @since 3.10
	 */
	ICC_PIN(6L),

	/**
	 * A CA public key is imported and verified using a signature generated with
	 * a private key for which the public key is already loaded.
	 * 
	 * @since 3.10
	 */
	PKCSV1_5_CA(7L);

	private final long value;

	private PINEMVImportScheme(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}