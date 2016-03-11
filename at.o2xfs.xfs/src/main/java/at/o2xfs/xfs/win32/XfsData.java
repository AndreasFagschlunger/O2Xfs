package at.o2xfs.xfs.win32;

import at.o2xfs.win32.BaseType;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.ValueType;

public class XfsData extends BaseType implements ValueType<byte[]> {

	protected XfsData(int length) {
		super(length);
	}

	public XfsData(byte[] value) {
		this(value.length);
		allocate();
		set(value);
	}

	public XfsData(Pointer p, int length) {
		this(length);
		assignBuffer(p.buffer(getSize()));
	}

	@Override
	public void set(byte[] value) {
		getBuffer().put(value);
	}

	@Override
	public byte[] get() {
		return getBytes();
	}
}