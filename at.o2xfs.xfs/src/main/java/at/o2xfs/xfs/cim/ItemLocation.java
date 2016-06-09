package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum ItemLocation implements XfsConstant {

	/*
	 * @since v3.30
	 */
	DEVICE(0x0001),

	/*
	 * @since v3.30
	 */
	CASHUNIT(0x0002),

	/*
	 * @since v3.30
	 */
	CUSTOMER(0x0003),

	/*
	 * @since v3.30
	 */
	UNKNOWN(0x0004);

	private final long value;

	private ItemLocation(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}