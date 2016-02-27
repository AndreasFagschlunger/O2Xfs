package at.o2xfs.xfs.win32;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import at.o2xfs.win32.DWORD;
import at.o2xfs.win32.DWORDArray;
import at.o2xfs.win32.ValueType;
import at.o2xfs.xfs.XfsConstant;

public class XfsBitmaskArray<T extends Enum<T> & XfsConstant> extends DWORDArray implements ValueType<List<Set<T>>> {

	private final Class<T> type;

	public XfsBitmaskArray(int length, Class<T> type) {
		super(length);
		this.type = type;
	}

	@Override
	public void set(List<Set<T>> value) {
		for (int i = 0; i < array.length; i++) {
			long l = 0L;
			for (T each : value.get(i)) {
				l |= each.getValue();
			}
			array[i].set(l);
		}
	}

	@Override
	public List<Set<T>> get() {
		List<Set<T>> result = new ArrayList<>();
		for (DWORD each : array) {
			result.add(get(each.longValue()));
		}
		return result;
	}

	private Set<T> get(long value) {
		EnumSet<T> result = EnumSet.noneOf(type);
		for (T each : type.getEnumConstants()) {
			if (each.getValue() == 0L) {
				continue;
			}
			if ((value & each.getValue()) == each.getValue()) {
				result.add(each);
			}
		}
		return result;
	}
}