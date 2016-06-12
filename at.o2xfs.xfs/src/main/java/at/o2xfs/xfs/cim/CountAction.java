package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum CountAction implements XfsConstant {

	/*
	 * @since v3.20
	 */
	NOTSUPP(0x0000),

	/*
	 * @since v3.20
	 */
	INDIVIDUAL(0x0001),

	/*
	 * @since v3.20
	 */
	ALL(0x0002);

	private final long value;

	private CountAction(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}