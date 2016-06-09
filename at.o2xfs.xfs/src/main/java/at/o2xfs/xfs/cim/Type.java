package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum Type implements XfsConstant {

	/*
	 * @since v3.00
	 */
	TELLERBILL(0L),

	/*
	 * @since v3.00
	 */
	SELFSERVICEBILL(1L),

	/*
	 * @since v3.00
	 */
	TELLERCOIN(2L),

	/*
	 * @since v3.00
	 */
	SELFSERVICECOIN(3L);

	private final long value;

	private Type(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}