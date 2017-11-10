package at.o2xfs.xfs.win32;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.XfsConstant;

public class XfsWordTest {

	private enum MockEnum implements XfsConstant {
		MIN_VALUE(0L),
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
	public void testMaxValue() {
		XfsWord<MockEnum> xfsWord = new XfsWord<>(MockEnum.class);
		xfsWord.allocate();
		xfsWord.set(MockEnum.MAX_VALUE);
		assertEquals(MockEnum.MAX_VALUE, xfsWord.get());
	}

	@Test
	public void testMinValue() {
		XfsWord<MockEnum> xfsWord = new XfsWord<>(MockEnum.class);
		xfsWord.allocate();
		xfsWord.set(MockEnum.MIN_VALUE);
		assertEquals(MockEnum.MIN_VALUE, xfsWord.get());
	}
}
