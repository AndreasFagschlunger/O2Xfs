package at.o2xfs.win32;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class UShortArrayTest {

	@Test
	public final void testEmptyArray() {
		UShortArray array = new UShortArray(Pointer.NULL, 0);
		System.out.println(array);
		assertArrayEquals(new int[0], array.get());
	}
}
