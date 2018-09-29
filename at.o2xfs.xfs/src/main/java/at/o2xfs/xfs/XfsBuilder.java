package at.o2xfs.xfs;

import at.o2xfs.win32.BufferFactory;
import at.o2xfs.win32.Type;

public interface XfsBuilder<T extends Type> {

	T build(BufferFactory factory);
}
