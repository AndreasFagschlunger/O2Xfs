package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum CashInLimit implements XfsConstant {

	/*
	 * @since v3.20
	 */
	NOTSUPP(0x0000),

	/*
	 * @since v3.20
	 */
	BYTOTALITEMS(0x0001),

	/*
	 * @since v3.20
	 */
	BYAMOUNT(0x0002);

	private final long value;

	private CashInLimit(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}