package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum AlternateInterface implements XfsConstant {

	/*
	 * @since v3.30
	 */
	CONTACT(0L),

	/*
	 * @since v3.30
	 */
	MAGNETICSTRIPE(1L);

	private final long value;

	private AlternateInterface(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}