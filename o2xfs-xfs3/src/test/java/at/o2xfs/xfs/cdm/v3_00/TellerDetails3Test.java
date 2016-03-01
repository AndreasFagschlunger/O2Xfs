package at.o2xfs.xfs.cdm.v3_00;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.o2xfs.win32.Buffer;
import at.o2xfs.xfs.v3_00.BaseXfs3Test;

public class TellerDetails3Test extends BaseXfs3Test {

	@Test
	public final void test() {
		TellerDetails3 expected = new TellerDetails3(buildTellerDetails3().getPointer());
		TellerDetails3 actual = new TellerDetails3(expected);
		System.out.println(actual);
		assertEquals(expected, actual);
	}

	private native Buffer buildTellerDetails3();
}