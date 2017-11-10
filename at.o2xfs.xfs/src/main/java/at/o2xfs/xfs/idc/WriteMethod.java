package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum WriteMethod implements XfsConstant {

	/*
	 * @since v3.00
	 */
	UNKNOWN(0x0001),

	/*
	 * @since v3.00
	 */
	LOCO(0x0002),

	/*
	 * @since v3.00
	 */
	HICO(0x0004),

	/*
	 * @since v3.00
	 */
	AUTO(0x0008);

	private final long value;

	private WriteMethod(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}