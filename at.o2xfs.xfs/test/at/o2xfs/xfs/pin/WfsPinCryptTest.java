package at.o2xfs.xfs.pin;

import at.o2xfs.common.Hex;

import org.junit.Test;

public class WfsPinCryptTest {

	@Test
	public void test() {
		WfsPinCrypt pinCrypt = new WfsPinCrypt();
		pinCrypt.allocate();
		pinCrypt.setMode(PinMode.ENCRYPT);
		pinCrypt.setKey("MAK");
		pinCrypt.setAlgorithm(PINAlgorithm.TRIDESMAC);
		pinCrypt.setStartValue(new byte[8]);
		pinCrypt.setCryptData(Hex.decode("CAFFEE"));
		System.out.println(pinCrypt);
	}

}
