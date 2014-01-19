package at.o2xfs.xfs.siu;

import at.o2xfs.xfs.XfsConstant;

public enum SIUUPSPortState implements XfsConstant {

	/**
	 * Do not change the current status of the UPS device.
	 */
	NO_CHANGE(0x0000L),

	/**
	 * Engage the UPS.
	 */
	ENGAGE(0x0001L),

	/**
	 * Disengage the UPS.
	 */
	DISENGAGE(0x0002L);

	private final long value;

	private SIUUPSPortState(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
