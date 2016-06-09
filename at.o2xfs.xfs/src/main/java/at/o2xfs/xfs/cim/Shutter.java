package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum Shutter implements XfsConstant {

	/*
	 * @since v3.00
	 */
	CLOSED(0L),

	/*
	 * @since v3.00
	 */
	OPEN(1L),

	/*
	 * @since v3.00
	 */
	JAMMED(2L),

	/*
	 * @since v3.00
	 */
	UNKNOWN(3L),

	/*
	 * @since v3.00
	 */
	NOTSUPPORTED(4L);

	private final long value;

	private Shutter(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}