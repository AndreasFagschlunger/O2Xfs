package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum AdditionalBunches implements XfsConstant {

	/*
	 * @since v3.10
	 */
	NONE(1L),

	/*
	 * @since v3.10
	 */
	ONEMORE(2L),

	/*
	 * @since v3.10
	 */
	UNKNOWN(3L);

	private final long value;

	private AdditionalBunches(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}