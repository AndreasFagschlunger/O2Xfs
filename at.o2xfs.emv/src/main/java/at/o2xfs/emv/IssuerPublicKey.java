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

import static at.o2xfs.emv.RecoveredIssuerData.CERTIFICATE_EXPIRATION_DATE;
import static at.o2xfs.emv.RecoveredIssuerData.CERTIFICATE_FORMAT;
import static at.o2xfs.emv.RecoveredIssuerData.DATA_HEADER;
import static at.o2xfs.emv.RecoveredIssuerData.DATA_TRAILER;
import static at.o2xfs.emv.RecoveredIssuerData.HASH_ALGORITHM_INDICATOR;
import static at.o2xfs.emv.RecoveredIssuerData.HASH_RESULT;
import static at.o2xfs.emv.RecoveredIssuerData.ISSUER_IDENTIFIER;
import static at.o2xfs.emv.RecoveredIssuerData.ISSUER_PUBLIC_KEY;
import static at.o2xfs.emv.RecoveredIssuerData.ISSUER_PUBLIC_KEY_ALGORITHM_INDICATOR;

import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;
import at.o2xfs.emv.ExpirationDate.ExpirationDateException;
import at.o2xfs.emv.cert.IssuerPublicKeyCertificate;
import at.o2xfs.emv.crypto.AsymmetricAlgorithm;
import at.o2xfs.emv.crypto.CAPublicKey;
import at.o2xfs.emv.crypto.Crypto;
import at.o2xfs.emv.crypto.CryptoException;
import at.o2xfs.emv.crypto.CryptoFactory;
import at.o2xfs.emv.crypto.PublicKey;
import at.o2xfs.emv.util.CompressedNumeric;

import java.util.Arrays;

public final class IssuerPublicKey {

	private final CAPublicKey caPublicKey;

	private final IssuerPublicKeyCertificate issuerPublicKeyCertificate;

	private RecoveredData<RecoveredIssuerData> issuerData = null;

	private PublicKey issuerPublicKey = null;

	public IssuerPublicKey(IssuerPublicKeyCertificate issuerPublicKeyCertificate, CAPublicKey caPublicKey) {
		this.issuerPublicKeyCertificate = issuerPublicKeyCertificate;
		this.caPublicKey = caPublicKey;
	}

	public PublicKey retrieve() throws IssuerPublicKeyException {
		try {
			checkLength();
			byte[] recoveredData = recoverData();
			buildRecoveredData(recoveredData);
			checkRecoveredData();
			return issuerPublicKey;
		} catch (CryptoException e) {
			throw new IssuerPublicKeyException(e);
		}
	}

	private void buildRecoveredData(byte[] recoveredData) {
		RecoveredData.Builder<RecoveredIssuerData> builder = new RecoveredData.Builder<RecoveredIssuerData>();
		builder.addField(RecoveredIssuerData.DATA_HEADER, 1);
		builder.addField(RecoveredIssuerData.CERTIFICATE_FORMAT, 1);
		builder.addField(RecoveredIssuerData.ISSUER_IDENTIFIER, 4);
		builder.addField(RecoveredIssuerData.CERTIFICATE_EXPIRATION_DATE, 2);
		builder.addField(RecoveredIssuerData.CERTIFICATE_SERIAL_NUMBER, 3);
		builder.addField(RecoveredIssuerData.HASH_ALGORITHM_INDICATOR, 1);
		builder.addField(RecoveredIssuerData.ISSUER_PUBLIC_KEY_ALGORITHM_INDICATOR, 1);
		builder.addField(RecoveredIssuerData.ISSUER_PUBLIC_KEY_LENGTH, 1);
		builder.addField(RecoveredIssuerData.ISSUER_PUBLIC_KEY_EXPONENT_LENGTH, 1);
		builder.addField(RecoveredIssuerData.ISSUER_PUBLIC_KEY, caPublicKey.getModulus().length - 36);
		builder.addField(RecoveredIssuerData.HASH_RESULT, 20);
		builder.addField(RecoveredIssuerData.DATA_TRAILER, 1);
		issuerData = new RecoveredData<RecoveredIssuerData>(builder, recoveredData);
	}

	private void checkRecoveredData() throws IssuerPublicKeyException {
		checkDataTrailer();
		checkDataHeader();
		checkCertificateFormat();
		checkHashResult();
		checkIssuerIdentifier();
		checkExpirationDate();
		// TODO: Certification Revocation List
		checkIssuerPublicKeyAlgorithmIndicator();
		int nCA = caPublicKey.getModulus().length;
		int nI = Bytes.toInt(issuerData.get(RecoveredIssuerData.ISSUER_PUBLIC_KEY_LENGTH)[0]);
		byte[] modulus;
		if (nI <= (nCA - 36)) {
			modulus = Bytes.left(issuerData.get(RecoveredIssuerData.ISSUER_PUBLIC_KEY), nI);
		} else {
			modulus = Bytes.concat(issuerData.get(ISSUER_PUBLIC_KEY), getIssuerPublicKeyRemainder());
		}
		byte[] exponent = issuerPublicKeyCertificate.getExponent();
		issuerPublicKey = new PublicKey(issuerData.get(ISSUER_PUBLIC_KEY_ALGORITHM_INDICATOR), modulus, exponent);
	}

	private void checkHashResult() throws IssuerPublicKeyException {
		byte[] hashInput = issuerData.concatenate(CERTIFICATE_FORMAT, ISSUER_PUBLIC_KEY);
		hashInput = Bytes.concat(hashInput, getIssuerPublicKeyRemainder(), issuerPublicKeyCertificate.getExponent());
		try {
			CryptoFactory cryptoFactory = CryptoFactory.getInstance();
			Crypto crypto = cryptoFactory.newCrypto();
			byte[] hashResult = crypto.digest(issuerData.get(HASH_ALGORITHM_INDICATOR), hashInput);
			if (!Arrays.equals(hashResult, issuerData.get(HASH_RESULT))) {
				throw new IssuerPublicKeyException("Hash mismatch");
			}
		} catch (CryptoException e) {
			throw new IssuerPublicKeyException(e);
		}
	}

	private byte[] getIssuerPublicKeyRemainder() throws IssuerPublicKeyRemainderNotPresentException {
		int nCA = caPublicKey.getModulus().length;
		int nI = Bytes.toInt(issuerData.get(RecoveredIssuerData.ISSUER_PUBLIC_KEY_LENGTH)[0]);
		if (nI > (nCA - 36)) {
			byte[] remainder = issuerPublicKeyCertificate.getRemainder();
			if (remainder.length == 0) {
				throw new IssuerPublicKeyRemainderNotPresentException();
			}
			return remainder;
		}
		return Bytes.EMPTY;
	}

	private void checkDataTrailer() throws IssuerPublicKeyException {
		if (!Arrays.equals(issuerData.get(DATA_TRAILER), Hex.decode("BC"))) {
			throw new IssuerPublicKeyException("The Recovered Data Trailer is not equal to 0xBC");
		}
	}

	private void checkDataHeader() throws IssuerPublicKeyException {
		if (!Arrays.equals(issuerData.get(DATA_HEADER), Hex.decode("6A"))) {
			throw new IssuerPublicKeyException("The Recovered Data Header is not equal to 0x6A");
		}
	}

	private void checkCertificateFormat() throws IssuerPublicKeyException {
		if (!Arrays.equals(issuerData.get(CERTIFICATE_FORMAT), Hex.decode("02"))) {
			throw new IssuerPublicKeyException("Certificate Format is not 0x02");
		}
	}

	private byte[] recoverData() throws CryptoException {
		CryptoFactory cryptoFactory = CryptoFactory.getInstance();
		Crypto crypto = cryptoFactory.newCrypto();
		PublicKey publicKey = new PublicKey(caPublicKey.getPublicKeyAlgorithm(),
											caPublicKey.getModulus(),
											caPublicKey.getExponent());
		byte[] recoveredData = crypto.decrypt(publicKey, issuerPublicKeyCertificate.getCertificate());
		return recoveredData;
	}

	private void checkLength() throws IssuerPublicKeyException {
		if (issuerPublicKeyCertificate.getCertificate().length != caPublicKey.getModulus().length) {
			throw new IssuerPublicKeyException("Issuer Public Key Certificate has a length different from the length of the Certification Authority Public Key Modulus");
		}
	}

	private void checkIssuerIdentifier() throws IssuerPublicKeyException {
		String pan = CompressedNumeric.toString(issuerPublicKeyCertificate.getPAN());
		String issuerIdentifier = CompressedNumeric.toString(issuerData.get(ISSUER_IDENTIFIER));
		if ((issuerIdentifier.length() < 3) || !pan.startsWith(issuerIdentifier)) {
			throw new IssuerPublicKeyException("Issuer Identifier does not match, PAN: " + pan
												+ ", Issuer Identifier: "
												+ issuerIdentifier);
		}
	}

	private void checkExpirationDate() throws IssuerPublicKeyException {
		try {
			final ExpirationDate expirationDate = new ExpirationDate(issuerData.get(CERTIFICATE_EXPIRATION_DATE));
			if (expirationDate.hasExpired()) {
				throw new IssuerPublicKeyException("Certificate has expired");
			}
		} catch (ExpirationDateException e) {
			throw new IssuerPublicKeyException(e);
		}
	}

	private void checkIssuerPublicKeyAlgorithmIndicator() throws IssuerPublicKeyException {
		if (!Arrays.equals(	issuerData.get(ISSUER_PUBLIC_KEY_ALGORITHM_INDICATOR),
							AsymmetricAlgorithm.RSA.getAlgorithmIndicator())) {
			throw new IssuerPublicKeyException("Unrecognised Issuer Public Key Algorithm Indicator: " + Hex.encode(issuerData.get(ISSUER_PUBLIC_KEY_ALGORITHM_INDICATOR)));
		}
	}
}