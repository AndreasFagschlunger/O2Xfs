package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum FormAction implements XfsConstant {

	/*
	 * @since v3.00
	 */
	READ(0x0001),

	/*
	 * @since v3.00
	 */
	WRITE(0x0002);

	private final long value;

	private FormAction(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}