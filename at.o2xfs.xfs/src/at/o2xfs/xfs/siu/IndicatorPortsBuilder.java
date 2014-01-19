package at.o2xfs.xfs.siu;

import java.util.Map;

import at.o2xfs.win32.WORDArray;

public final class IndicatorPortsBuilder extends AbstractPortsBuilder {

	protected IndicatorPortsBuilder(final WORDArray ports) {
		super(ports);
	}

	public void setOpenClosed(final SIUOpenClosedIndicatorPortState state) {
		setPort(SIUIndicator.OPENCLOSE, state);
	}

	public void setFasciaLight(final SIUFasciaLightPortState state) {
		setPort(SIUIndicator.FASCIALIGHT, state);
	}

	/**
	 * TODO: {@link SIUIndicator#AUDIO}
	 */

	public void setInternalHeating(final SIUInternalHeatingPortState state) {
		setPort(SIUIndicator.HEATING, state);
	}

	public void setConsumerDisplayBacklight(
			final SIUConsumerDisplayBacklightPortState state) {
		setPort(SIUIndicator.CONSUMER_DISPLAY_BACKLIGHT, state);
	}

	public void setTransactionIndicators(final Map<SIULamp, Boolean> indicators) {
		setPorts(SIUIndicator.TRANSINDICATOR, indicators);
	}

	public void setGeneralPurposeOutputPorts(final Map<SIULamp, Boolean> ports) {
		setPorts(SIUIndicator.GENERALOUTPUTPORT, ports);
	}
}
