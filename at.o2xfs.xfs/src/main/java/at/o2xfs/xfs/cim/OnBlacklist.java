package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum OnBlacklist implements XfsConstant {

	/*
	 * @since v3.30
	 */
	ONBLACKLIST(0x0001),

	/*
	 * @since v3.30
	 */
	NOTONBLACKLIST(0x0002),

	/*
	 * @since v3.30
	 */
	BLACKLISTUNKNOWN(0x0003);

	private final long value;

	private OnBlacklist(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}