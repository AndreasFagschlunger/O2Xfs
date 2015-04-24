package at.o2xfs.xfs.cam;

import at.o2xfs.xfs.XfsConstant;

public enum CamInfoCommand implements XfsConstant {

	STATUS(0L),

	CAPABILITIES(1L);

	private final long value;

	private CamInfoCommand(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}