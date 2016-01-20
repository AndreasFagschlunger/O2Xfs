package at.o2xfs.win32;

public interface ValueType<T> {

	public void set(T value);

	public T get();
}