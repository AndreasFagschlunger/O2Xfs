package at.o2xfs.xfs.win32;

import at.o2xfs.win32.Array;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.UINT;
import at.o2xfs.win32.ValueType;

public class XfsUIntArray extends Array<UINT> implements ValueType<long[]> {

	public XfsUIntArray(long[] values) {
		super(new UINT[values.length]);
		for (int i = 0; i < array.length; i++) {
			array[i] = new UINT();
		}
		allocate();
		set(values);
	}

	public XfsUIntArray(Pointer pointer, int length) {
		super(new UINT[length]);
		for (int i = 0; i < array.length; i++) {
			array[i] = new UINT();
		}
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