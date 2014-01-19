package at.o2xfs.win32;

import org.junit.Assert;
import org.junit.Test;

public class ZSTRTest {

	@Test
	public final void helloWorld() {
		final String expected = "Hello World!";
		ZSTR zString = new ZSTR(expected);
		Assert.assertEquals(expected, zString.toString());
	}
}