package at.o2xfs.win32.impl;

import at.o2xfs.win32.Buffer;
import at.o2xfs.win32.BufferFactory;

public class Win32BufferFactory extends BufferFactory {

	@Override
	public Buffer createBuffer(int size) {
		return new Win32Buffer(size);
	}

	@Override
	public Buffer createBuffer(byte[] address, int size) {
		return new Win32Buffer(address, size);
	}
}