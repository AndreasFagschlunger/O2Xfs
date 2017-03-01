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

package at.o2xfs.emv.sda;

import static at.o2xfs.emv.sda.RecoveredSSAData.DATA_AUTHENTICATION_CODE;
import static at.o2xfs.emv.sda.RecoveredSSAData.DATA_HEADER;
import static at.o2xfs.emv.sda.RecoveredSSAData.DATA_TRAILER;
import static at.o2xfs.emv.sda.RecoveredSSAData.HASH_ALGORITHM_INDICATOR;
import static at.o2xfs.emv.sda.RecoveredSSAData.HASH_RESULT;
import static at.o2xfs.emv.sda.RecoveredSSAData.PAD_PATTERN;
import static at.o2xfs.emv.sda.RecoveredSSAData.SIGNED_DATA_FORMAT;

import java.util.Arrays;

import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;
import at.o2xfs.emv.EMVTag;
import at.o2xfs.emv.EMVTransaction;
import at.o2xfs.emv.IssuerPublicKey;
import at.o2xfs.emv.IssuerPublicKeyException;
import at.o2xfs.emv.IssuerPublicKeyRemainderNotPresentException;
import at.o2xfs.emv.MandatoryData;
import at.o2xfs.emv.MissingMandatoryDataException;
import at.o2xfs.emv.RecoveredData;
import at.o2xfs.emv.StaticAuthenticationData;
import at.o2xfs.emv.StaticAuthenticationDataException;
import at.o2xfs.emv.cert.CertStore;
import at.o2xfs.emv.cert.CertStoreException;
import at.o2xfs.emv.cert.IssuerPublicKeyCertificate;
import at.o2xfs.emv.crypto.CAPublicKey;
import at.o2xfs.emv.crypto.Crypto;
import at.o2xfs.emv.crypto.CryptoException;
import at.o2xfs.emv.crypto.CryptoFactory;
import at.o2xfs.emv.crypto.PublicKey;

public class StaticDataAuthentication {

	private final EMVTransaction transaction;

	private final Crypto crypto;

	private PublicKey issuerPublicKey = null;

	public StaticDataAuthentication(EMVTransaction transaction)
			throws SDAFailedException {
		this.transaction = transaction;
		crypto = CryptoFactory.getInstance().newCrypto();
	}

	public void perform() throws SDAFailedException {
		checkConsistency();
		retrieveIssuerPublicKey();
		verifySSAD();
	}

	private void checkConsistency() throws SDAFailedException {
		try {
			final MandatoryData mandatoryData = new MandatoryData(transaction);
			mandatoryData
					.assertPresent(EMVTag.CERTIFICATION_AUTHORITY_PUBLIC_KEY_INDEX);
			mandatoryData.assertPresent(EMVTag.ISSUER_PUBLIC_KEY_CERTIFICATE);
			mandatoryData.assertPresent(EMVTag.SIGNED_STATIC_APPLICATION_DATA);
			mandatoryData.assertPresent(EMVTag.ISSUER_PUBLIC_KEY_EXPONENT);
		} catch (MissingMandatoryDataException e) {
			transaction.getTVR().getByte1().iccDataMissing();
			throw new SDAFailedException(e);
		}
	}

	private void retrieveIssuerPublicKey() throws SDAFailedException {
		try {
			CertStore certStore = new CertStore(transaction);
			CAPublicKey caPublicKey = certStore.getCAPublicKey();
			IssuerPublicKeyCertificate issuerPublicKeyCertificate = certStore
					.getIssuerPublicKeyCertificate();
			issuerPublicKey = new IssuerPublicKey(issuerPublicKeyCertificate,
					caPublicKey).retrieve();
		} catch (IssuerPublicKeyException e) {
			if (e instanceof IssuerPublicKeyRemainderNotPresentException) {
				transaction.getTVR().getByte1().iccDataMissing();
			}
			throw new SDAFailedException(e);
		} catch (CertStoreException e) {
			throw new SDAFailedException(e);
		}
	}

	private void verifySSAD() throws SDAFailedException {
		byte[] ssad = transaction
				.getMandatoryData(EMVTag.SIGNED_STATIC_APPLICATION_DATA);
		if (issuerPublicKey.getModulusLength() != ssad.length) {
			throw new SDAFailedException(
					"Signed Static Application Data has a length different from the length of the Issuer Public Key Modulus");
		}
		byte[] recoveredData = decryptSSAD(ssad);
		RecoveredData<RecoveredSSAData> recoveredSSAData = buildSSAD(recoveredData);
		verifyRecoveredSSAData(recoveredSSAData);
		byte[] dataAuthenticationCode = recoveredSSAData
				.get(DATA_AUTHENTICATION_CODE);
		transaction.putData(EMVTag.DATA_AUTHENTICATION_CODE,
				dataAuthenticationCode);
	}

	private RecoveredData<RecoveredSSAData> buildSSAD(byte[] recoveredData) {
		RecoveredData.Builder<RecoveredSSAData> builder = new RecoveredData.Builder<RecoveredSSAData>();
		builder.addField(DATA_HEADER, 1);
		builder.addField(SIGNED_DATA_FORMAT, 1);
		builder.addField(HASH_ALGORITHM_INDICATOR, 1);
		builder.addField(DATA_AUTHENTICATION_CODE, 2);
		builder.addField(PAD_PATTERN, issuerPublicKey.getModulusLength() - 26);
		builder.addField(HASH_RESULT, 20);
		builder.addField(RecoveredSSAData.DATA_TRAILER, 1);
		return new RecoveredData<RecoveredSSAData>(builder, recoveredData);
	}

	private byte[] decryptSSAD(byte[] ssad) throws SDAFailedException {
		try {
			return crypto.decrypt(issuerPublicKey, ssad);
		} catch (CryptoException e) {
			throw new SDAFailedException(e);
		}
	}

	private void verifyRecoveredSSAData(
			RecoveredData<RecoveredSSAData> recoveredData)
			throws SDAFailedException {
		byte[] dataHeader = recoveredData.get(DATA_HEADER);
		byte[] signedDataFormat = recoveredData.get(SIGNED_DATA_FORMAT);
		byte[] dataTrailer = recoveredData.get(DATA_TRAILER);
		if (!Arrays.equals(dataHeader, Hex.decode("6A"))) {
			throw new SDAFailedException("Illegal Data Header: "
					+ Hex.encode(dataHeader));
		} else if (!Arrays.equals(signedDataFormat, Hex.decode("03"))) {
			throw new SDAFailedException("Illegal Signed Data Format: "
					+ Hex.encode(signedDataFormat));
		} else if (!Arrays.equals(dataTrailer, Hex.decode("BC"))) {
			throw new SDAFailedException("Illegal Data Trailer: "
					+ Hex.encode(dataTrailer));
		}
		byte[] hashInput = createHashInput(recoveredData);
		try {
			byte[] hash = crypto.digest(
					recoveredData.get(HASH_ALGORITHM_INDICATOR), hashInput);
			if (!Arrays.equals(hash, recoveredData.get(HASH_RESULT))) {
				throw new SDAFailedException("Hash mismatch");
			}
		} catch (CryptoException e) {
			throw new SDAFailedException(e);
		}
	}

	private byte[] createHashInput(RecoveredData<RecoveredSSAData> recoveredData)
			throws SDAFailedException {
		byte[] concatenated = recoveredData.concatenate(SIGNED_DATA_FORMAT,
				PAD_PATTERN);
		byte[] staticAuthenticationData = getStaticAuthenticationData();
		byte[] hashInput = Bytes.concat(concatenated, staticAuthenticationData);
		return hashInput;
	}

	private byte[] getStaticAuthenticationData() throws SDAFailedException {
		try {
			StaticAuthenticationData staticAuthenticationData = new StaticAuthenticationData(
					transaction);
			return staticAuthenticationData.getData();
		} catch (StaticAuthenticationDataException e) {
			throw new SDAFailedException(e);
		}
	}
}