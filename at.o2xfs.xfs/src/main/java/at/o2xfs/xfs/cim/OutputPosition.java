package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum OutputPosition implements XfsConstant {

	/*
	 * @since v3.00
	 */
	NULL(0x0000),

	/*
	 * @since v3.00
	 */
	LEFT(0x0080),

	/*
	 * @since v3.00
	 */
	RIGHT(0x0100),

	/*
	 * @since v3.00
	 */
	CENTER(0x0200),

	/*
	 * @since v3.00
	 */
	TOP(0x0400),

	/*
	 * @since v3.00
	 */
	BOTTOM(0x0800),

	/*
	 * @since v3.00
	 */
	FRONT(0x1000),

	/*
	 * @since v3.00
	 */
	REAR(0x2000);

	private final long value;

	private OutputPosition(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}