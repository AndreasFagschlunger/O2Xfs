package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsConstant;

public enum Direction implements XfsConstant {

	/*
	 * @since v3.30
	 */
	IN(0x0001),

	/*
	 * @since v3.30
	 */
	OUT(0x0002);

	private final long value;

	private Direction(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}