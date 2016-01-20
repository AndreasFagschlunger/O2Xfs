package at.o2xfs.xfs.cam;

import at.o2xfs.xfs.XfsConstant;

public enum CamDeviceState implements XfsConstant {
	/**
	 * @since 2.00
	 */
	ONLINE(0L),
	/**
	 * @since 2.00
	 */
	OFFLINE(1L),
	/**
	 * @since 2.00
	 */
	POWEROFF(2L),
	/**
	 * @since 2.00
	 */
	NODEVICE(3L),
	/**
	 * @since 2.00
	 */
	HWERROR(4L),
	/**
	 * @since 2.00
	 */
	USERERROR(5L),
	/**
	 * @since 3.00
	 */
	BUSY(6L),
	/**
	 * @since 3.10
	 */
	FRAUDATTEMPT(7L),
	/**
	 * @since 3.20
	 */
	POTENTIALFRAUD(8L);

	private final long value;

	private CamDeviceState(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
