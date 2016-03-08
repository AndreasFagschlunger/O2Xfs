package at.o2xfs.xfs.cdm.v3_00;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.o2xfs.win32.Buffer;
import at.o2xfs.xfs.v3_00.BaseXfs3Test;

public class StartEx3Test extends BaseXfs3Test {

	@Test
	public final void test() {
		StartEx3 expected = new StartEx3(buildStartEx3().getPointer());
		StartEx3 actual = new StartEx3(expected);
		System.out.println(actual);
		assertEquals(expected, actual);
	}

	private native Buffer buildStartEx3();
}