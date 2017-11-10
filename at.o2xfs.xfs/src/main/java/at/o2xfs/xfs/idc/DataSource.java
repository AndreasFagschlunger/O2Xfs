package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum DataSource implements XfsConstant {
	/*
	 * @since v3.00
	 */
	NOTSUPP(0x0000),

	/*
	 * @since v3.00
	 */
	TRACK1(0x0001),

	/*
	 * @since v3.00
	 */
	TRACK2(0x0002),

	/*
	 * @since v3.00
	 */
	TRACK3(0x0004),

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
	 * @since v3.10
	 */
	FRONT_TRACK_1(0x0080),

	/*
	 * @since v3.20
	 */
	TRACK1_JIS1(0x0400),

	/*
	 * @since v3.20
	 */
	TRACK3_JIS1(0x0800),

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