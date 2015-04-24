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

package at.o2xfs.xfs.siu;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SIUGuidLightCapabilities {

	private final int[] guidLights;

	public SIUGuidLightCapabilities(final int[] guidLights) {
		if (guidLights == null) {
			throw new IllegalArgumentException("guidLights must not be null");
		} else if (guidLights.length != SIUConstant.GUIDLIGHTS_SIZE) {
			throw new IllegalArgumentException("guidLights.length != " + SIUConstant.GUIDLIGHTS_SIZE);
		}
		this.guidLights = guidLights;
	}

	private boolean isAvailable(final SIUGuidLight guidLight) {
		final int value = guidLights[(int) guidLight.getValue()];
		return value == SIUConstant.AVAILABLE;
	}

	public boolean isCardUnit() {
		return isAvailable(SIUGuidLight.CARDUNIT);
	}

	public boolean isPINPad() {
		return isAvailable(SIUGuidLight.PINPAD);
	}

	public boolean isNoteDispenser() {
		return isAvailable(SIUGuidLight.NOTESDISPENSER);
	}

	public boolean isCoinDispenser() {
		return isAvailable(SIUGuidLight.COINDISPENSER);
	}

	public boolean isReceiptPrinter() {
		return isAvailable(SIUGuidLight.RECEIPTPRINTER);
	}

	public boolean isPassbookPrinter() {
		return isAvailable(SIUGuidLight.PASSBOOKPRINTER);
	}

	public boolean isEnvelopeDepository() {
		return isAvailable(SIUGuidLight.ENVDEPOSITORY);
	}

	public boolean isChequeUnit() {
		return isAvailable(SIUGuidLight.CHEQUEUNIT);
	}

	public boolean isBillAcceptor() {
		return isAvailable(SIUGuidLight.BILLACCEPTOR);
	}

	public boolean isEnvelopeDispenser() {
		return isAvailable(SIUGuidLight.ENVDISPENSER);
	}

	public boolean isDocumentPrinter() {
		return isAvailable(SIUGuidLight.DOCUMENTPRINTER);
	}

	public boolean isCoinAcceptor() {
		return isAvailable(SIUGuidLight.COINACCEPTOR);
	}

	public boolean isScanner() {
		return isAvailable(SIUGuidLight.SCANNER);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("cardUnit", isCardUnit())
										.append("pinPad", isPINPad())
										.append("noteDispenser", isNoteDispenser())
										.append("coinDispenser", isCoinDispenser())
										.append("receiptPrinter", isReceiptPrinter())
										.append("passbookPrinter", isPassbookPrinter())
										.append("envelopeDepository", isEnvelopeDepository())
										.append("chequeUnit", isChequeUnit())
										.append("billAcceptor", isBillAcceptor())
										.append("envelopeDispenser", isEnvelopeDispenser())
										.append("documentPrinter", isDocumentPrinter())
										.append("coinAcceptor", isCoinAcceptor())
										.append("scanner", isScanner())
										.toString();
	}
}