package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum ChipModule implements XfsConstant {

	/*
	 * @since v3.10
	 */
	OK(1L),

	/*
	 * @since v3.10
	 */
	INOP(2L),

	/*
	 * @since v3.10
	 */
	UNKNOWN(3L),

	/*
	 * @since v3.10
	 */
	NOTSUPP(4L);

	private final long value;

	private ChipModule(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}