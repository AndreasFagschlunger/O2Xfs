package at.o2xfs.xfs.pin;

import at.o2xfs.common.Hex;

import org.junit.Test;

public class WfsXDataTest {

	@Test
	public void test() {
		byte[] data = Hex.decode("CAFFEEBABE");
		WfsXData xData = new WfsXData(data);
		System.out.println(xData);
	}

}
