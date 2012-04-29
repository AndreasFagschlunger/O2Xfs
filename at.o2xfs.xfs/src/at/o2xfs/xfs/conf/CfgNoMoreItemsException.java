package at.o2xfs.xfs.conf;

import at.o2xfs.xfs.XfsError;
import at.o2xfs.xfs.XfsServiceException;

public class CfgNoMoreItemsException extends XfsServiceException {

	public CfgNoMoreItemsException() {
		super(XfsError.WFS_ERR_CFG_NO_MORE_ITEMS);
	}

}
