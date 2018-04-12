package at.o2xfs.xfs.win32;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import at.o2xfs.win32.Pointer;

public class XfsUIntArrayTest {

	@Test
	public final void testEmptyArray() {
		XfsUIntArray array = new XfsUIntArray(Pointer.NULL, 0);
		System.out.println(array);
		assertArrayEquals(new long[0], array.get());
	}

}
