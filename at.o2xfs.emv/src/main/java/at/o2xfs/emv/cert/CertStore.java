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
import at.o2xfs.common.Hex;
import at.o2xfs.emv.DataNotFoundException;
import at.o2xfs.emv.EMVTag;
import at.o2xfs.emv.EMVTransaction;
import at.o2xfs.emv.StaticAuthenticationData;
import at.o2xfs.emv.StaticAuthenticationDataException;
import at.o2xfs.emv.cert.AbstractPublicKeyCertificate.PublicKeyCertificateBuilder;
import at.o2xfs.emv.cert.ICCPublicKeyCertificate.ICCPublicKeyCertificateBuilder;
import at.o2xfs.emv.crypto.CAPublicKey;
import at.o2xfs.emv.crypto.KeyStore;
import at.o2xfs.emv.crypto.KeyStoreException;
import at.o2xfs.emv.crypto.KeyStoreFactory;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

public final class CertStore {

	private static final Logger LOG = LoggerFactory.getLogger(CertStore.class);

	private static final int RID_SIZE = 5;

	private final EMVTransaction transaction;

	public CertStore(EMVTransaction transaction) {
		this.transaction = transaction;
	}

	public CAPublicKey getCAPublicKey() throws CertStoreException {
		final String method = "getCAPublicKey()";
		byte[] rid = Bytes.left(transaction
				.getMandatoryData(EMVTag.APPLICATION_IDENTIFIER_TERMINAL),
				RID_SIZE);
		byte[] publicKeyIndex = getCAPublicKeyIndex();
		try {
			KeyStore keyStore = KeyStoreFactory.getInstance().createKeyStore();
			return keyStore.getCAPublicKey(rid, publicKeyIndex);
		} catch (KeyStoreException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(
						method,
						"Error loading CA Public Key for RID: "
								+ Hex.encode(rid) + ", PKI: "
								+ Hex.encode(publicKeyIndex));
			}
			throw new CertStoreException(e);
		}
	}

	private byte[] getCAPublicKeyIndex() throws CertStoreException {
		try {
			return transaction
					.getData(EMVTag.CERTIFICATION_AUTHORITY_PUBLIC_KEY_INDEX);
		} catch (DataNotFoundException e) {
			throw new CertStoreException(e);
		}
	}

	public IssuerPublicKeyCertificate getIssuerPublicKeyCertificate()
			throws CertStoreException {
		PublicKeyCertificateBuilder builder = new PublicKeyCertificateBuilder();
		try {
			builder.pan(transaction
					.getMandatoryData(EMVTag.APPLICATION_PRIMARY_ACCOUNT_NUMBER));
			builder.certificate(transaction
					.getData(EMVTag.ISSUER_PUBLIC_KEY_CERTIFICATE));
			if (transaction.containsData(EMVTag.ISSUER_PUBLIC_KEY_REMAINDER)) {
				builder.remainder(transaction
						.getData(EMVTag.ISSUER_PUBLIC_KEY_REMAINDER));
			}
			builder.exponent(transaction
					.getData(EMVTag.ISSUER_PUBLIC_KEY_EXPONENT));
			return new IssuerPublicKeyCertificate(builder);
		} catch (DataNotFoundException e) {
			throw new CertStoreException(e);
		}
	}

	public ICCPublicKeyCertificate getICCPublicKeyCertificate()
			throws CertStoreException {
		ICCPublicKeyCertificateBuilder builder = new ICCPublicKeyCertificateBuilder();
		try {
			builder.pan(transaction
					.getMandatoryData(EMVTag.APPLICATION_PRIMARY_ACCOUNT_NUMBER));
			builder.certificate(transaction
					.getData(EMVTag.ICC_PUBLIC_KEY_CERTIFICATE));
			if (transaction.containsData(EMVTag.ICC_PUBLIC_KEY_REMAINDER)) {
				builder.remainder(transaction
						.getData(EMVTag.ICC_PUBLIC_KEY_REMAINDER));
			}
			builder.exponent(transaction
					.getData(EMVTag.ICC_PUBLIC_KEY_EXPONENT));
		} catch (DataNotFoundException e) {
			throw new CertStoreException(e);
		}
		builder.sda(getStaticAuthenticationData());
		return new ICCPublicKeyCertificate(builder);
	}

	private byte[] getStaticAuthenticationData() throws CertStoreException {
		try {
			StaticAuthenticationData staticAuthenticationData = new StaticAuthenticationData(
					transaction);
			return staticAuthenticationData.getData();
		} catch (StaticAuthenticationDataException e) {
			throw new CertStoreException(e);
		}
	}

	public ICCPINEnciphermentPublicKeyCertificate getICCPINEnciphermentPublicKeyCertificate()
			throws CertStoreException {
		ICCPublicKeyCertificateBuilder builder = new ICCPublicKeyCertificateBuilder();
		try {
			builder.pan(transaction
					.getMandatoryData(EMVTag.APPLICATION_PRIMARY_ACCOUNT_NUMBER));
			builder.certificate(transaction
					.getData(EMVTag.ICC_PIN_ENCIPHERMENT_PUBLIC_KEY_CERTIFICATE));
			if (transaction
					.containsData(EMVTag.ICC_PIN_ENCIPHERMENT_PUBLIC_KEY_REMAINDER)) {
				builder.remainder(transaction
						.getData(EMVTag.ICC_PIN_ENCIPHERMENT_PUBLIC_KEY_REMAINDER));
			}
			builder.exponent(transaction
					.getData(EMVTag.ICC_PIN_ENCIPHERMENT_PUBLIC_KEY_EXPONENT));
		} catch (DataNotFoundException e) {
			throw new CertStoreException(e);
		}
		builder.sda(getStaticAuthenticationData());
		return new ICCPINEnciphermentPublicKeyCertificate(builder);
	}
}