package at.o2xfs.xfs.pin;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class WfsPINEntryTest {

	@Test
	public final void testCopy() {
		final WfsPINEntry pinEntry = new WfsPINEntry();
		pinEntry.allocate();
		pinEntry.setDigits(4);
		pinEntry.setCompletion(PINCompletion.ENTER);
		final WfsPINEntry copy = new WfsPINEntry(pinEntry);
		assertEquals(pinEntry, copy);
		assertEquals(pinEntry.getDigits(), copy.getDigits());
		assertEquals(pinEntry.getCompletion(), copy.getCompletion());
	}

}
