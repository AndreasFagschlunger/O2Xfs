package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum AntiFraudModule implements XfsConstant {

	/*
	 * @since v3.20
	 */
	NOTSUPP(0L),

	/*
	 * @since v3.20
	 */
	OK(1L),

	/*
	 * @since v3.20
	 */
	INOP(2L),

	/*
	 * @since v3.20
	 */
	DEVICEDETECTED(3L),

	/*
	 * @since v3.20
	 */
	UNKNOWN(4L);

	private final long value;

	private AntiFraudModule(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}