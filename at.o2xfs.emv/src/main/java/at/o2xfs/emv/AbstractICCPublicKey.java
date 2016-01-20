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

import static at.o2xfs.emv.RecoveredICCData.APPLICATION_PAN;
import static at.o2xfs.emv.RecoveredICCData.CERTIFICATE_EXPIRATION_DATE;
import static at.o2xfs.emv.RecoveredICCData.CERTIFICATE_FORMAT;
import static at.o2xfs.emv.RecoveredICCData.CERTIFICATE_SERIAL_NUMBER;
import static at.o2xfs.emv.RecoveredICCData.DATA_HEADER;
import static at.o2xfs.emv.RecoveredICCData.DATA_TRAILER;
import static at.o2xfs.emv.RecoveredICCData.HASH_ALGORITHM_INDICATOR;
import static at.o2xfs.emv.RecoveredICCData.HASH_RESULT;
import static at.o2xfs.emv.RecoveredICCData.ICC_PUBLIC_KEY;
import static at.o2xfs.emv.RecoveredICCData.ICC_PUBLIC_KEY_ALGORITHM_INDICATOR;
import static at.o2xfs.emv.RecoveredICCData.ICC_PUBLIC_KEY_EXPONENT_LENGTH;
import static at.o2xfs.emv.RecoveredICCData.ICC_PUBLIC_KEY_LENGTH;

import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;
import at.o2xfs.emv.ExpirationDate.ExpirationDateException;
import at.o2xfs.emv.cert.AbstractPublicKeyCertificate;
import at.o2xfs.emv.crypto.AsymmetricAlgorithm;
import at.o2xfs.emv.crypto.Crypto;
import at.o2xfs.emv.crypto.CryptoException;
import at.o2xfs.emv.crypto.CryptoFactory;
import at.o2xfs.emv.crypto.PublicKey;
import at.o2xfs.emv.util.CompressedNumeric;

import java.util.Arrays;

public abstract class AbstractICCPublicKey {

	private final AbstractPublicKeyCertificate iccPublicKeyCertificate;

	private final PublicKey issuerPublicKey;

	private RecoveredData<RecoveredICCData> recoveredData = null;

	public AbstractICCPublicKey(AbstractPublicKeyCertificate iccPublicKeyCertificate, PublicKey issuerPublicKey) {
		if (iccPublicKeyCertificate == null) {
			throw new NullPointerException("ICC Public Key Certificate must not be null");
		}
		this.iccPublicKeyCertificate = iccPublicKeyCertificate;
		if (issuerPublicKey == null) {
			throw new NullPointerException("Issuer Public Key must not be null");
		}
		this.issuerPublicKey = issuerPublicKey;
	}

	public final PublicKey retrieve() throws ICCPublicKeyException {
		checkLength();
		recoverData();
		checkDataTrailer();
		checkDataHeader();
		checkCertificateFormat();
		checkHashResult();
		checkPAN();
		checkCertificateExpirationDate();
		checkICCPublicKeyAlgorithmIndicator();
		byte[] algorithm = recoveredData.get(ICC_PUBLIC_KEY_ALGORITHM_INDICATOR);
		byte[] modulus;
		int nI = issuerPublicKey.getModulusLength();
		int nIC = Bytes.toInt(recoveredData.get(ICC_PUBLIC_KEY_LENGTH)[0]);
		if (nIC <= (nI - 42)) {
			modulus = Bytes.left(recoveredData.get(ICC_PUBLIC_KEY), nIC);
		} else {
			modulus = Bytes.concat(recoveredData.get(ICC_PUBLIC_KEY), getPublicKeyRemainder());
		}
		byte[] exponent = iccPublicKeyCertificate.getExponent();
		return new PublicKey(algorithm, modulus, exponent);
	}

	private void checkLength() throws ICCPublicKeyException {
		if (iccPublicKeyCertificate.getCertificate().length != issuerPublicKey.getModulusLength()) {
			throw new ICCPublicKeyException("ICC Public Key Certificate has a length (" + iccPublicKeyCertificate.getCertificate().length
											+ ") different from the length ("
											+ issuerPublicKey.getModulusLength()
											+ ") of the Issuer Public Key Modulus");
		}
	}

	private void recoverData() throws ICCPublicKeyException {
		Crypto crypto = CryptoFactory.getInstance().newCrypto();
		try {
			byte[] decrypted = crypto.decrypt(issuerPublicKey, iccPublicKeyCertificate.getCertificate());
			buildRecoveredData(decrypted);
		} catch (CryptoException e) {
			throw new ICCPublicKeyException(e);
		}
	}

	private void buildRecoveredData(byte[] data) {
		RecoveredData.Builder<RecoveredICCData> builder = new RecoveredData.Builder<RecoveredICCData>();
		builder.addField(DATA_HEADER, 1);
		builder.addField(CERTIFICATE_FORMAT, 1);
		builder.addField(APPLICATION_PAN, 10);
		builder.addField(CERTIFICATE_EXPIRATION_DATE, 2);
		builder.addField(CERTIFICATE_SERIAL_NUMBER, 3);
		builder.addField(HASH_ALGORITHM_INDICATOR, 1);
		builder.addField(ICC_PUBLIC_KEY_ALGORITHM_INDICATOR, 1);
		builder.addField(ICC_PUBLIC_KEY_LENGTH, 1);
		builder.addField(ICC_PUBLIC_KEY_EXPONENT_LENGTH, 1);
		builder.addField(ICC_PUBLIC_KEY, issuerPublicKey.getModulusLength() - 42);
		builder.addField(HASH_RESULT, 20);
		builder.addField(DATA_TRAILER, 1);
		recoveredData = new RecoveredData<RecoveredICCData>(builder, data);
	}

	private void checkDataTrailer() throws ICCPublicKeyException {
		byte[] dataTrailer = recoveredData.get(DATA_TRAILER);
		if (!Arrays.equals(dataTrailer, Hex.decode("BC"))) {
			throw new ICCPublicKeyException("Illegal Data Trailer: " + Hex.encode(dataTrailer));
		}
	}

	private void checkDataHeader() throws ICCPublicKeyException {
		byte[] dataHeader = recoveredData.get(DATA_HEADER);
		if (!Arrays.equals(dataHeader, Hex.decode("6A"))) {
			throw new ICCPublicKeyException("Illegal Data Header: " + Hex.encode(dataHeader));
		}
	}

	private void checkCertificateFormat() throws ICCPublicKeyException {
		byte[] certificateFormat = recoveredData.get(CERTIFICATE_FORMAT);
		if (!Arrays.equals(certificateFormat, Hex.decode("04"))) {
			throw new ICCPublicKeyException("Illegal Certificate Format: " + Hex.encode(certificateFormat));
		}
	}

	private void checkHashResult() throws ICCPublicKeyException {
		try {
			byte[] hashInput = createHashInput();
			Crypto crypto = CryptoFactory.getInstance().newCrypto();
			byte[] hashResult = crypto.digest(recoveredData.get(HASH_ALGORITHM_INDICATOR), hashInput);
			if (!Arrays.equals(hashResult, recoveredData.get(HASH_RESULT))) {
				throw new ICCPublicKeyException("Hash mismatch");
			}
		} catch (CryptoException e) {
			throw new ICCPublicKeyException(e);
		}
	}

	private byte[] createHashInput() throws ICCPublicKeyException {
		byte[] concatenated = recoveredData.concatenate(CERTIFICATE_FORMAT, ICC_PUBLIC_KEY);
		byte[] publicKeyRemainder = getPublicKeyRemainder();
		byte[] publicKeyExponent = iccPublicKeyCertificate.getExponent();
		byte[] staticAuthenticationData = getStaticAuthenticationData();
		return Bytes.concat(concatenated, publicKeyRemainder, publicKeyExponent, staticAuthenticationData);
	}

	protected abstract byte[] getStaticAuthenticationData();

	private void checkPAN() throws ICCPublicKeyException {
		String iccPAN = CompressedNumeric.toString(iccPublicKeyCertificate.getPAN());
		String recoveredPAN = CompressedNumeric.toString(recoveredData.get(APPLICATION_PAN));
		if (!iccPAN.equals(recoveredPAN)) {
			throw new ICCPublicKeyException("Recovered PAN (" + recoveredPAN
											+ ") does not match the Application PAN read from the ICC ("
											+ iccPAN
											+ ")");
		}
	}

	private void checkCertificateExpirationDate() throws ICCPublicKeyException {
		byte[] expirationDate = recoveredData.get(CERTIFICATE_EXPIRATION_DATE);
		try {
			if (new ExpirationDate(expirationDate).hasExpired()) {
				throw new ICCPublicKeyException("Certificate has expired: " + Hex.encode(expirationDate));
			}
		} catch (ExpirationDateException e) {
			throw new ICCPublicKeyException(e);
		}
	}

	private void checkICCPublicKeyAlgorithmIndicator() throws ICCPublicKeyException {
		byte[] algorithmIndicator = recoveredData.get(ICC_PUBLIC_KEY_ALGORITHM_INDICATOR);
		for (AsymmetricAlgorithm algorithm : AsymmetricAlgorithm.values()) {
			if (Arrays.equals(algorithmIndicator, algorithm.getAlgorithmIndicator())) {
				return;
			}
		}
		throw new ICCPublicKeyException("Unrecognised ICC Public Key Algorithm: " + Hex.encode(algorithmIndicator));
	}

	private byte[] getPublicKeyRemainder() throws ICCPublicKeyRemainderNotPresentException {
		final int nIC = Bytes.toInt(recoveredData.get(ICC_PUBLIC_KEY_LENGTH)[0]);
		final int nI = issuerPublicKey.getModulusLength();
		if (nIC > nI - 42) {
			byte[] remainder = iccPublicKeyCertificate.getRemainder();
			if (remainder.length == 0) {
				throw new ICCPublicKeyRemainderNotPresentException();
			}
			return remainder;
		}
		return Bytes.EMPTY;
	}
}