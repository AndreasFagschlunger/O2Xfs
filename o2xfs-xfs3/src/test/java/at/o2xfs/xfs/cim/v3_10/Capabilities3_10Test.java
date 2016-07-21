package at.o2xfs.xfs.cim.v3_10;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.o2xfs.win32.Buffer;
import at.o2xfs.xfs.v3_10.BaseXfs3_10Test;

public class Capabilities3_10Test extends BaseXfs3_10Test {

	@Test
	public final void test() {
		Capabilities3_10 expected = new Capabilities3_10(buildCapabilities3_10().getPointer());
		Capabilities3_10 actual = new Capabilities3_10(expected);
		System.out.println(actual);
		assertEquals(expected, actual);
	}

	private native Buffer buildCapabilities3_10();
}
