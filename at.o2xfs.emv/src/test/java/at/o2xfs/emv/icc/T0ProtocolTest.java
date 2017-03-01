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