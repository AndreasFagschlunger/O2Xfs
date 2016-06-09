package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum CashInItemType implements XfsConstant {

	/*
	 * @since v3.02
	 */
	ALL(0x0001),

	/*
	 * @since v3.02
	 */
	UNFIT(0x0002),

	/*
	 * @since v3.02
	 */
	INDIVIDUAL(0x0004),

	/*
	 * @since v3.02
	 */
	LEVEL3(0x0008),

	/*
	 * @since v3.02
	 */
	LEVEL2(0x0010),

	/*
	 * @since v3.20
	 */
	IPM(0x0020);

	private final long value;

	private CashInItemType(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}