package at.o2xfs.xfs.cim.v3_30;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.o2xfs.win32.Buffer;
import at.o2xfs.xfs.v3_30.BaseXfs3_30Test;

public class ItemInfo3_30Test extends BaseXfs3_30Test {

	@Test
	public final void test() {
		ItemInfo3_30 expected = new ItemInfo3_30(buildItemInfo3_30().getPointer());
		ItemInfo3_30 actual = new ItemInfo3_30(expected);
		System.out.println(actual);
		assertEquals(expected, actual);
	}

	private native Buffer buildItemInfo3_30();
}
