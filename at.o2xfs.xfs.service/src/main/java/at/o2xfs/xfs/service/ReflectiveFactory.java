package at.o2xfs.xfs.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.XfsVersion;

public enum ReflectiveFactory {

	INSTANCE;

	private <R extends Struct> R doCreate(XfsVersion version, Pointer buffer, Class<R> type) {
		return createResult(buffer, resultClass(version, type));
	}

	private <R extends Struct> R createResult(Pointer buffer, Class<R> resultClass) {
		R result = null;
		try {
			Constructor<R> constructor = resultClass.getConstructor(Pointer.class);
			Constructor<R> copyConstructor = resultClass.getConstructor(resultClass);
			result = copyConstructor.newInstance(constructor.newInstance(buffer));
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	private <R extends Struct> Class<R> resultClass(XfsVersion version, Class<R> type) {
		Optional<Class<R>> resultClass = Optional.empty();
		if (version.compareTo(XfsVersion.V3_30) >= 0) {
			resultClass = findClass(XfsVersion.V3_30, type);
		}
		if (!resultClass.isPresent() && version.compareTo(XfsVersion.V3_20) >= 0) {
			resultClass = findClass(XfsVersion.V3_20, type);
		}
		if (!resultClass.isPresent() && version.compareTo(XfsVersion.V3_10) >= 0) {
			resultClass = findClass(XfsVersion.V3_10, type);
		}
		if (!resultClass.isPresent()) {
			resultClass = Optional.of(type);
		}
		return resultClass.get();
	}

	@SuppressWarnings("unchecked")
	private <R extends Struct> Optional<Class<R>> findClass(XfsVersion version, Class<R> type) {
		Optional<Class<R>> result = Optional.empty();
		try {
			result = Optional.of((Class<R>) Class.forName(className(version, type)));
		} catch (ClassNotFoundException e) {
		}
		return result;
	}

	private <R extends Struct> String className(XfsVersion version, Class<R> type) {
		StringBuilder builder = new StringBuilder().append("at.o2xfs.xfs.").append(XfsServiceClass.CDM.getName().toLowerCase()).append('.').append('v')
				.append(version.getMajorVersion()).append('_').append(String.format("%02d", version.getMinorVersion())).append('.')
				.append(type.getSimpleName().replaceFirst("3(_[0-9]{2})?$", "3"));
		if (XfsVersion.V3_10.compareTo(version) >= 0) {
			builder.append('_').append(String.format("%02d", version.getMinorVersion()));
		}
		return builder.toString();
	}

	public static <R extends Struct> R create(XfsVersion version, Pointer p, Class<R> type) {
		return INSTANCE.doCreate(version, p, type);
	}
}