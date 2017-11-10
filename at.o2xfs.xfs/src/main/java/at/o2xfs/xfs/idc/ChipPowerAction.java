package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum ChipPowerAction implements XfsConstant {

	/*
	 * @since v3.00
	 */
	COLD(0x0002),

	/*
	 * @since v3.00
	 */
	WARM(0x0004),

	/*
	 * @since v3.00
	 */
	OFF(0x0008);

	private final long value;

	private ChipPowerAction(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}