package at.o2xfs.xfs.cdm.v3_00;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.o2xfs.win32.Buffer;
import at.o2xfs.xfs.v3_00.BaseXfs3Test;

public class Count3Test extends BaseXfs3Test {

	@Test
	public final void test() {
		Count3 expected = new Count3(buildCount3().getPointer());
		Count3 actual = new Count3(expected);
		System.out.println(actual);
		assertEquals(expected, actual);
	}

	private native Buffer buildCount3();
}