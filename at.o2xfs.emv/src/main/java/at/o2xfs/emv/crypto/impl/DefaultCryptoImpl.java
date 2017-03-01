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

package at.o2xfs.emv.crypto.impl;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import at.o2xfs.common.Hex;
import at.o2xfs.emv.crypto.AsymmetricAlgorithm;
import at.o2xfs.emv.crypto.Crypto;
import at.o2xfs.emv.crypto.CryptoException;
import at.o2xfs.emv.crypto.HashAlgorithm;
import at.o2xfs.emv.crypto.PublicKey;

class DefaultCryptoImpl implements Crypto {

	private static final String SHA_1 = "SHA-1";

	private static final String RSA_TRANSFORMATION = "RSA/ECB/NoPadding";

	private MessageDigest createDigest(byte[] algorithmIndicator)
			throws CryptoException {
		try {
			switch (HashAlgorithm.getByAlgorithmIndicator(algorithmIndicator)) {
				case SHA_1:
					return MessageDigest.getInstance(SHA_1);
			}
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoException(e);
		}
		throw new CryptoException("Illegal algorithm: "
				+ Hex.encode(algorithmIndicator));
	}

	@Override
	public byte[] digest(byte[] algorithm, byte[] input) throws CryptoException {
		MessageDigest messageDigest = createDigest(algorithm);
		return messageDigest.digest(input);
	}

	@Override
	public byte[] decrypt(PublicKey key, byte[] input) throws CryptoException {
		try {
			Cipher cipher = createCipher(key.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, toRSAPublicKey(key));
			return cipher.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw new CryptoException(e);
		}
	}

	@Override
	public byte[] encrypt(PublicKey publicKey, byte[] input)
			throws CryptoException {
		try {
			Cipher cipher = createCipher(publicKey.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, toRSAPublicKey(publicKey));
			return cipher.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw new CryptoException(e);
		}
	}

	private java.security.PublicKey toRSAPublicKey(PublicKey key)
			throws GeneralSecurityException {
		final BigInteger modulus = new BigInteger(1, key.getModulus());
		final BigInteger exponent = new BigInteger(1, key.getPublicExponent());
		RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modulus, exponent);
		return KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);
	}

	private Cipher createCipher(byte[] algorithm) throws CryptoException {
		try {
			switch (AsymmetricAlgorithm.getByAlgorithmIndicator(algorithm)) {
				case RSA:
					return Cipher.getInstance(RSA_TRANSFORMATION);
			}
		} catch (GeneralSecurityException e) {
			throw new CryptoException(e);
		}
		throw new CryptoException("Illegal algorithm: " + Hex.encode(algorithm));
	}

}