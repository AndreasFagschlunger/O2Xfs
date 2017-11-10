package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum Security implements XfsConstant {

	/*
	 * @since v3.00
	 */
	NOTSUPP(1L),

	/*
	 * @since v3.00
	 */
	NOTREADY(2L),

	/*
	 * @since v3.00
	 */
	OPEN(3L);

	private final long value;

	private Security(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}