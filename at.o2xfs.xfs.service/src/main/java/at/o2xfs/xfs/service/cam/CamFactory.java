package at.o2xfs.xfs.service.cam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.ZList;
import at.o2xfs.xfs.XfsVersion;
import at.o2xfs.xfs.cam.CamStatus;

public enum CamFactory {

	INSTANCE;

	private <T> T doCreate(XfsVersion xfsVersion, Pointer p, Class<T> type) {
		Object result;
		if (CamStatus.class.equals(type)) {
			result = createCamStatus(p);
		} else {
			throw new IllegalArgumentException(type.toString());
		}
		return type.cast(result);
	}

	private CamStatus createCamStatus(Pointer p) {
		return new CamStatus(new CamStatus(p));
	}

	public static <T> T create(XfsVersion xfsVersion, Pointer p, Class<T> type) {
		return INSTANCE.doCreate(xfsVersion, p, type);
	}

	public static <T> List<T> fromNullTerminatedArray(XfsVersion xfsVersion, Pointer p, Class<T> type) {
		List<T> result = new ArrayList<>();
		for (Pointer each : new ZList(p)) {
			result.add(create(xfsVersion, each, type));
		}
		return Collections.unmodifiableList(result);
	}
}
