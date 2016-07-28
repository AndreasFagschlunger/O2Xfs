package at.o2xfs.xfs.cim.v3_30;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.o2xfs.win32.Buffer;
import at.o2xfs.xfs.v3_30.BaseXfs3_30Test;

public class ShutterStatusChanged3_30Test extends BaseXfs3_30Test {

	@Test
	public final void test() {
		ShutterStatusChanged3_30 expected = new ShutterStatusChanged3_30(buildShutterStatusChanged3_30().getPointer());
		ShutterStatusChanged3_30 actual = new ShutterStatusChanged3_30(expected);
		System.out.println(actual);
		assertEquals(expected, actual);
	}

	private native Buffer buildShutterStatusChanged3_30();
}
