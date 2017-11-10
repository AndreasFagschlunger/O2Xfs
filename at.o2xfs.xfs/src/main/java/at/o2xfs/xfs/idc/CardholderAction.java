package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum CardholderAction implements XfsConstant {

	/*
	 * @since v3.30
	 */
	NOACTION(0L),

	/*
	 * @since v3.30
	 */
	RETAP(1L),

	/*
	 * @since v3.30
	 */
	HOLDCARD(2L);

	private final long value;

	private CardholderAction(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}