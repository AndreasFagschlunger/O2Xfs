package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum Track implements XfsConstant {

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
	TRACK3_JIS1(0x0800);

	private final long value;

	private Track(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}