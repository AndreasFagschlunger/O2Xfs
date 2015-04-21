package at.o2xfs.emv.icc;

import at.o2xfs.common.Hex;

import org.junit.Assert;
import org.junit.Test;

public class CAPDUParserTest {

	@Test
	public void testCase1() throws CAPDUParserException {
		byte[] capdu = Hex.decode("20110101");
		CAPDU expected = new CAPDU(0x20, 0x11, 0x01, 0x01);
		CAPDU actual = CAPDUParser.parse(capdu);
		Assert.assertEquals(expected, actual);
		Assert.assertTrue(actual.isCase1());
		Assert.assertFalse(actual.isCase2());
		Assert.assertFalse(actual.isCase3());
		Assert.assertFalse(actual.isCase4());
		Assert.assertEquals(expected.getCla(), actual.getCla());
		Assert.assertEquals(expected.getIns(), actual.getIns());
		Assert.assertEquals(expected.getP1(), actual.getP1());
		Assert.assertEquals(expected.getP2(), actual.getP2());
		Assert.assertArrayEquals(expected.getData(), actual.getData());
		Assert.assertArrayEquals(expected.getLe(), actual.getLe());
	}

	@Test
	public void testCase2() throws CAPDUParserException {
		byte[] capdu = Hex.decode("20110101FF");
		CAPDU expected = new CAPDU(0x20, 0x11, 0x01, 0x01, 0xFF);
		CAPDU actual = CAPDUParser.parse(capdu);
		Assert.assertEquals(expected, actual);
		Assert.assertFalse(actual.isCase1());
		Assert.assertTrue(actual.isCase2());
		Assert.assertFalse(actual.isCase3());
		Assert.assertFalse(actual.isCase4());
		Assert.assertEquals(expected.getCla(), actual.getCla());
		Assert.assertEquals(expected.getIns(), actual.getIns());
		Assert.assertEquals(expected.getP1(), actual.getP1());
		Assert.assertEquals(expected.getP2(), actual.getP2());
		Assert.assertArrayEquals(expected.getData(), actual.getData());
		Assert.assertArrayEquals(expected.getLe(), actual.getLe());
	}

	@Test
	public void testCase3() throws CAPDUParserException {
		byte[] capdu = Hex.decode("0082000003123456");
		CAPDU expected = new CAPDU(0x00, 0x82, 0x00, 0x00, Hex.decode("123456"));
		CAPDU actual = CAPDUParser.parse(capdu);
		Assert.assertEquals(expected, actual);
		Assert.assertFalse(actual.isCase1());
		Assert.assertFalse(actual.isCase2());
		Assert.assertTrue(actual.isCase3());
		Assert.assertFalse(actual.isCase4());
		Assert.assertEquals(expected.getCla(), actual.getCla());
		Assert.assertEquals(expected.getIns(), actual.getIns());
		Assert.assertEquals(expected.getP1(), actual.getP1());
		Assert.assertEquals(expected.getP2(), actual.getP2());
		Assert.assertArrayEquals(expected.getData(), actual.getData());
		Assert.assertArrayEquals(expected.getLe(), actual.getLe());
	}

	@Test
	public void testCase4() throws CAPDUParserException {
		byte[] capdu = Hex.decode("00A4040004CAFEBABE00");
		CAPDU expected = new CAPDU(0x00, 0xA4, 0x04, 0x00, Hex.decode("CAFEBABE"), 0x00);
		CAPDU actual = CAPDUParser.parse(capdu);
		Assert.assertEquals(expected, actual);
		Assert.assertFalse(actual.isCase1());
		Assert.assertFalse(actual.isCase2());
		Assert.assertFalse(actual.isCase3());
		Assert.assertTrue(actual.isCase4());
		Assert.assertEquals(expected.getCla(), actual.getCla());
		Assert.assertEquals(expected.getIns(), actual.getIns());
		Assert.assertEquals(expected.getP1(), actual.getP1());
		Assert.assertEquals(expected.getP2(), actual.getP2());
		Assert.assertArrayEquals(expected.getData(), actual.getData());
		Assert.assertArrayEquals(expected.getLe(), actual.getLe());
	}
}