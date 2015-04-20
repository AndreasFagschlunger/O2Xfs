/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
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

public final class CAPDUParser {

	private static final int MANDATORY_HEADER_LENGTH = 4;

	private final byte[] capdu;

	private int cla = 0;

	private int ins = 0;

	private int p1 = 0;

	private int p2 = 0;

	private byte[] data = Bytes.EMPTY;

	private byte[] le = Bytes.EMPTY;

	private CAPDUParser(byte[] capdu) {
		this.capdu = Bytes.copy(capdu);
	}

	private CAPDU parse() throws CAPDUParserException {
		parseMandatoryHeader();
		if (capdu.length > MANDATORY_HEADER_LENGTH) {
			parseConditionalBody();
		}
		if (le.length != 0) {
			return new CAPDU(cla, ins, p1, p2, data, Bytes.toInt(le[0]));
		}
		return new CAPDU(cla, ins, p1, p2, data);
	}

	private void parseMandatoryHeader() throws CAPDUParserException {
		if (capdu.length < MANDATORY_HEADER_LENGTH) {
			throw new CAPDUParserException("Missing Mandatory Header", capdu);
		}
		cla = Bytes.toInt(capdu[0]);
		ins = Bytes.toInt(capdu[1]);
		p1 = Bytes.toInt(capdu[2]);
		p2 = Bytes.toInt(capdu[3]);
	}

	private void parseConditionalBody() throws CAPDUParserException {
		if (capdu.length == MANDATORY_HEADER_LENGTH + 1) {
			le = Bytes.right(capdu, 1);
			return;
		}
		int offset = MANDATORY_HEADER_LENGTH;
		int lc = Bytes.toInt(capdu[offset++]);
		if ((offset + lc) > capdu.length) {
			throw new CAPDUParserException("Unexpected end of data", capdu);
		}
		data = new byte[lc];
		System.arraycopy(capdu, offset, data, 0, data.length);
		offset += data.length;
		if (capdu.length >= offset + 1) {
			le = new byte[capdu.length - offset];
			System.arraycopy(capdu, offset, le, 0, le.length);
			if (le.length != 1) {
				throw new CAPDUParserException("Illegal Le: " + Hex.encode(le), capdu);
			}
		}
	}

	public static CAPDU parse(byte[] capdu) throws CAPDUParserException {
		return new CAPDUParser(capdu).parse();
	}
}