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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;

@Ignore
public class TestReader
		extends AbstractICReader {

	private class TPDU {

		private final String ctpdu;
		private final String rtpdu;

		private TPDU(String ctpdu, String rtpdu) {
			this.ctpdu = ctpdu;
			this.rtpdu = rtpdu;
		}
	}

	private final byte[] atr;

	private final List<TPDU> tpdus;

	private int tpduIndex = 0;

	public TestReader(byte[] atr) {
		this.atr = atr;
		tpdus = new ArrayList<TPDU>();
	}

	public TestReader expect(String ctpdu, String rtpdu) {
		tpdus.add(new TPDU(ctpdu, rtpdu));
		return this;
	}

	@Override
	protected byte[] internalReset() throws IOException {
		return atr;
	}

	@Override
	protected RAPDU internalTransmit(byte[] ctpdu) throws IOException {
		TPDU tpdu = tpdus.get(tpduIndex++);
		Assert.assertEquals(tpdu.ctpdu, Hex.encode(ctpdu));
		return new RAPDU(Hex.decode(tpdu.rtpdu));
	}

}