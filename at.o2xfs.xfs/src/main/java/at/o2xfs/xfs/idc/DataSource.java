package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum DataSource implements XfsConstant {

	/*
	 * @since v3.00
	 */
	CHIP(0x0008),

	/*
	 * @since v3.00
	 */
	SECURITY(0x0010),

	/*
	 * @since v3.00
	 */
	FLUXINACTIVE(0x0020),

	/*
	 * @since v3.00
	 */
	TRACK_WM(0x8000),

	/*
	 * @since v3.10
	 */
	MEMORY_CHIP(0x0040),

	/*
	 * @since v3.10
	 */
	FRONTIMAGE(0x0100),

	/*
	 * @since v3.10
	 */
	BACKIMAGE(0x0200),

	/*
	 * @since v3.20
	 */
	DDI(0x4000);

	private final long value;

	private DataSource(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}