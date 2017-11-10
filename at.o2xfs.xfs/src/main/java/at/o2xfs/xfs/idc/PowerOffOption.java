package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum PowerOffOption implements XfsConstant {

	/*
	 * @since v3.00
	 */
	NOACTION(1L),

	/*
	 * @since v3.00
	 */
	EJECT(2L),

	/*
	 * @since v3.00
	 */
	RETAIN(3L),

	/*
	 * @since v3.00
	 */
	EJECTTHENRETAIN(4L),

	/*
	 * @since v3.00
	 */
	READPOSITION(5L);

	private final long value;

	private PowerOffOption(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}