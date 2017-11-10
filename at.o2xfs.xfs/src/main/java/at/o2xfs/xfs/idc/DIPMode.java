package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum DIPMode implements XfsConstant {

	/*
	 * @since v3.10
	 */
	UNKNOWN(0x0001),

	/*
	 * @since v3.10
	 */
	EXIT(0x0002),

	/*
	 * @since v3.10
	 */
	ENTRY(0x0004),

	/*
	 * @since v3.10
	 */
	ENTRY_EXIT(0x0008);

	private final long value;

	private DIPMode(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}