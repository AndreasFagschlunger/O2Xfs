package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum Type implements XfsConstant {

	/*
	 * @since v3.00
	 */
	MOTOR(1L),

	/*
	 * @since v3.00
	 */
	SWIPE(2L),

	/*
	 * @since v3.00
	 */
	DIP(3L),

	/*
	 * @since v3.00
	 */
	CONTACTLESS(4L),

	/*
	 * @since v3.00
	 */
	LATCHEDDIP(5L),

	/*
	 * @since v3.00
	 */
	PERMANENT(6L),

	/*
	 * @since v3.30
	 */
	INTELLIGENTCONTACTLESS(7L);

	private final long value;

	private Type(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}