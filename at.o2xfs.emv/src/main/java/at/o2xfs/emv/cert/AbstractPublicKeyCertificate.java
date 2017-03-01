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

package at.o2xfs.emv.cert;

import at.o2xfs.common.Bytes;

public abstract class AbstractPublicKeyCertificate {

	public static class PublicKeyCertificateBuilder {

		private byte[] pan = null;

		private byte[] certificate = null;

		private byte[] remainder = Bytes.EMPTY;

		private byte[] exponent = null;

		public PublicKeyCertificateBuilder pan(byte[] pan) {
			this.pan = Bytes.copy(pan);
			return this;
		}

		public PublicKeyCertificateBuilder certificate(byte[] certificate) {
			this.certificate = Bytes.copy(certificate);
			return this;
		}

		public PublicKeyCertificateBuilder remainder(byte[] remainder) {
			this.remainder = Bytes.copy(remainder);
			return this;
		}

		public PublicKeyCertificateBuilder exponent(byte[] exponent) {
			this.exponent = Bytes.copy(exponent);
			return this;
		}
	}

	private final byte[] pan;

	private final byte[] certificate;

	private final byte[] exponent;

	private final byte[] remainder;

	public AbstractPublicKeyCertificate(PublicKeyCertificateBuilder builder) {
		if (builder.pan == null) {
			throw new NullPointerException("pan must not be null");
		}
		pan = builder.pan;
		if (builder.certificate == null) {
			throw new NullPointerException("certificate must not be null");
		}
		certificate = builder.certificate;
		if (builder.remainder == null) {
			throw new NullPointerException("remainder must not be null");
		}
		remainder = builder.remainder;
		if (builder.exponent == null) {
			throw new NullPointerException("exponent must not be null");
		}
		exponent = builder.exponent;
	}

	public byte[] getPAN() {
		return Bytes.copy(pan);
	}

	public byte[] getCertificate() {
		return Bytes.copy(certificate);
	}

	public byte[] getRemainder() {
		return Bytes.copy(remainder);
	}

	public byte[] getExponent() {
		return Bytes.copy(exponent);
	}
}