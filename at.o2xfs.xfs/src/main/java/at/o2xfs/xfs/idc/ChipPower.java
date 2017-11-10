package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum ChipPower implements XfsConstant {

	/*
	 * @since v3.00
	 */
	ONLINE(0L),

	/*
	 * @since v3.00
	 */
	POWEREDOFF(1L),

	/*
	 * @since v3.00
	 */
	BUSY(2L),

	/*
	 * @since v3.00
	 */
	NODEVICE(3L),

	/*
	 * @since v3.00
	 */
	HWERROR(4L),

	/*
	 * @since v3.00
	 */
	NOCARD(5L),

	/*
	 * @since v3.00
	 */
	NOTSUPP(6L),

	/*
	 * @since v3.00
	 */
	UNKNOWN(7L);

	private final long value;

	private ChipPower(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}