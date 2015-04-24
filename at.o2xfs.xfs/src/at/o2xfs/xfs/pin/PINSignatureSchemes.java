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

public enum PINSignatureSchemes implements XfsConstant {

	/**
	 * Specifies if the Service Provider supports the RSA Signature Scheme
	 * WFS_CMD_PIN_GENERATE_RSA_KEY _PAIR and WFS_CMD_PIN_EXPORT_RSA_EPP_SIG NED
	 * commands.
	 * 
	 * @since 3.10
	 */
	WFS_PIN_SIG_GEN_RSA_KEY_PAIR(0x00000001L),

	/**
	 * Specifies if the Service Provider returns a random number from the
	 * WFS_CMD_PIN_START_KEY_EXCHAN GE command within the RSA Signature Scheme.
	 * 
	 * @since 3.10
	 */
	WFS_PIN_SIG_RANDOM_NUMBER(0x00000002L),

	/**
	 * Specifies if the Service Provider supports exporting the EPP Security
	 * Item within the RSA Signature Scheme.
	 * 
	 * @since 3.10
	 */
	WFS_PIN_SIG_EXPORT_EPP_ID(0x00000004L),

	/**
	 * Specifies that the Service Provider supports the Enhanced Signature
	 * Remote Key Scheme. This scheme allows the customer to manage their own
	 * public keys independently of the Signature Issuer. When this mode is
	 * supported then the key loaded signed with the Signature Issuer key is the
	 * host root public key PK<sub>ROOT</sub>, rather than PK<sub>HOST</sub>.
	 * See Section 8.1 for a full description.
	 * 
	 * @since 3.10
	 */
	WFS_PIN_SIG_ENHANCED_RKL(0x00000008L);

	private final long value;

	private PINSignatureSchemes(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}