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
import at.o2xfs.emv.icc.atr.ATR;
import at.o2xfs.emv.icc.atr.ATRException;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

import java.io.IOException;

public abstract class AbstractICReader
		implements ICReader {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractICReader.class);

	private static final int SW_9000 = 0x9000;
	private static final int SW1_61 = 0x61;
	private static final int WRONG_LENGTH_LE = 0x6C;

	private ATR atr;

	private boolean t0Protocol = true;

	@Override
	final public byte[] reset() throws IOException {
		final String method = "reset()";
		byte[] result = internalReset();
		try {
			atr = new ATR(result);
			if (atr.containsTD(1) && atr.getTD(1) >>> 4 == 1) {
				t0Protocol = false;
			}
		} catch (ATRException e) {
			throw new IOException(e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "result=" + Hex.encode(result) + ",t0Protocol=" + t0Protocol);
		}
		return result;
	}

	@Override
	final public RAPDU transmit(CAPDU capdu) throws IOException {
		if (t0Protocol) {
			return t0Transmit(capdu);
		}
		return internalTransmit(capdu.getBytes());
	}

	protected RAPDU t0Transmit(CAPDU aCapdu) throws IOException {
		CAPDU capdu = aCapdu;
		byte[] ctpdu = capdu.getBytes();
		if (capdu.isCase1()) {
			ctpdu = Bytes.rightPad(ctpdu, ctpdu.length + 1);
		} else if (capdu.isCase4()) {
			return t0Case4(aCapdu);
		}
		RAPDU result = internalTransmit(ctpdu);
		if (result.getSW1() == WRONG_LENGTH_LE) {
			result = wrongLengthLe(aCapdu, result.getSW2());
		}
		return result;
	}

	protected RAPDU t0Case4(CAPDU capdu) throws IOException {
		CAPDU ctpdu = new CAPDU(capdu.getCla(), capdu.getIns(), capdu.getP1(), capdu.getP2(), capdu.getData());
		RAPDU result = internalTransmit(ctpdu.getBytes());
		if (SW_9000 == result.getSW()) {
			result = transmit(GetResponseCommand.create(Bytes.toInt(capdu.getLe()[0])));
		} else if (SW1_61 == result.getSW1()) {
			int le = Bytes.toInt(capdu.getLe()[0]);
			if (le == 0 || le > result.getSW2()) {
				le = result.getSW2();
			}
			result = transmit(GetResponseCommand.create(le));
		}
		return result;

	}

	private RAPDU wrongLengthLe(CAPDU aCapdu, int le) throws IOException {
		CAPDU capdu = new CAPDU(aCapdu.getCla(), aCapdu.getIns(), aCapdu.getP1(), aCapdu.getP2(), aCapdu.getData(), le);
		RAPDU result = internalTransmit(capdu.getBytes());
		if (result.getData().length > le) {
			result = new RAPDU(Bytes.concat(Bytes.left(result.getData(), le), new byte[] {	(byte) result.getSW1(),
																							(byte) result.getSW2() }));
		}
		return result;
	}

	abstract protected byte[] internalReset() throws IOException;

	abstract protected RAPDU internalTransmit(byte[] ctpdu) throws IOException;
}