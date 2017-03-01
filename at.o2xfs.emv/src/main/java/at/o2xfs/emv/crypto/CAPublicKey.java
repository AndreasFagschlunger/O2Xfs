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

package at.o2xfs.emv.crypto;

import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;

public final class CAPublicKey {

	public static final class CAPublicKeyBuilder {

		private byte[] rid = null;

		private byte[] publicKeyIndex = null;

		private byte[] hashAlgorithm = Hex.decode("01");

		private byte[] publicKeyAlgorithm = Hex.decode("01");

		private byte[] modulus = null;

		private byte[] exponent = null;

		private byte[] checkSum = null;

		public CAPublicKeyBuilder() {
		}

		public CAPublicKeyBuilder rid(byte[] rid) {
			this.rid = rid;
			return this;
		}

		public CAPublicKeyBuilder publicKeyIndex(byte[] publicKeyIndex) {
			this.publicKeyIndex = publicKeyIndex;
			return this;
		}

		public CAPublicKeyBuilder hashAlgorithm(byte[] hashAlgorithm) {
			this.hashAlgorithm = hashAlgorithm;
			return this;
		}

		public CAPublicKeyBuilder publicKeyAlgorithm(byte[] publicKeyAlgorithm) {
			this.publicKeyAlgorithm = publicKeyAlgorithm;
			return this;
		}

		public CAPublicKeyBuilder modulus(byte[] modulus) {
			this.modulus = modulus;
			return this;
		}

		public CAPublicKeyBuilder exponent(byte[] exponent) {
			this.exponent = exponent;
			return this;
		}

		public CAPublicKeyBuilder checkSum(byte[] checkSum) {
			this.checkSum = checkSum;
			return this;
		}

		public CAPublicKey build() {
			return new CAPublicKey(this);
		}
	}

	private final byte[] rid;

	private final byte[] publicKeyIndex;

	private final byte[] hashAlgorithm;

	private final byte[] publicKeyAlgorithm;

	private final byte[] modulus;

	private final byte[] exponent;

	private final byte[] checkSum;

	private CAPublicKey(CAPublicKeyBuilder builder) {
		rid = builder.rid;
		if (rid.length != 5) {
			throw new IllegalArgumentException("Illegal RID: "
					+ Hex.encode(rid));
		}
		publicKeyIndex = builder.publicKeyIndex;
		if (publicKeyIndex.length != 1) {
			throw new IllegalArgumentException("Illegal Public Key Index: "
					+ Hex.encode(publicKeyIndex));
		}
		hashAlgorithm = builder.hashAlgorithm;
		if (hashAlgorithm.length != 1) {
			throw new IllegalArgumentException("Illegal Hash Algorithm: "
					+ Hex.encode(hashAlgorithm));
		}
		publicKeyAlgorithm = builder.publicKeyAlgorithm;
		if (publicKeyAlgorithm.length != 1) {
			throw new IllegalArgumentException("Illegal Public Key Algorithm: "
					+ Hex.encode(hashAlgorithm));
		}
		this.modulus = builder.modulus;
		this.exponent = builder.exponent;
		checkSum = builder.checkSum;
		if (checkSum.length != 20) {
			throw new IllegalArgumentException("Illegal Check Sum: "
					+ Hex.encode(checkSum));
		}
	}

	public byte[] getRID() {
		return Bytes.copy(rid);
	}

	public byte[] getPublicKeyIndex() {
		return Bytes.copy(publicKeyIndex);
	}

	public byte[] getHashAlgorithm() {
		return Bytes.copy(hashAlgorithm);
	}

	public byte[] getPublicKeyAlgorithm() {
		return Bytes.copy(publicKeyAlgorithm);
	}

	public byte[] getModulus() {
		return Bytes.copy(modulus);
	}

	public byte[] getExponent() {
		return Bytes.copy(exponent);
	}

	public byte[] getCheckSum() {
		return Bytes.copy(checkSum);
	}
}