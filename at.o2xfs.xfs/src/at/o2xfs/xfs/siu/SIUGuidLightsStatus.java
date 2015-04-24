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

import at.o2xfs.xfs.util.XfsConstants;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SIUGuidLightsStatus {

	private final int[] guidLights;

	public SIUGuidLightsStatus(final int[] guidLights) {
		if (guidLights == null) {
			throw new IllegalArgumentException("guidLights must not be null");
		} else if (guidLights.length != SIUConstant.GUIDLIGHTS_SIZE) {
			throw new IllegalArgumentException("guidLights.length != " + SIUConstant.GUIDLIGHTS_SIZE);
		}
		this.guidLights = guidLights;
	}

	private SIUGuidLightState getGuidLightState(final SIUGuidLight guidLight) {
		final int value = guidLights[(int) guidLight.getValue()];
		return XfsConstants.valueOf(value, SIUGuidLightState.class);
	}

	public SIUGuidLightState getCardUnit() {
		return getGuidLightState(SIUGuidLight.CARDUNIT);
	}

	public SIUGuidLightState getPINPad() {
		return getGuidLightState(SIUGuidLight.PINPAD);
	}

	public SIUGuidLightState getNoteDispenser() {
		return getGuidLightState(SIUGuidLight.NOTESDISPENSER);
	}

	public SIUGuidLightState getCoinDispenser() {
		return getGuidLightState(SIUGuidLight.COINDISPENSER);
	}

	public SIUGuidLightState getReceiptPrinter() {
		return getGuidLightState(SIUGuidLight.RECEIPTPRINTER);
	}

	public SIUGuidLightState getPassbookPrinter() {
		return getGuidLightState(SIUGuidLight.PASSBOOKPRINTER);
	}

	public SIUGuidLightState getEnvelopeDepository() {
		return getGuidLightState(SIUGuidLight.ENVDEPOSITORY);
	}

	public SIUGuidLightState getChequeUnit() {
		return getGuidLightState(SIUGuidLight.CHEQUEUNIT);
	}

	public SIUGuidLightState getBillAcceptor() {
		return getGuidLightState(SIUGuidLight.BILLACCEPTOR);
	}

	public SIUGuidLightState getEnvelopeDispenser() {
		return getGuidLightState(SIUGuidLight.ENVDISPENSER);
	}

	public SIUGuidLightState getDocumentPrinter() {
		return getGuidLightState(SIUGuidLight.DOCUMENTPRINTER);
	}

	public SIUGuidLightState getCoinAcceptor() {
		return getGuidLightState(SIUGuidLight.COINACCEPTOR);
	}

	public SIUGuidLightState getScanner() {
		return getGuidLightState(SIUGuidLight.SCANNER);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("cardUnit", getCardUnit())
										.append("pinPad", getPINPad())
										.append("noteDispenser", getNoteDispenser())
										.append("coinDispenser", getCoinDispenser())
										.append("receiptPrinter", getReceiptPrinter())
										.append("passbookPrinter", getPassbookPrinter())
										.append("envelopeDepository", getEnvelopeDepository())
										.append("chequeUnit", getChequeUnit())
										.append("billAcceptor", getBillAcceptor())
										.append("envelopeDispenser", getEnvelopeDispenser())
										.append("documentPrinter", getDocumentPrinter())
										.append("coinAcceptor", getCoinAcceptor())
										.append("scanner", getScanner())
										.toString();
	}
}