package at.o2xfs.xfs.cam;

import at.o2xfs.xfs.XfsConstant;

public enum Camera implements XfsConstant {

	ROOM(0L),
	PERSON(1L),
	EXITSLOT(2L);

	private final long value;

	private Camera(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}
