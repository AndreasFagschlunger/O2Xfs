package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum ValueQualifier implements XfsConstant {

	/*
	 * @since v3.30
	 */
	AMOUNT(0L),

	/*
	 * @since v3.30
	 */
	BALANCE(1L);

	private final long value;

	private ValueQualifier(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}