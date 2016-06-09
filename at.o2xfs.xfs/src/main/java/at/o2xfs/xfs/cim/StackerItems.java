package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum StackerItems implements XfsConstant {

	/*
	 * @since v3.00
	 */
	CUSTOMERACCESS(0L),

	/*
	 * @since v3.00
	 */
	NOCUSTOMERACCESS(1L),

	/*
	 * @since v3.00
	 */
	ACCESSUNKNOWN(2L),

	/*
	 * @since v3.00
	 */
	NOITEMS(4L);

	private final long value;

	private StackerItems(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}