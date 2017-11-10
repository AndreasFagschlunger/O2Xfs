package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum CVM implements XfsConstant {

	/*
	 * @since v3.30
	 */
	ONLINEPIN(0L),

	/*
	 * @since v3.30
	 */
	CONFIRMATIONCODEVERIFIED(1L),

	/*
	 * @since v3.30
	 */
	SIGN(2L),

	/*
	 * @since v3.30
	 */
	NOCVM(3L),

	/*
	 * @since v3.30
	 */
	NOCVMPREFERENCE(4L);

	private final long value;

	private CVM(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}