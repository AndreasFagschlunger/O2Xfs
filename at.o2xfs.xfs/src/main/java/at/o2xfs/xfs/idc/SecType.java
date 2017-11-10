package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum SecType implements XfsConstant {

	/*
	 * @since v3.00
	 */
	NOTSUPP(1L),

	/*
	 * @since v3.00
	 */
	MMBOX(2L),

	/*
	 * @since v3.00
	 */
	CIM86(3L);

	private final long value;

	private SecType(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}