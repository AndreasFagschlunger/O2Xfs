/*
 * Copyright (c) 2017, Andreas Fagschlunger. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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