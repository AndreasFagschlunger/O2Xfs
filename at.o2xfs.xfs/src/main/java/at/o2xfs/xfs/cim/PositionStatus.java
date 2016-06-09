package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum PositionStatus implements XfsConstant {

	/*
	 * @since v3.00
	 */
	EMPTY(0L),

	/*
	 * @since v3.00
	 */
	NOTEMPTY(1L),

	/*
	 * @since v3.00
	 */
	UNKNOWN(2L),

	/*
	 * @since v3.00
	 */
	NOTSUPPORTED(3L),

	/*
	 * @since v3.10
	 */
	FOREIGNITEMS(4L);

	private final long value;

	private PositionStatus(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}