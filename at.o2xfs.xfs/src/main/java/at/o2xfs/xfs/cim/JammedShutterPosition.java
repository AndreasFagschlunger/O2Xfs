package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum JammedShutterPosition implements XfsConstant {

	/*
	 * @since v3.30
	 */
	NOTSUPPORTED(0L),

	/*
	 * @since v3.30
	 */
	NOTJAMMED(1L),

	/*
	 * @since v3.30
	 */
	OPEN(2L),

	/*
	 * @since v3.30
	 */
	PARTIALLY_OPEN(3L),

	/*
	 * @since v3.30
	 */
	CLOSED(4L),

	/*
	 * @since v3.30
	 */
	UNKNOWN(5L);

	private final long value;

	private JammedShutterPosition(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}