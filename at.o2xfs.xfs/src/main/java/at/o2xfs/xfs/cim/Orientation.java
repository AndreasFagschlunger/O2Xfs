package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum Orientation implements XfsConstant {

	/*
	 * @since v3.00
	 */
	FRONTTOP(1L),

	/*
	 * @since v3.00
	 */
	FRONTBOTTOM(2L),

	/*
	 * @since v3.00
	 */
	BACKTOP(3L),

	/*
	 * @since v3.00
	 */
	BACKBOTTOM(4L),

	/*
	 * @since v3.00
	 */
	UNKNOWN(5L),

	/*
	 * @since v3.00
	 */
	NOTSUPPORTED(6L);

	private final long value;

	private Orientation(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}