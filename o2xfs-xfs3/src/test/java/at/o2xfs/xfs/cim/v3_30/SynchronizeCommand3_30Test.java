package at.o2xfs.xfs.cim.v3_30;

import org.junit.Test;

import at.o2xfs.win32.Buffer;
import at.o2xfs.xfs.cim.v3_00.Retract3;
import at.o2xfs.xfs.v3_30.BaseXfs3_30Test;

public class SynchronizeCommand3_30Test extends BaseXfs3_30Test {

	@Test
	public final void test() {
		SynchronizeCommand3_30 expected = new SynchronizeCommand3_30(buildSynchronizeCommand3_30().getPointer());
		System.out.println(expected);
		System.out.println(new Retract3(expected.getCmdData()));
	}

	private native Buffer buildSynchronizeCommand3_30();
}
