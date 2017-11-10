package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum DevicePosition implements XfsConstant {

	/*
	 * @since v3.10
	 */
	INPOSITION(0L),

	/*
	 * @since v3.10
	 */
	NOTINPOSITION(1L),

	/*
	 * @since v3.10
	 */
	POSUNKNOWN(2L),

	/*
	 * @since v3.10
	 */
	POSNOTSUPP(3L);

	private final long value;

	private DevicePosition(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}