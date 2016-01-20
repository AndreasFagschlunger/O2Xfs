package at.o2xfs.xfs.cam;

import at.o2xfs.xfs.XfsConstant;

public enum CamType implements XfsConstant {
	/**
	 * @since 3.00
	 */
	CAM(1L);

	private final long value;

	private CamType(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
