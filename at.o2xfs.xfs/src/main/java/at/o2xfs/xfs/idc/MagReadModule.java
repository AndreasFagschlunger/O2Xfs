package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum MagReadModule implements XfsConstant {

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

	private MagReadModule(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}