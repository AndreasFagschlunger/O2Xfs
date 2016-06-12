package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum GuidLights implements XfsConstant {

	/*
	 * @since v3.10
	 */
	NOT_AVAILABLE(0x00000000),

	/*
	 * @since v3.10
	 */
	OFF(0x00000001),

	/*
	 * @since v3.10
	 */
	SLOW_FLASH(0x00000004),

	/*
	 * @since v3.10
	 */
	MEDIUM_FLASH(0x00000008),

	/*
	 * @since v3.10
	 */
	QUICK_FLASH(0x00000010),

	/*
	 * @since v3.10
	 */
	CONTINUOUS(0x00000080),

	/*
	 * @since v3.10
	 */
	RED(0x00000100),

	/*
	 * @since v3.10
	 */
	GREEN(0x00000200),

	/*
	 * @since v3.10
	 */
	YELLOW(0x00000400),

	/*
	 * @since v3.10
	 */
	BLUE(0x00000800),

	/*
	 * @since v3.10
	 */
	CYAN(0x00001000),

	/*
	 * @since v3.10
	 */
	MAGENTA(0x00002000),

	/*
	 * @since v3.10
	 */
	WHITE(0x00004000),

	/*
	 * @since v3.30
	 */
	ENTRY(0x00100000),

	/*
	 * @since v3.30
	 */
	EXIT(0x00200000);

	private final long value;

	private GuidLights(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}