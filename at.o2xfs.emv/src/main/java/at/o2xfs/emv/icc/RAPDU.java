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

public class RAPDU {

	/**
	 * String of data bytes received in response
	 */
	private byte[] data = new byte[0];

	/**
	 * Command processing status
	 */
	private int sw1 = 0;

	/**
	 * Command processing qualifier
	 */
	private int sw2 = 0;

	public RAPDU(byte[] apdu) {
		parse(apdu);
	}

	private void parse(byte[] apdu) {
		if (apdu.length < 2) {
			throw new IllegalArgumentException(
					"apdu must have at least two bytes");
		}
		data = new byte[apdu.length - 2];
		System.arraycopy(apdu, 0, data, 0, data.length);
		sw1 = apdu[apdu.length - 2] & 0xFF;
		sw2 = apdu[apdu.length - 1] & 0xFF;
	}

	private byte[] getBytes() {
		final byte[] apdu = new byte[data.length + 2];
		System.arraycopy(data, 0, apdu, 0, data.length);
		apdu[apdu.length - 2] = (byte) sw1;
		apdu[apdu.length - 1] = (byte) sw2;
		return apdu;
	}

	public byte[] getData() {
		final byte[] copy = new byte[data.length];
		System.arraycopy(data, 0, copy, 0, data.length);
		return copy;
	}

	public int getSW() {
		return (sw1 << 8) | sw2;
	}

	public int getSW1() {
		return sw1;
	}

	public int getSW2() {
		return sw2;
	}

	@Override
	public String toString() {
		return Hex.encode(getBytes());
	}
}