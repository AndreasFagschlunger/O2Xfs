package at.o2xfs.xfs.cam;

import at.o2xfs.xfs.XfsConstant;

public enum CamEventId implements XfsConstant {

	MEDIATHRESHOLD(1001L),
	INVALIDDATA(1002L);

	private final long value;

	private CamEventId(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}