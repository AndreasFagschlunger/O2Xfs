package at.o2xfs.win32;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class ULongArrayTest {

	@Test
	public final void testEmptyArray() {
		ULongArray array = new ULongArray(Pointer.NULL, 0);
		System.out.println(array);
		assertArrayEquals(new long[0], array.get());
	}

}
