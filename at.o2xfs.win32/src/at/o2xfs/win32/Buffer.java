package at.o2xfs.win32;

import org.apache.commons.lang.builder.ToStringBuilder;

import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;

public abstract class Buffer {

	private final boolean allocated;

	private byte[] address;

	private final int size;

	protected Buffer(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("size must not be negative: "
					+ size);
		}
		this.size = size;
		address = allocate();
		allocated = true;
	}

	protected Buffer(byte[] address, int size) {
		if (size < 0) {
			throw new IllegalArgumentException("size must not be negative: "
					+ size);
		}
		this.size = size;
		this.address = Bytes.copy(address);
		allocated = false;
	}

	protected final int getSize() {
		return size;
	}

	protected final byte[] getAddress() {
		return Bytes.copy(address);
	}

	protected abstract byte[] allocate();

	public abstract byte[] get();

	public abstract void put(byte[] src);

	public abstract Buffer subBuffer(int index, int size);

	public final void free() {
		if (address != null) {
			if (allocated) {
				_free();
			}
			address = null;
		}
	}

	protected abstract void _free();

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("size", size)
				.append("address", Hex.encode(address)).toString();
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			free();
		} finally {
			super.finalize();
		}
	}
}