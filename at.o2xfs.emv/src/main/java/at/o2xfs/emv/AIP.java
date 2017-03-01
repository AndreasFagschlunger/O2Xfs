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

import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.common.Bit;
import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;

/**
 * Application Interchange Profile
 */
public class AIP {

	private final byte[] aip;

	private boolean sda = false;

	private boolean dda = false;

	private boolean cardholderVerification = false;

	private boolean terminalRiskManagement = false;

	private boolean issuerAuthentication = false;

	private boolean cda = false;

	public AIP(byte[] aip) {
		if (aip.length != 2) {
			throw new IllegalArgumentException("Illegal AIP: "
					+ Hex.encode(aip));
		}
		this.aip = Bytes.copy(aip);
		parseByte1();
	}

	private void parseByte1() {
		byte b = aip[0];
		sda = Bit.isSet(b, Bit.B7);
		dda = Bit.isSet(b, Bit.B6);
		cardholderVerification = Bit.isSet(b, Bit.B5);
		terminalRiskManagement = Bit.isSet(b, Bit.B4);
		issuerAuthentication = Bit.isSet(b, Bit.B3);
		cda = Bit.isSet(b, Bit.B1);
	}

	/**
	 *
	 */
	public byte[] getBytes() {
		return Bytes.copy(aip);
	}

	/**
	 * @return true if SDA is supported
	 */
	public boolean isSDA() {
		return sda;
	}

	/**
	 * @return true if DDA is supported
	 */
	public boolean isDDA() {
		return dda;
	}

	/**
	 * @return true if Cardholder verification is supported
	 */
	public boolean isCardholderVerification() {
		return cardholderVerification;
	}

	/**
	 * @return Terminal risk management is to be performed
	 */
	public boolean isTerminalRiskManagement() {
		return terminalRiskManagement;
	}

	/**
	 * @return true if Issuer authentication is supported
	 */
	public boolean isIssuerAuthentication() {
		return issuerAuthentication;
	}

	/**
	 * @return true if CDA is supported
	 */
	public boolean isCDA() {
		return cda;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("sda", sda).append("dda", dda)
				.append("cardholderVerification", cardholderVerification)
				.append("terminalRiskManagement", terminalRiskManagement)
				.append("issuerAuthentication", issuerAuthentication)
				.append("cda", cda).toString();
	}
}