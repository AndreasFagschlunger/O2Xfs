package at.o2xfs.xfs.siu;

import at.o2xfs.xfs.XfsConstant;

public enum SIUOpenClosedIndicatorPortState implements XfsConstant {

	/**
	 * 
	 */
	NO_CHANGE(0x0000L),

	/**
	 * 
	 */
	CLOSED(0x0001L),

	/**
	 * 
	 */
	OPEN(0x0002L);

	private final long value;

	private SIUOpenClosedIndicatorPortState(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
