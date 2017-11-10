package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum Position implements XfsConstant {

	/*
	 * @since v3.30
	 */
	PRESENT(1L),

	/*
	 * @since v3.30
	 */
	NOTPRESENT(2L),

	/*
	 * @since v3.30
	 */
	JAMMED(3L),

	/*
	 * @since v3.30
	 */
	NOTSUPP(4L),

	/*
	 * @since v3.30
	 */
	UNKNOWN(5L),

	/*
	 * @since v3.30
	 */
	ENTERING(6L),

	/*
	 * @since v3.30
	 */
	LATCHED(7L);

	private final long value;

	private Position(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}