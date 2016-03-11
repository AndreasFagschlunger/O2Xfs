package at.o2xfs.xfs.cdm.v3_30;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.o2xfs.win32.Buffer;
import at.o2xfs.xfs.v3_30.BaseXfs3_30Test;

public class GetAllItemsInfo3_30Test extends BaseXfs3_30Test {

	@Test
	public final void test() {
		GetAllItemsInfo3_30 expected = new GetAllItemsInfo3_30(buildGetAllItemsInfo3_30().getPointer());
		GetAllItemsInfo3_30 actual = new GetAllItemsInfo3_30(expected);
		System.out.println(actual);
		assertEquals(expected, actual);
	}

	private native Buffer buildGetAllItemsInfo3_30();
}
