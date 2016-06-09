package at.o2xfs.xfs.cim;

import at.o2xfs.xfs.XfsConstant;

public enum BunchesRemaining implements XfsConstant {

	/*
	 * @since v3.10
	 */
	WFS_CIM_NUMBERUNKNOWN(255L);

	private final long value;

	private BunchesRemaining(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}