package at.o2xfs.xfs.win32;

import at.o2xfs.xfs.XfsConstant;

public class XfsDWordBitmask<T extends Enum<T> & XfsConstant> extends XfsBitmask<T> {

	public XfsDWordBitmask(Class<T> aType) {
		super(4, aType);
	}
}