package at.o2xfs.xfs.cam;

import at.o2xfs.xfs.AbstractXfsExceptionFactory;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.util.XfsConstants;

public class CamExceptionFactory
		extends AbstractXfsExceptionFactory {

	public CamExceptionFactory() {
		super(XfsServiceClass.CAM);
	}

	@Override
	public void throwException(long errorCode) throws XfsException {
		throw new CamException(XfsConstants.valueOf(errorCode, CamError.class));
	}
}