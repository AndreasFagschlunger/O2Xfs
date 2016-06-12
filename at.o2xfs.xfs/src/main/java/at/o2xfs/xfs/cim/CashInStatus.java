package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum CashInStatus implements XfsConstant {

	/*
	 * @since v3.00
	 */
	OK(0L),

	/*
	 * @since v3.00
	 */
	ROLLBACK(1L),

	/*
	 * @since v3.00
	 */
	ACTIVE(2L),

	/*
	 * @since v3.00
	 */
	RETRACT(3L),

	/*
	 * @since v3.00
	 */
	UNKNOWN(4L),

	/*
	 * @since v3.10
	 */
	RESET(5L);

	private final long value;

	private CashInStatus(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}