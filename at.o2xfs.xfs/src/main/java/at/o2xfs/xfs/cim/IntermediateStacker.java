package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum IntermediateStacker implements XfsConstant {

	/*
	 * @since v3.00
	 */
	EMPTY(0L),

	/*
	 * @since v3.00
	 */
	NOTEMPTY(1L),

	/*
	 * @since v3.00
	 */
	FULL(2L),

	/*
	 * @since v3.00
	 */
	UNKNOWN(4L),

	/*
	 * @since v3.00
	 */
	NOTSUPPORTED(5L);

	private final long value;

	private IntermediateStacker(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}