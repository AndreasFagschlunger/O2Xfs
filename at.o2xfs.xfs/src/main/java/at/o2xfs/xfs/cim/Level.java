package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum Level implements XfsConstant {

	/*
	 * @since v3.00
	 */
	LEVEL_ALL(0L),

	/*
	 * @since v3.00
	 */
	LEVEL_2(2L),

	/*
	 * @since v3.00
	 */
	LEVEL_3(3L),

	/*
	 * @since v3.10
	 */
	LEVEL_4(4L);

	private final long value;

	private Level(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}