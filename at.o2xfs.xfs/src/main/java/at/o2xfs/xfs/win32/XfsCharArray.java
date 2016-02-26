package at.o2xfs.xfs.win32;

import at.o2xfs.win32.Type;
import at.o2xfs.win32.ValueType;

public class XfsCharArray extends Type implements ValueType<char[]> {

	private final int length;

	public XfsCharArray(int length) {
		this.length = length;
	}

	@Override
	public int getSize() {
		return length;
	}

	@Override
	public void set(char[] value) {
		byte[] bytes = new byte[length];
		for (int i = 0; i < length; i++) {
			bytes[i] = (byte) value[i];
		}
		put(bytes);
	}

	@Override
	public char[] get() {
		byte[] bytes = getBytes();
		char[] result = new char[length];
		for (int i = 0; i < length; i++) {
			result[i] = (char) bytes[i];
		}
		return result;
	}
}