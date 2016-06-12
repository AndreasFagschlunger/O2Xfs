package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum RetractAction implements XfsConstant {

	/*
	 * @since v3.00
	 */
	PRESENT(0x0001),

	/*
	 * @since v3.00
	 */
	RETRACT(0x0002),

	/*
	 * @since v3.00
	 */
	NOTSUPP(0x0004),

	/*
	 * @since v3.10
	 */
	REJECT(0x0008),

	/*
	 * @since v3.20
	 */
	BILLCASSETTES(0x0010),

	/*
	 * @since v3.30
	 */
	CASHIN(0x0020);

	private final long value;

	private RetractAction(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}