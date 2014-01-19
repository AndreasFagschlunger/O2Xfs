package at.o2xfs.win32;

public class NumberType extends BaseType {

	protected NumberType(int size) {
		super(size);
	}

	public short shortValue() {
		return (short) longValue();
	}

	public int intValue() {
		return (int) longValue();
	}

	public long longValue() {
		return BitConverter.toLong(get());
	}
}