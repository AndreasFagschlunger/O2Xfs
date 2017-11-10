package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum CardAction implements XfsConstant {

	/*
	 * @since v3.30
	 */
	RETAINED(1L),

	/*
	 * @since v3.30
	 */
	EJECTED(2L),

	/*
	 * @since v3.30
	 */
	READPOSITION(3L),

	/*
	 * @since v3.30
	 */
	JAMMED(4L);

	private final long value;

	private CardAction(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}