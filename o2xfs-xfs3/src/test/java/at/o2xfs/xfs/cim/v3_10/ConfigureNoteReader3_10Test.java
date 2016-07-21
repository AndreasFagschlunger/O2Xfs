package at.o2xfs.xfs.cim.v3_10;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.o2xfs.win32.Buffer;
import at.o2xfs.xfs.v3_10.BaseXfs3_10Test;

public class ConfigureNoteReader3_10Test extends BaseXfs3_10Test {

	@Test
	public final void test() {
		ConfigureNoteReader3_10 expected = new ConfigureNoteReader3_10(buildConfigureNoteReader3_10().getPointer());
		ConfigureNoteReader3_10 actual = new ConfigureNoteReader3_10(expected);
		System.out.println(actual);
		assertEquals(expected, actual);
	}

	private native Buffer buildConfigureNoteReader3_10();
}
