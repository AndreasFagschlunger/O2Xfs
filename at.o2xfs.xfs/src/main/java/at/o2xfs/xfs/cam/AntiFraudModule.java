package at.o2xfs.xfs.cam;

import at.o2xfs.xfs.XfsConstant;

public enum AntiFraudModule implements XfsConstant {
	/**
	 * @since 3.20
	 */
	NOTSUPP(0L),
	/**
	 * @since 3.20
	 */
	OK(1L),
	/**
	 * @since 3.20
	 */
	INOP(2L),
	/**
	 * @since 3.20
	 */
	DEVICEDETECTED(3L),
	/**
	 * @since 3.20
	 */
	UNKNOWN(4L);

	private final long value;

	private AntiFraudModule(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
