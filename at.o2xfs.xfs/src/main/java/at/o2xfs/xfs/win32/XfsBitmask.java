package at.o2xfs.xfs.win32;

import java.util.EnumSet;
import java.util.Set;

import at.o2xfs.win32.BitConverter;
import at.o2xfs.win32.NumberType;
import at.o2xfs.xfs.XfsConstant;

class XfsBitmask<T extends Enum<T> & XfsConstant> extends NumberType<Set<T>> {

	private final Class<T> type;

	protected XfsBitmask(int size, Class<T> aType) {
		super(size);
		type = aType;
	}

	public void set(XfsBitmask<T> value) {
		put(value.getBytes());
	}

	@Override
	public void set(Set<T> value) {
		long l = 0L;
		for (T each : value) {
			l |= each.getValue();
		}
		put(BitConverter.getBytes(getSize(), l));
	}

	@Override
	public Set<T> get() {
		EnumSet<T> result = EnumSet.noneOf(type);
		long value = longValue();
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
