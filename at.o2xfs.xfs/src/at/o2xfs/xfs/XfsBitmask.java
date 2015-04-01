package at.o2xfs.xfs;

import at.o2xfs.win32.BitConverter;
import at.o2xfs.win32.NumberType;

import java.util.EnumSet;
import java.util.Set;

public class XfsBitmask<T extends Enum<T> & XfsConstant>
		extends NumberType<Set<T>> {

	private final Class<T> type;

	public XfsBitmask(Class<T> aType) {
		super(2);
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
