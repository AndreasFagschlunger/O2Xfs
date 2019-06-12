package at.o2xfs.xfs.service.siu;

import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.siu.SIUPortError;

public enum SiuFactory {

	INSTANCE;

	private <T> T doCreate(XfsVersion xfsVersion, Pointer p, Class<T> type) {
		Object result;
		if (SIUPortError.class.equals(type)) {
			result = createPortError(p);
		} else {
			throw new IllegalArgumentException(type.toString());
		}
		return type.cast(result);
	}

	private SIUPortError createPortError(Pointer p) {
		return new SIUPortError(new SIUPortError(p));
	}

	public static <T> T create(XfsVersion xfsVersion, Pointer p, Class<T> type) {
		return INSTANCE.doCreate(xfsVersion, p, type);
	}
}
