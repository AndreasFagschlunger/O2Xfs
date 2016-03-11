package at.o2xfs.xfs.cdm.v3_30;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.o2xfs.win32.Buffer;
import at.o2xfs.xfs.v3_30.BaseXfs3_30Test;

public class AllItemsInfo3_30Test extends BaseXfs3_30Test {

	@Test
	public final void test() {
		AllItemsInfo3_30 expected = new AllItemsInfo3_30(buildAllItemsInfo3_30().getPointer());
		AllItemsInfo3_30 actual = new AllItemsInfo3_30(expected);
		System.out.println(actual);
		assertEquals(expected, actual);
	}

	private native Buffer buildAllItemsInfo3_30();
}