package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum CashUnitStatus implements XfsConstant {

	/*
	 * @since v3.00
	 */
	OK(0L),

	/*
	 * @since v3.00
	 */
	FULL(1L),

	/*
	 * @since v3.00
	 */
	HIGH(2L),

	/*
	 * @since v3.00
	 */
	LOW(3L),

	/*
	 * @since v3.00
	 */
	EMPTY(4L),

	/*
	 * @since v3.00
	 */
	INOP(5L),

	/*
	 * @since v3.00
	 */
	MISSING(6L),

	/*
	 * @since v3.00
	 */
	NOVAL(7L),

	/*
	 * @since v3.00
	 */
	NOREF(8L),

	/*
	 * @since v3.00
	 */
	MANIP(9L);

	private final long value;

	private CashUnitStatus(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}