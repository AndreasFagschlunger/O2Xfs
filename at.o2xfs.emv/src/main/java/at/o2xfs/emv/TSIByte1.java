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

public final class TSIByte1 {

	private final TSI tsi;

	protected TSIByte1(TSI tsi) {
		this.tsi = tsi;
	}

	private void setBit(int mask, String message) {
		tsi.setBit(0, mask, message);
	}

	/**
	 * Offline data authentication was performed
	 */
	public void offlineDataAuthenticationWasPerformed() {
		setBit(Bit.B8, "Offline data authentication was performed");
	}

	/**
	 * Cardholder verification was performed
	 */
	public void cardholderVerificationWasPerformed() {
		setBit(Bit.B7, "Cardholder verification was performed");
	}

	/**
	 * Card risk management was performed
	 */
	public void cardRiskManagementWasPerformed() {
		setBit(Bit.B6, "Card risk management was performed");
	}

	/**
	 * Issuer authentication was performed
	 */
	public void issuerAuthenticationWasPerformed() {
		setBit(Bit.B5, "Issuer authentication was performed");
	}

	/**
	 * Terminal risk management was performed
	 */
	public void terminalRiskManagementWasPerformed() {
		setBit(Bit.B4, "Terminal risk management was performed");
	}

	/**
	 * Script processing was performed
	 */
	public void scriptProcessingWasPerformed() {
		setBit(Bit.B3, "Script processing was performed");
	}
}