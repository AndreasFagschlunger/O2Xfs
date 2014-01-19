package at.o2xfs.xfs;

import at.o2xfs.win32.WORD;
import at.o2xfs.xfs.util.XfsConstants;

public class XfsWord<T extends Enum<T> & XfsConstant> extends WORD {

	private final Class<T> type;

	public XfsWord(Class<T> type) {
		this.type = type;
	}

	public void set(T value) {
		put(value.getValue());
	}

	public T enumValue() {
		return XfsConstants.valueOf(this, type);
	}
}