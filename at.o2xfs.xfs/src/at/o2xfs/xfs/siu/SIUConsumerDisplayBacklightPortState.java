package at.o2xfs.xfs.siu;

import at.o2xfs.xfs.XfsConstant;

public enum SIUConsumerDisplayBacklightPortState implements XfsConstant {

	/**
	 * Do not change the current status of the Internal Heating device.
	 */
	NO_CHANGE(0x0000L),

	/**
	 * The Internal Heating device is turned off.
	 */
	OFF(0x0001L),

	/**
	 * The Internal Heating device is turned on.
	 */
	ON(0x0002L);

	private final long value;

	private SIUConsumerDisplayBacklightPortState(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
