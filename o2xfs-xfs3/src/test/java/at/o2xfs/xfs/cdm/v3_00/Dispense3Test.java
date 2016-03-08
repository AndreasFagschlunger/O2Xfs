package at.o2xfs.xfs.cdm.v3_00;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.o2xfs.win32.Buffer;
import at.o2xfs.xfs.v3_00.BaseXfs3Test;

public class Dispense3Test extends BaseXfs3Test {

	@Test
	public final void test() {
		Dispense3 expected = new Dispense3(buildDispense3().getPointer());
		Dispense3 actual = new Dispense3(expected);
		System.out.println(actual);
		assertEquals(expected, actual);
	}

	private native Buffer buildDispense3();
}