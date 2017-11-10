package at.o2xfs.xfs.win32;

import static org.junit.Assert.assertTrue;

import java.util.EnumSet;

import org.junit.Test;

import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.XfsConstant;

public class XfsWordBitmaskTest {

	private enum MockEnum implements XfsConstant {

		MIN_VALUE(1L),
		MAX_VALUE(USHORT.MAX_VALUE);

		private long value;

		private MockEnum(long value) {
			this.value = value;
		}

		@Override
		public long getValue() {
			return value;
		}
	}

	@Test
	public void test() {
		XfsWordBitmask<MockEnum> bitmask = new XfsWordBitmask<>(MockEnum.class, EnumSet.allOf(MockEnum.class));
		assertTrue(bitmask.get().containsAll(EnumSet.allOf(MockEnum.class)));
	}
}
