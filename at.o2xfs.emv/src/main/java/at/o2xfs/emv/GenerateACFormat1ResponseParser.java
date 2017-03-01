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

package at.o2xfs.emv;

import at.o2xfs.common.Bytes;
import at.o2xfs.emv.tlv.TLV;
import at.o2xfs.emv.tlv.TLVBuilder;

class GenerateACFormat1ResponseParser {

	private static final int CID_LENGTH = 1;

	private static final int ATC_LENGTH = 1;

	private static final int AC_LENGTH = 8;

	private final byte[] value;

	private int offset = 0;

	private TLVBuilder tlvBuilder = null;

	GenerateACFormat1ResponseParser(byte[] value) {
		this.value = value;
	}

	TLV parse() {
		tlvBuilder = TLVBuilder
				.newConstructed(EMVTag.RESPONSE_MESSAGE_TEMPLATE_FORMAT_2
						.getTag());
		offset = 0;
		parseCryptogramInformationData();
		parseApplicationTransactionCounter();
		parseApplicationCryptogram();
		parseIssuerApplicationData();
		return tlvBuilder.build();
	}

	private void parseCryptogramInformationData() {
		byte[] cid = Bytes.mid(value, offset, CID_LENGTH);
		tlvBuilder.add(TLVBuilder.newPrimitive(
				EMVTag.CRYPTOGRAM_INFORMATION_DATA.getTag(), cid));
		offset += CID_LENGTH;
	}

	private void parseApplicationTransactionCounter() {
		byte[] atc = Bytes.mid(value, offset, ATC_LENGTH);
		tlvBuilder.add(TLVBuilder.newPrimitive(
				EMVTag.APPLICATION_TRANSACTION_COUNTER.getTag(), atc));
		offset += ATC_LENGTH;
	}

	private void parseApplicationCryptogram() {
		byte[] ac = Bytes.mid(value, offset, AC_LENGTH);
		tlvBuilder.add(TLVBuilder.newPrimitive(
				EMVTag.APPLICATION_CRYPTOGRAM.getTag(), ac));
		offset += AC_LENGTH;
	}

	private void parseIssuerApplicationData() {
		if (offset == value.length) {
			return;
		}
		byte[] iad = Bytes.right(value, value.length - offset);
		tlvBuilder.add(TLVBuilder.newPrimitive(
				EMVTag.ISSUER_APPLICATION_DATA.getTag(), iad));
	}
}