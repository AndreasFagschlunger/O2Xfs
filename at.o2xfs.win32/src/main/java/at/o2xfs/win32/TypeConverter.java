package at.o2xfs.win32;

public interface TypeConverter<T> extends ToNativeConverter<T> {

	T fromNative(Pointer pointer);
}
