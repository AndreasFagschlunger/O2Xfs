package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum SafeDoor implements XfsConstant {

	/*
	 * @since v3.00
	 */
	NOTSUPPORTED(1L),

	/*
	 * @since v3.00
	 */
	OPEN(2L),

	/*
	 * @since v3.00
	 */
	CLOSED(3L),

	/*
	 * @since v3.00
	 */
	UNKNOWN(4L);

	private final long value;

	private SafeDoor(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}