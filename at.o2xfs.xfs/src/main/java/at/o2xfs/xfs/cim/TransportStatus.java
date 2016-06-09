package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum TransportStatus implements XfsConstant {

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
	NOTEMPTYCUST(2L),

	/*
	 * @since v3.00
	 */
	NOTEMPTY_UNK(3L),

	/*
	 * @since v3.00
	 */
	NOTSUPPORTED(4L);

	private final long value;

	private TransportStatus(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}