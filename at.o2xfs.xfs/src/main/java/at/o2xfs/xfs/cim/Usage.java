package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum Usage implements XfsConstant {

	/*
	 * @since v3.10
	 */
	IN(0x0001),

	/*
	 * @since v3.10
	 */
	REFUSE(0x0002),

	/*
	 * @since v3.10
	 */
	ROLLBACK(0x0004);

	private final long value;

	private Usage(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}