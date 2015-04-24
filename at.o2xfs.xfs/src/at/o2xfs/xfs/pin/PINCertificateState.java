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

public enum PINCertificateState implements XfsConstant {

	/**
	 * The state of the certificate module is unknown or the device dies not
	 * have this capability.
	 * 
	 * @since 3.10
	 */
	WFS_PIN_CERT_UNKNOWN(0x00000000L),

	/**
	 * All pre-loaded certificates have been loaded and that primary
	 * verification certificates will be accepted for the commands
	 * WFS_CMD_PIN_LOAD_CERTIFICATE or WFS_CMD_PIN_REPLACE_CERTIFICATE.
	 * 
	 * @since 3.10
	 */
	WFS_PIN_CERT_PRIMARY(0x00000001L),

	/**
	 * Primary verification certificates will not be accepted and only secondary
	 * verification certificates will be accepted. If primary certificates have
	 * been compromised (which the certificate authority or the host detects),
	 * then secondary certificates should be used in any transaction. This is
	 * done by calling the WFS_CMD_PIN_LOAD_CERTIFICATE command or the
	 * WFS_CMD_PIN_REPLACE_CERTIFICATE.
	 * 
	 * @since 3.10
	 */
	WFS_PIN_CERT_SECONDARY(0x00000002L),

	/**
	 * The certificate module is not ready. (The device is powered off or
	 * physically not present).
	 * 
	 * @since 3.10
	 */
	WFS_PIN_CERT_NOTREADY(0x00000004L);

	private final long value;

	private PINCertificateState(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}