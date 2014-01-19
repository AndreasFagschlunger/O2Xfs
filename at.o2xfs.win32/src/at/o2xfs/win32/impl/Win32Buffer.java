package at.o2xfs.win32.impl;

import java.util.Arrays;

import at.o2xfs.win32.Buffer;
import at.o2xfs.win32.BufferOverflowException;

public final class Win32Buffer extends Buffer {

	static {
		System.loadLibrary("O2XfsWIN32");
	}

	private static final byte[] NULL = new byte[4];

	public Win32Buffer(int size) {
		super(size);
	}

	public Win32Buffer(byte[] address, int size) {
		super(address, size);
	}

	@Override
	protected byte[] allocate() {
		return allocate0(getSize());
	}

	private native byte[] allocate0(int size);

	@Override
	public byte[] get() {
		byte[] address = getAddress();
		if (Arrays.equals(NULL, address)) {
			return null;
		}
		return get0(getAddress(), getSize());
	}

	private native byte[] get0(byte[] address, int length);

	@Override
	public void put(byte[] src) {
		if (src.length > getSize()) {
			throw new BufferOverflowException();
		} else if (getAddress() == NULL) {
			throw new NullPointerException("address is 0");
		}
		put0(getAddress(), src);
	}

	private native void put0(byte[] address, byte[] src);

	@Override
	public Buffer subBuffer(int index, int size) {
		if (index < 0) {
			throw new IllegalArgumentException("index must not be negative: "
					+ index);
		} else if (size < 0) {
			throw new IllegalArgumentException("size must not be negative: "
					+ size);
		} else if (index + size > getSize()) {
			throw new BufferOverflowException();
		}
		return subBuffer0(getAddress(), index, size);
	}

	private native Buffer subBuffer0(byte[] address, int index, int size);

	protected void _free() {
		free0(getAddress());
	}

	private native void free0(byte[] address);
}