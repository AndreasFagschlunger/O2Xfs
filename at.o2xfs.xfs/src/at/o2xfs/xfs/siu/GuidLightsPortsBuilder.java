package at.o2xfs.xfs.siu;

import at.o2xfs.win32.WORDArray;

public final class GuidLightsPortsBuilder extends AbstractPortsBuilder {

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
