package at.o2xfs.xfs.cam;

import at.o2xfs.xfs.XfsException;

public class CamException
		extends XfsException {

	protected CamException(CamError error) {
		super(error);
	}

	@Override
	public CamError getError() {
		return getError(CamError.class);
	}
}
