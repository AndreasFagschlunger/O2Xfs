package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum Acceptor implements XfsConstant {

	/*
	 * @since v3.00
	 */
	OK(0L),

	/*
	 * @since v3.00
	 */
	CUSTATE(1L),

	/*
	 * @since v3.00
	 */
	CUSTOP(2L),

	/*
	 * @since v3.00
	 */
	CUUNKNOWN(3L);

	private final long value;

	private Acceptor(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}