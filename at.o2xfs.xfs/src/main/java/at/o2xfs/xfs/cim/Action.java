package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum Action implements XfsConstant {

	/*
	 * @since v3.00
	 */
	CREATE_TELLER(1L),

	/*
	 * @since v3.00
	 */
	MODIFY_TELLER(2L),

	/*
	 * @since v3.00
	 */
	DELETE_TELLER(3L);

	private final long value;

	private Action(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}