package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum DataStatus implements XfsConstant {

	/*
	 * @since v3.00
	 */
	OK(0L),

	/*
	 * @since v3.00
	 */
	MISSING(1L),

	/*
	 * @since v3.00
	 */
	INVALID(2L),

	/*
	 * @since v3.00
	 */
	TOOLONG(3L),

	/*
	 * @since v3.00
	 */
	TOOSHORT(4L),

	/*
	 * @since v3.00
	 */
	SRCNOTSUPP(5L),

	/*
	 * @since v3.00
	 */
	SRCMISSING(6L);

	private final long value;

	private DataStatus(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}