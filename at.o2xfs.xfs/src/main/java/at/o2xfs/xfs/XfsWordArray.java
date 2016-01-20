package at.o2xfs.xfs;

import at.o2xfs.win32.Array;
import at.o2xfs.win32.ValueType;

public class XfsWordArray<T extends Enum<T> & XfsConstant>
		extends Array<XfsWord<T>>
		implements ValueType<T[]> {

	private final Class<T> type;

	public XfsWordArray(Class<T> type, int length) {
		super(new XfsWord[length]);
		for (int i = 0; i < array.length; i++) {
			array[i] = new XfsWord<>(type);
		}
		this.type = type;
	}

	public void set(XfsWordArray<T> values) {
		set(values.get());
	}

	@Override
	public T[] get() {
		T[] result = (T[]) java.lang.reflect.Array.newInstance(type, length);
		for (int i = 0; i < length; i++) {
			result[i] = get(i).get();
		}
		return result;
	}

	@Override
	public void set(T[] value) {
		for (int i = 0; i < value.length; i++) {
			get(i).set(value[i]);
		}
	}
}