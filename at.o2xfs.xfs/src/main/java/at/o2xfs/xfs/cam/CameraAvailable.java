package at.o2xfs.xfs.cam;

import at.o2xfs.xfs.XfsConstant;

public enum CameraAvailable implements XfsConstant {

	NOT_AVAILABLE(0L),
	AVAILABLE(1L);

	private final long value;

	private CameraAvailable(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
