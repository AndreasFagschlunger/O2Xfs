package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum EjectPosition implements XfsConstant {

	/*
	 * @since v3.30
	 */
	EXITPOSITION(0x0001),

	/*
	 * @since v3.30
	 */
	TRANSPORTPOSITION(0x0002);

	private final long value;

	private EjectPosition(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}