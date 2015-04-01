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

public enum PINENCIOProtocols implements XfsConstant {

	/**
	 * For Swiss specific protocols. The document specification for Swiss
	 * specific protocols is "CMD_ENC_IO - CH Protocol.doc". This document is
	 * available at the following address:
	 * 
	 * EUROPAY (Switzerland) SA Terminal Management Hertistrasse 27 CH-8304
	 * Wallisellen
	 * 
	 * @since 3.10
	 */
	WFS_PIN_ENC_PROT_CH(0x0001L),

	/**
	 * Protocol for "Groupement des Cartes Bancaires" (France).
	 * 
	 * @since 3.10
	 */
	WFS_PIN_ENC_PROT_GIECB(0x0002L),

	/**
	 * Protocol for Luxemburg commands. The reference for this specific protocol
	 * is the Authorization Center in Luxemburg (CETREL.) Cryptography
	 * Management Postal address: CETREL Soci�t� Coop�rative Centre de
	 * Transferts Electroniques L-2956 Luxembourg
	 * 
	 * @since 3.10
	 */
	WFS_PIN_ENC_PROT_LUX(0x0004L);

	private final long value;

	private PINENCIOProtocols(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}