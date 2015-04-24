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

import at.o2xfs.win32.WORDArray;

public final class GuidLightsPortsBuilder
		extends AbstractPortsBuilder {

	protected GuidLightsPortsBuilder(final WORDArray ports) {
		super(ports);
	}

	public void setCardUnit(final SIUGuidLightPortState state) {
		setPort(SIUGuidLight.CARDUNIT, state);
	}

	public void setPINPad(final SIUGuidLightPortState state) {
		setPort(SIUGuidLight.PINPAD, state);
	}

	public void setNoteDispenser(final SIUGuidLightPortState state) {
		setPort(SIUGuidLight.NOTESDISPENSER, state);
	}

	public void setCoinDispenser(final SIUGuidLightPortState state) {
		setPort(SIUGuidLight.COINDISPENSER, state);
	}

	public void setReceiptPrinter(final SIUGuidLightPortState state) {
		setPort(SIUGuidLight.RECEIPTPRINTER, state);
	}

	public void setPassbookPrinter(final SIUGuidLightPortState state) {
		setPort(SIUGuidLight.PASSBOOKPRINTER, state);
	}

	public void setEnvelopeDepository(final SIUGuidLightPortState state) {
		setPort(SIUGuidLight.ENVDEPOSITORY, state);
	}

	public void setChequeUnit(final SIUGuidLightPortState state) {
		setPort(SIUGuidLight.CHEQUEUNIT, state);
	}

	public void setBillAcceptor(final SIUGuidLightPortState state) {
		setPort(SIUGuidLight.BILLACCEPTOR, state);
	}

	public void setEnvelopeDispenser(final SIUGuidLightPortState state) {
		setPort(SIUGuidLight.ENVDISPENSER, state);
	}

	public void setDocumentPrinter(final SIUGuidLightPortState state) {
		setPort(SIUGuidLight.DOCUMENTPRINTER, state);
	}

	public void setCoinAcceptor(final SIUGuidLightPortState state) {
		setPort(SIUGuidLight.COINACCEPTOR, state);
	}

	public void setScanner(final SIUGuidLightPortState state) {
		setPort(SIUGuidLight.SCANNER, state);
	}
}