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

package at.o2xfs.emv;

import static at.o2xfs.emv.dda.RecoveredSDAData.DATA_HEADER;
import static at.o2xfs.emv.dda.RecoveredSDAData.DATA_TRAILER;
import static at.o2xfs.emv.dda.RecoveredSDAData.HASH_ALGORITHM_INDICATOR;
import static at.o2xfs.emv.dda.RecoveredSDAData.HASH_RESULT;
import static at.o2xfs.emv.dda.RecoveredSDAData.ICC_DYNAMIC_DATA;
import static at.o2xfs.emv.dda.RecoveredSDAData.ICC_DYNAMIC_DATA_LENGTH;
import static at.o2xfs.emv.dda.RecoveredSDAData.PAD_PATTERN;
import static at.o2xfs.emv.dda.RecoveredSDAData.SIGNED_DATA_FORMAT;

import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;
import at.o2xfs.emv.cert.CertStore;
import at.o2xfs.emv.cert.CertStoreException;
import at.o2xfs.emv.cert.ICCPublicKeyCertificate;
import at.o2xfs.emv.cert.IssuerPublicKeyCertificate;
import at.o2xfs.emv.crypto.CAPublicKey;
import at.o2xfs.emv.crypto.Crypto;
import at.o2xfs.emv.crypto.CryptoException;
import at.o2xfs.emv.crypto.CryptoFactory;
import at.o2xfs.emv.crypto.PublicKey;
import at.o2xfs.emv.dda.RecoveredSDAData;

import java.util.Arrays;

abstract public class OfflineDynamicDataAuthentication {

	protected final EMVTransaction transaction;

	private PublicKey iccPublicKey = null;

	protected RecoveredData<RecoveredSDAData> recoveredData = null;

	public OfflineDynamicDataAuthentication(EMVTransaction transaction) {
		this.transaction = transaction;
	}

	private void checkRequiredData() throws OfflineDynamicDataAuthenticationFailedException {
		try {
			MandatoryData mandatoryData = new MandatoryData(transaction);
			mandatoryData.assertPresent(EMVTag.CERTIFICATION_AUTHORITY_PUBLIC_KEY_INDEX);
			mandatoryData.assertPresent(EMVTag.ISSUER_PUBLIC_KEY_CERTIFICATE);
			mandatoryData.assertPresent(EMVTag.ISSUER_PUBLIC_KEY_EXPONENT);
			mandatoryData.assertPresent(EMVTag.ICC_PUBLIC_KEY_CERTIFICATE);
			mandatoryData.assertPresent(EMVTag.ICC_PUBLIC_KEY_EXPONENT);
			mandatoryData.assertPresent(EMVTag.DYNAMIC_DATA_AUTHENTICATION_DATA_OBJECT_LIST);
		} catch (MissingMandatoryDataException e) {
			transaction.getTVR().getByte1().iccDataMissing();
			throw new OfflineDynamicDataAuthenticationFailedException(e);
		}
	}

	protected void retrieveICCPublicKey() throws OfflineDynamicDataAuthenticationFailedException {
		try {
			checkRequiredData();
			PublicKey issuerPublicKey = retrieveIssuerPublicKey();
			CertStore certStore = new CertStore(transaction);
			ICCPublicKeyCertificate iccPublicKeyCertificate = certStore.getICCPublicKeyCertificate();
			iccPublicKey = new ICCPublicKey(iccPublicKeyCertificate, issuerPublicKey).retrieve();
		} catch (CertStoreException e) {
			throw new OfflineDynamicDataAuthenticationFailedException(e);
		} catch (ICCPublicKeyException e) {
			if (e instanceof ICCPublicKeyRemainderNotPresentException) {
				transaction.getTVR().getByte1().iccDataMissing();
			}
			throw new OfflineDynamicDataAuthenticationFailedException(e);
		}
	}

	private PublicKey retrieveIssuerPublicKey() throws OfflineDynamicDataAuthenticationFailedException {
		try {
			CertStore certStore = new CertStore(transaction);
			CAPublicKey caPublicKey = certStore.getCAPublicKey();
			IssuerPublicKeyCertificate issuerPublicKeyCertificate = certStore.getIssuerPublicKeyCertificate();
			return new IssuerPublicKey(issuerPublicKeyCertificate, caPublicKey).retrieve();
		} catch (CertStoreException e) {
			throw new OfflineDynamicDataAuthenticationFailedException(e);
		} catch (IssuerPublicKeyException e) {
			if (e instanceof IssuerPublicKeyRemainderNotPresentException) {
				transaction.getTVR().getByte1().iccDataMissing();
			}
			throw new OfflineDynamicDataAuthenticationFailedException(e);
		}
	}

	protected void recoverData(byte[] signedDynamicApplicationData) throws OfflineDynamicDataAuthenticationFailedException {
		try {
			checkSignedDynamicApplicationDataLength(signedDynamicApplicationData);
			Crypto crypto = CryptoFactory.getInstance().newCrypto();
			byte[] decrypted = crypto.decrypt(iccPublicKey, signedDynamicApplicationData);
			recoveredData = buildRecoveredData(decrypted);
			checkRecoveredDataTrailer();
			checkRecoveredDataHeader();
			checkSignedDataFormat();
		} catch (CryptoException e) {
			throw new OfflineDynamicDataAuthenticationFailedException(e);
		}
	}

	private RecoveredData<RecoveredSDAData> buildRecoveredData(byte[] recoveredData) {
		RecoveredData.Builder<RecoveredSDAData> builder = new RecoveredData.Builder<RecoveredSDAData>();
		int iccDynamicDataLength = Bytes.toInt(recoveredData[3]);
		int padPatternLength = iccPublicKey.getModulusLength() - iccDynamicDataLength - 25;
		builder.addField(DATA_HEADER, 1);
		builder.addField(SIGNED_DATA_FORMAT, 1);
		builder.addField(HASH_ALGORITHM_INDICATOR, 1);
		builder.addField(ICC_DYNAMIC_DATA_LENGTH, 1);
		builder.addField(ICC_DYNAMIC_DATA, iccDynamicDataLength);
		builder.addField(PAD_PATTERN, padPatternLength);
		builder.addField(HASH_RESULT, 20);
		builder.addField(DATA_TRAILER, 1);
		return new RecoveredData<RecoveredSDAData>(builder, recoveredData);
	}

	private void checkSignedDynamicApplicationDataLength(byte[] signedDynamicApplicationData) throws OfflineDynamicDataAuthenticationFailedException {
		if (signedDynamicApplicationData.length != iccPublicKey.getModulusLength()) {
			throw new OfflineDynamicDataAuthenticationFailedException("Signed Dynamic Application Data has a length (" + signedDynamicApplicationData.length
																		+ ") different from the length ("
																		+ iccPublicKey.getModulusLength()
																		+ ") of the ICC Public Key Modulus");
		}
	}

	private void checkRecoveredDataTrailer() throws OfflineDynamicDataAuthenticationFailedException {
		byte[] dataTrailer = recoveredData.get(DATA_TRAILER);
		if (!Arrays.equals(dataTrailer, Hex.decode("BC"))) {
			throw new OfflineDynamicDataAuthenticationFailedException("Illegal Data Trailer: " + Hex.encode(dataTrailer));
		}
	}

	private void checkRecoveredDataHeader() throws OfflineDynamicDataAuthenticationFailedException {
		byte[] dataHeader = recoveredData.get(DATA_HEADER);
		if (!Arrays.equals(dataHeader, Hex.decode("6A"))) {
			throw new OfflineDynamicDataAuthenticationFailedException("Illegal Data Header: " + Hex.encode(dataHeader));
		}
	}

	private void checkSignedDataFormat() throws OfflineDynamicDataAuthenticationFailedException {
		byte[] signedDataFormat = recoveredData.get(SIGNED_DATA_FORMAT);
		if (!Arrays.equals(signedDataFormat, Hex.decode("05"))) {
			throw new OfflineDynamicDataAuthenticationFailedException("Illegal Signed Data Format: " + Hex.encode(signedDataFormat));
		}
	}

}