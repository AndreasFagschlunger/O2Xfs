package at.o2xfs.emv.icc;

import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;
import at.o2xfs.emv.SelectCommand;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class T0ProtocolTest {

	private static final int SW_SUCCESS = 0x9000;
	private static final byte[] T0_ATR = Hex.decode("3B600000");

	@Test
	public void testCase1() throws IOException {
		TestReader reader = new TestReader(T0_ATR);
		CAPDU capdu = new CAPDU(0x00, 0xE0, 0x00, 0x00);
		RAPDU rapdu = reader.expect("00E0000000", "9000").transmit(capdu);
		Assert.assertEquals(SW_SUCCESS, rapdu.getSW());
	}

	@Test
	public void testCase2LeAccepted() throws IOException {
		TestReader reader = new TestReader(T0_ATR);
		CAPDU capdu = new CAPDU(0x80, 0xCA, 0x9F, 0x17, 0x01);
		RAPDU rapdu = reader.expect("80CA9F1701", "039000").transmit(capdu);
		Assert.assertEquals("03", Hex.encode(rapdu.getData()));
		Assert.assertEquals(SW_SUCCESS, rapdu.getSW());
	}

	@Test
	public void testCase2LeNotAcceptedLaIndicated() throws IOException {
		TestReader reader = new TestReader(T0_ATR);
		CAPDU capdu = new CAPDU(0x80, 0xCA, 0x9F, 0x17, 0x00);
		RAPDU rapdu = reader.expect("80CA9F1700", "6C01").expect("80CA9F1701", "039000").transmit(capdu);
		Assert.assertEquals("03", Hex.encode(rapdu.getData()));
		Assert.assertEquals(SW_SUCCESS, rapdu.getSW());
	}

	@Test
	public void testCase2LeNotAcceptedLaIndicatedAndLaGreaterThatLe() throws IOException {
		TestReader reader = new TestReader(T0_ATR);
		CAPDU capdu = new CAPDU(0x80, 0xCA, 0x9F, 0x17, 0x00);
		RAPDU rapdu = reader.expect("80CA9F1700", "6C01").expect("80CA9F1701", "03009000").transmit(capdu);
		Assert.assertEquals("03", Hex.encode(rapdu.getData()));
		Assert.assertEquals(SW_SUCCESS, rapdu.getSW());
	}

	@Test
	public void testCase3() throws IOException {
		TestReader reader = new TestReader(T0_ATR);
		CAPDU capdu = new ExternalAuthenticate(Hex.decode("123456"));
		RAPDU rapdu = reader.expect("0082000003123456", "9000").transmit(capdu);
		Assert.assertArrayEquals(Bytes.EMPTY, rapdu.getData());
		Assert.assertEquals(SW_SUCCESS, rapdu.getSW());
	}

	@Test
	public void testCase4CommandAccepted() throws IOException {
		TestReader reader = new TestReader(T0_ATR);
		CAPDU capdu = SelectCommand.selectFirstByName(Hex.decode("123456"));
		RAPDU rapdu = reader.expect("00A4040003123456", "9000").expect("00C0000000", "CAFEBABE9000").transmit(capdu);
		Assert.assertArrayEquals(Hex.decode("CAFEBABE"), rapdu.getData());
		Assert.assertEquals(SW_SUCCESS, rapdu.getSW());
	}

	@Test
	public void testCase4CommandAcceptedWithInformationAdded() throws IOException {
		TestReader reader = new TestReader(T0_ATR);
		CAPDU capdu = new CAPDU(0x00, 0xA4, 0x04, 0x00, Hex.decode("123456"), 5);
		RAPDU rapdu = reader.expect("00A4040003123456", "6104").expect("00C0000004", "CAFEBABE9000").transmit(capdu);
		Assert.assertArrayEquals(Hex.decode("CAFEBABE"), rapdu.getData());
		Assert.assertEquals(SW_SUCCESS, rapdu.getSW());
	}
}