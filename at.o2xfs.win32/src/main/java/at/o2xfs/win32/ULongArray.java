package at.o2xfs.win32;

public class ULongArray extends Array<ULONG> implements ValueType<long[]> {

	private ULongArray(int length) {
		super(new ULONG[length]);
		for (int i = 0; i < array.length; i++) {
			array[i] = new ULONG();
		}
	}

	public ULongArray(long[] aArray) {
		this(aArray.length);
		allocate();
		set(aArray);
	}

	public ULongArray(Pointer pointer, int length) {
		this(length);
		assignBuffer(pointer);
	}

	@Override
	public void set(long[] value) {
		for (int i = 0; i < array.length; i++) {
			array[i].set(value[i]);
		}
	}

	@Override
	public long[] get() {
		long[] result = new long[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i].longValue();
		}
		return result;
	}
}