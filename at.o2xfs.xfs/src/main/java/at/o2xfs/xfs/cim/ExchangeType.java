package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum ExchangeType implements XfsConstant {

	/*
	 * @since v3.00
	 */
	EXBYHAND(0x0001),

	/*
	 * @since v3.00
	 */
	EXTOCASSETTES(0x0002),

	/*
	 * @since v3.00
	 */
	CLEARRECYCLER(0x0004),

	/*
	 * @since v3.00
	 */
	DEPOSITINTO(0x0008);

	private final long value;

	private ExchangeType(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}