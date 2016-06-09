package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum BankNoteReader implements XfsConstant {

	/*
	 * @since v3.00
	 */
	OK(0L),

	/*
	 * @since v3.00
	 */
	INOP(1L),

	/*
	 * @since v3.00
	 */
	UNKNOWN(2L),

	/*
	 * @since v3.00
	 */
	NOTSUPPORTED(3L);

	private final long value;

	private BankNoteReader(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}