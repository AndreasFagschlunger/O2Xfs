package at.o2xfs.xfs.win32;

import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Type;
import at.o2xfs.win32.ValueType;

public abstract class XfsPointerArray<T extends Type> extends Type implements ValueType<T[]> {

	protected final Pointer[] pointers;

	public XfsPointerArray(T[] array) {
		pointers = new Pointer[array.length];
		allocate();
		for (int i = 0; i < pointers.length; i++) {
			pointers[i] = new Pointer(getBuffer().subBuffer(i * Pointer.NULL.getSize(), Pointer.NULL.getSize()));
			pointers[i].pointTo(array[i]);
		}
	}

	public XfsPointerArray(Pointer p, int length) {
		pointers = new Pointer[length];
		assignBuffer(p.buffer(getSize()));
		for (int i = 0; i < pointers.length; i++) {
			pointers[i] = new Pointer(getBuffer().subBuffer(i * Pointer.NULL.getSize(), Pointer.NULL.getSize()));
		}
	}

	@Override
	public int getSize() {
		return (Pointer.NULL.getSize() * pointers.length);
	}

	abstract public T copy(T copy);

	@Override
	public void set(T[] value) {
		if (pointers.length != value.length) {
			throw new IllegalArgumentException("Illegal length: " + value.length);
		}
		for (int i = 0; i < pointers.length; i++) {
			pointers[i].pointTo(copy(value[i]));
		}
	}
}