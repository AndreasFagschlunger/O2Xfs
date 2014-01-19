package at.o2xfs.win32;

import at.o2xfs.win32.impl.Win32BufferFactory;

public abstract class BufferFactory {

	private static BufferFactory instance = null;

	public static final BufferFactory getInstance() {
		if (instance == null) {
			instance = new Win32BufferFactory();
		}
		return instance;
	}

	public abstract Buffer createBuffer(int size);

	public abstract Buffer createBuffer(byte[] address, int size);
}