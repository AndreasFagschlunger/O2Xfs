package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum IFMAuthority implements XfsConstant {

	/*
	 * @since v3.10
	 */
	EMV(1L),

	/*
	 * @since v3.10
	 */
	EUROPAY(2L),

	/*
	 * @since v3.10
	 */
	VISA(3L),

	/*
	 * @since v3.10
	 */
	GIECB(4L);

	private final long value;

	private IFMAuthority(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}