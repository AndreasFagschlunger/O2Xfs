package at.o2xfs.win32;

import java.util.Objects;

public class MappedPointer<T> extends Pointer {

	private final TypeConverter<T> converter;

	public MappedPointer(TypeConverter<T> converter) {
		Objects.requireNonNull(converter);
		this.converter = converter;
	}

	public void put(T value, BufferFactory bufferFactory) {
		pointTo(converter.toNative(value, bufferFactory));
	}

	public T get() {
		return converter.fromNative(this);
	}
}
