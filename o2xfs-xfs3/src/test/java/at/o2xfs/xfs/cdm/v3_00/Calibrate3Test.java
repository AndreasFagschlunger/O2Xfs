package at.o2xfs.xfs.cdm.v3_00;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.o2xfs.win32.Buffer;
import at.o2xfs.xfs.v3_00.BaseXfs3Test;

public class Calibrate3Test extends BaseXfs3Test {

	@Test
	public final void test() {
		Calibrate3 expected = new Calibrate3(buildCalibrate3().getPointer());
		Calibrate3 actual = new Calibrate3(expected);
		System.out.println(actual);
		assertEquals(expected, actual);
	}

	private native Buffer buildCalibrate3();
}