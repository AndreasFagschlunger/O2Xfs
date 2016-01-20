package at.o2xfs.xfs.cam;

import at.o2xfs.xfs.XfsConstant;

public enum Media implements XfsConstant {
	/**
	 * @since 3.00
	 */
	OK(0L),
	/**
	 * @since 3.00
	 */
	HIGH(1L),
	/**
	 * @since 3.00
	 */
	FULL(2L),
	/**
	 * @since 3.00
	 */
	UNKNOWN(3L),
	/**
	 * @since 3.00
	 */
	NOTSUPP(4L);

	private final long value;

	private Media(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}