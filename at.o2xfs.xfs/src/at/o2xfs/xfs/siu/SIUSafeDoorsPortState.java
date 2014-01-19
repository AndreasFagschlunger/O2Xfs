package at.o2xfs.xfs.siu;

import at.o2xfs.xfs.XfsConstant;

public enum SIUSafeDoorsPortState implements XfsConstant {

	/**
	 * 
	 */
	NO_CHANGE(0x0000L),

	/**
	 * 
	 */
	BOLT(0x0001L),

	/**
	 * 
	 */
	UNBOLT(0x0002L);

	private final long value;

	private SIUSafeDoorsPortState(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
