package at.o2xfs.xfs.cam;

import at.o2xfs.xfs.XfsConstant;

public enum CharSupport implements XfsConstant {
	/**
	 * @since 3.00
	 */
	ASCII(1L),
	/**
	 * @since 3.00
	 */
	UNICODE(2L);

	private final long value;

	private CharSupport(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
