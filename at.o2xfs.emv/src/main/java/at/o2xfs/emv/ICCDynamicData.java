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

final class ICCDynamicData {

	private final byte[] dynamicData;

	private byte[] dynamicNumber = null;

	private byte[] cryptogramInformationData = null;

	private byte[] cryptogram = null;

	private byte[] hashCode = null;

	private ICCDynamicData(byte[] dynamicData) {
		this.dynamicData = Bytes.copy(dynamicData);
		parseDynamicData();
	}

	private void parseDynamicData() {
		dynamicNumber = parseDynamicNumber();
		int offset = 1 + dynamicNumber.length;
		cryptogramInformationData = parseCryptogramInformationData(offset);
		offset += cryptogramInformationData.length;
		cryptogram = parseCryptogram(offset);
		offset += cryptogram.length;
		hashCode = parseHashCode(offset);
	}

	private byte[] parseDynamicNumber() {
		int length = Bytes.toInt(dynamicData[0]);
		return Bytes.mid(dynamicData, 1, length);
	}

	private byte[] parseCryptogramInformationData(int offset) {
		return Bytes.mid(dynamicData, offset, 1);
	}

	private byte[] parseCryptogram(int offset) {
		return Bytes.mid(dynamicData, offset, 8);
	}

	private byte[] parseHashCode(int offset) {
		return Bytes.mid(dynamicData, offset, 20);
	}

	public byte[] getDynamicNumber() {
		return Bytes.copy(dynamicNumber);
	}

	public byte[] getCryptogramInformationData() {
		return Bytes.copy(cryptogramInformationData);
	}

	public byte[] getCryptogram() {
		return Bytes.copy(cryptogram);
	}

	public byte[] getHashCode() {
		return Bytes.copy(hashCode);
	}

	public static final ICCDynamicData parse(byte[] data) {
		return new ICCDynamicData(data);
	}
}