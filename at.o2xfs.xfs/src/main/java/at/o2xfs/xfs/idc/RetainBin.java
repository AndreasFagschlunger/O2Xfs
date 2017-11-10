package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum RetainBin implements XfsConstant {

	/*
	 * @since v3.00
	 */
	BINOK(1L),

	/*
	 * @since v3.00
	 */
	NOTSUPP(2L),

	/*
	 * @since v3.00
	 */
	BINFULL(3L),

	/*
	 * @since v3.00
	 */
	BINHIGH(4L),

	/*
	 * @since v3.10
	 */
	BINMISSING(5L);

	private final long value;

	private RetainBin(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}