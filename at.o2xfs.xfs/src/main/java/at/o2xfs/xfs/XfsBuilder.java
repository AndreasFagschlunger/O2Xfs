package at.o2xfs.xfs;

import at.o2xfs.win32.BufferFactory;

public interface XfsBuilder<T> {

	T build(BufferFactory factory);
}
