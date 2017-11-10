package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum ClessStatus implements XfsConstant {

	/*
	 * @since v3.30
	 */
	NOT_READY(0L),

	/*
	 * @since v3.30
	 */
	IDLE(1L),

	/*
	 * @since v3.30
	 */
	READYTOREAD(2L),

	/*
	 * @since v3.30
	 */
	PROCESSING(3L),

	/*
	 * @since v3.30
	 */
	CARDREADOK(4L),

	/*
	 * @since v3.30
	 */
	PROCESSINGERROR(5L);

	private final long value;

	private ClessStatus(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}