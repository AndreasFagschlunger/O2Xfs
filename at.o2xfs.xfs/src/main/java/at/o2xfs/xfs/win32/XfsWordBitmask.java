package at.o2xfs.xfs.win32;

import at.o2xfs.xfs.XfsConstant;

public class XfsWordBitmask<T extends Enum<T> & XfsConstant> extends XfsBitmask<T> {

	public XfsWordBitmask(Class<T> aType) {
		super(2, aType);
	}
}