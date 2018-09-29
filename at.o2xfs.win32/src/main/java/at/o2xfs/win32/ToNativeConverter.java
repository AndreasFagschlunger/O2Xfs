package at.o2xfs.win32;

public interface ToNativeConverter<T> {

	Type toNative(T value, BufferFactory bufferFactory);
}
