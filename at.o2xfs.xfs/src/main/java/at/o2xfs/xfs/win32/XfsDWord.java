package at.o2xfs.xfs.win32;

import at.o2xfs.win32.BitConverter;
import at.o2xfs.win32.NumberType;
import at.o2xfs.xfs.XfsConstant;
import at.o2xfs.xfs.util.XfsConstants;

public class XfsDWord<T extends Enum<T> & XfsConstant> extends NumberType<T> {

	private final Class<T> elementType;

	public XfsDWord(Class<T> elementType) {
		super(1 << 2);
		this.elementType = elementType;
	}

	@Override
	public void set(T value) {
		put(BitConverter.getBytes(getSize(), value.getValue()));
	}

	@Override
	public T get() {
		return XfsConstants.valueOf(longValue(), elementType);
	}
}