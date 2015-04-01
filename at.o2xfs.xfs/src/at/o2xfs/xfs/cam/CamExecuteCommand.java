package at.o2xfs.xfs.cam;

import at.o2xfs.xfs.XfsConstant;

public enum CamExecuteCommand implements XfsConstant {

	TAKE_PICTURE(1001L),
	RESET(1002L),
	TAKE_PICTURE_EX(1003L);

	private final long value;

	private CamExecuteCommand(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}
}