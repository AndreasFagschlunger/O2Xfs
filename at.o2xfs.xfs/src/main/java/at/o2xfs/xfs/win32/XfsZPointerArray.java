package at.o2xfs.xfs.win32;

import java.util.ArrayList;
import java.util.List;

import at.o2xfs.win32.Buffer;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Type;
import at.o2xfs.win32.ValueType;

public abstract class XfsZPointerArray<T extends Type> extends Type implements ValueType<T[]> {

	protected final Pointer[] pointers;

	public XfsZPointerArray(T[] array) {
		pointers = new Pointer[array.length];
		allocate();
		int size = Pointer.NULL.getSize();
		for (int i = 0; i < pointers.length; i++) {
			pointers[i] = new Pointer(getBuffer().subBuffer(i * size, size));
			pointers[i].pointTo(array[i]);
		}
	}

	public XfsZPointerArray(Pointer aPointer) {
		int offset = 0;
		Buffer buffer = null;
		List<Pointer> pointers = new ArrayList<>();
		while (true) {
			buffer = aPointer.buffer(offset + aPointer.getSize());
			Pointer p = new Pointer(buffer.subBuffer(offset, aPointer.getSize()));
			if (Pointer.NULL.equals(p)) {
				break;
			}
			pointers.add(p);
			offset += p.getSize();
		}
		this.pointers = pointers.toArray(new Pointer[pointers.size()]);
		assignBuffer(buffer);
	}

	@Override
	public int getSize() {
		return Pointer.NULL.getSize() + (Pointer.NULL.getSize() * pointers.length);
	}

	public abstract T copy(T copy);

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