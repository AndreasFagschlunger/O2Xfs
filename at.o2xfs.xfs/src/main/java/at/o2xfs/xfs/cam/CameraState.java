package at.o2xfs.xfs.cam;

import at.o2xfs.xfs.XfsConstant;

public enum CameraState implements XfsConstant {
	/**
	 * @since 3.00
	 */
	NOTSUPP(0L),

	/**
	 * @since 3.00
	 */
	OK(1L),

	/**
	 * @since 3.00
	 */
	INOP(2L),
	/**
	 * @since 3.00
	 */
	UNKNOWN(3L);

	private final long value;

	private CameraState(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
