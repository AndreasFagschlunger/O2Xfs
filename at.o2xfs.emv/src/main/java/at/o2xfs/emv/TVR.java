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
import at.o2xfs.common.Hex;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

public final class TVR {

	private static final Logger LOG = LoggerFactory.getLogger(TVR.class);

	public static final int LENGTH = 5;

	private final byte[] tvr;

	private final TVRByte1 byte1;

	private final TVRByte2 byte2;

	private final TVRByte3 byte3;

	private final TVRByte4 byte4;

	private final TVRByte5 byte5;

	TVR(byte[] tvr) {
		if (tvr.length != LENGTH) {
			throw new IllegalArgumentException("Illegal TVR: "
					+ Hex.encode(tvr));
		}
		this.tvr = tvr;
		byte1 = new TVRByte1(this);
		byte2 = new TVRByte2(this);
		byte3 = new TVRByte3(this);
		byte4 = new TVRByte4(this);
		byte5 = new TVRByte5(this);
	}

	void setBit(int index, int mask, String info) {
		final String method = "setBit(int, int, String)";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, "TVR Byte " + (index + 1) + ": " + info);
		}
		tvr[index] |= mask;
	}

	boolean isBitSet(int index, int mask) {
		return (tvr[index] & mask) == mask;
	}

	public byte[] getBytes() {
		return Bytes.copy(tvr);
	}

	public TVRByte1 getByte1() {
		return byte1;
	}

	public TVRByte2 getByte2() {
		return byte2;
	}

	public TVRByte3 getByte3() {
		return byte3;
	}

	public TVRByte4 getByte4() {
		return byte4;
	}

	public TVRByte5 getByte5() {
		return byte5;
	}
}