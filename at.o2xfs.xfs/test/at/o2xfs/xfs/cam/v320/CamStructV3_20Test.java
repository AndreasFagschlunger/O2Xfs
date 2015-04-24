package at.o2xfs.xfs.cam.v320;

import at.o2xfs.win32.Buffer;

import org.junit.Assert;
import org.junit.Test;

public class CamStructV3_20Test {

	static {
		System.loadLibrary("at.o2xfs.win32");
		System.loadLibrary("at.o2xfs.xfs.test");
	}

	@Test
	public void testCamStatusV3_20() {
		Buffer buffer = createCamStatusV3_20();
		CamStatusV3_20 status = new CamStatusV3_20(buffer.getPointer());
		System.out.println(status);
		CamStatusV3_20 copy = new CamStatusV3_20(status);
		Assert.assertEquals(status, copy);
	}

	@Test
	public void testCamCapsV3_20() {
		Buffer buffer = createCamCapsV3_20();
		CamCapsV3_20 capabilities = new CamCapsV3_20(buffer.getPointer());
		System.out.println(capabilities);
		CamCapsV3_20 copy = new CamCapsV3_20(capabilities);
		Assert.assertEquals(capabilities, copy);
	}

	@Test
	public void testTakepictEx() {
		TakepictEx takepictEx = new TakepictEx(createTakepictEx().getPointer());
		System.out.println(takepictEx);
		TakepictEx copy = new TakepictEx(takepictEx);
		Assert.assertEquals(takepictEx, copy);
	}

	private native Buffer createCamStatusV3_20();

	private native Buffer createCamCapsV3_20();

	private native Buffer createTakepictEx();
}
