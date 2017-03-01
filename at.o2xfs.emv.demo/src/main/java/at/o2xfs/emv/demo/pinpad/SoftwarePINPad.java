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

package at.o2xfs.emv.demo.pinpad;

import java.security.SecureRandom;
import java.util.Arrays;

import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;
import at.o2xfs.emv.ICCPublicKey;
import at.o2xfs.emv.ICCPublicKeyException;
import at.o2xfs.emv.IssuerPublicKey;
import at.o2xfs.emv.IssuerPublicKeyException;
import at.o2xfs.emv.cert.ICCPINEnciphermentPublicKeyCertificate;
import at.o2xfs.emv.cert.ICCPublicKeyCertificate;
import at.o2xfs.emv.cert.IssuerPublicKeyCertificate;
import at.o2xfs.emv.crypto.CAPublicKey;
import at.o2xfs.emv.crypto.Crypto;
import at.o2xfs.emv.crypto.CryptoException;
import at.o2xfs.emv.crypto.CryptoFactory;
import at.o2xfs.emv.crypto.PublicKey;
import at.o2xfs.emv.cvm.ICCPINEnciphermentPublicKey;
import at.o2xfs.emv.cvm.PINBlock;
import at.o2xfs.emv.demo.ui.UserInterface;
import at.o2xfs.emv.pinpad.PINEntryBypassedException;
import at.o2xfs.emv.pinpad.PINPad;
import at.o2xfs.emv.pinpad.PINPadException;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

public class SoftwarePINPad implements PINPad {

	private static final Logger LOG = LoggerFactory.getLogger(SoftwarePINPad.class);

	private final UserInterface terminalUI;

	private CAPublicKey caPublicKey = null;

	private PublicKey issuerPublicKey = null;

	private PublicKey iccPublicKey = null;

	public SoftwarePINPad(UserInterface terminalUI) {
		this.terminalUI = terminalUI;
	}

	@Override
	public void loadCAPublicKey(CAPublicKey caPublicKey) throws PINPadException {
		verifyCAPublicKeyCheckSum(caPublicKey);
		this.caPublicKey = caPublicKey;
	}

	private void verifyCAPublicKeyCheckSum(CAPublicKey caPublicKey) throws PINPadException {
		try {
			byte[] rid = caPublicKey.getRID();
			byte[] publicKeyIndex = caPublicKey.getPublicKeyIndex();
			byte[] publicKeyModulus = caPublicKey.getModulus();
			byte[] publicKeyExponent = caPublicKey.getExponent();
			byte[] hashInput = Bytes.concat(rid, publicKeyIndex, publicKeyModulus, publicKeyExponent);
			Crypto crypto = CryptoFactory.getInstance().newCrypto();
			byte[] hashResult = crypto.digest(caPublicKey.getHashAlgorithm(), hashInput);
			if (!Arrays.equals(hashResult, caPublicKey.getCheckSum())) {
				throw new PINPadException("Hash mismatch");
			}
		} catch (CryptoException e) {
			throw new PINPadException(e);
		}
	}

	@Override
	public void loadIssuerPublicKey(IssuerPublicKeyCertificate issuerPublicKeyCertificate) throws PINPadException {
		final String method = "loadIssuerPublicKey(IssuerPublicKeyCertificate)";
		try {
			issuerPublicKey = new IssuerPublicKey(issuerPublicKeyCertificate, caPublicKey).retrieve();
		} catch (IssuerPublicKeyException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error retrieving Issuer Public Key", e);
			}
			throw new PINPadException(e);
		}
	}

	@Override
	public void loadICCPublicKey(ICCPublicKeyCertificate iccPublicKeyCertificate) throws PINPadException {
		final String method = "loadICCPublicKey(ICCPublicKeyCertificate)";
		try {
			iccPublicKey = new ICCPublicKey(iccPublicKeyCertificate, issuerPublicKey).retrieve();
		} catch (ICCPublicKeyException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error retrieving ICC Public Key", e);
			}
			throw new PINPadException(e);
		}
	}

	@Override
	public void loadICCPINEnciphermentPublicKey(ICCPINEnciphermentPublicKeyCertificate iccPINEnciphermentPublicKeyCertificate) throws PINPadException {
		final String method = "loadICCPINEnciphermentPublicKey(ICCPINEnciphermentPublicKeyCertificate)";
		try {
			iccPublicKey = new ICCPINEnciphermentPublicKey(iccPINEnciphermentPublicKeyCertificate, issuerPublicKey).retrieve();
		} catch (ICCPublicKeyException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error retrieving ICC PIN Encipherment Public Key", e);
			}
			throw new PINPadException(e);
		}
	}

	@Override
	public byte[] getPlaintextPIN() throws PINPadException {
		String pin = askForPINEntry();
		return new PINBlock(pin).getPINBlock();
	}

	@Override
	public byte[] getEncipheredPIN(byte[] challenge) throws PINPadException {
		try {
			String pin = askForPINEntry();
			byte[] dataHeader = Hex.decode("7F");
			byte[] pinBlock = new PINBlock(pin).getPINBlock();
			byte[] padding = randomPadding();
			byte[] pinData = Bytes.concat(dataHeader, pinBlock, challenge, padding);
			return encrypt(pinData);
		} catch (CryptoException e) {
			throw new PINPadException(e);
		}
	}

	@Override
	public void getOnlinePIN() throws PINPadException {
		throw new PINPadException("Not supported");
	}

	private String askForPINEntry() throws PINPadException {
		String pin = terminalUI.performPinEntry();
		if (pin == null) {
			throw new PINEntryBypassedException();
		}
		return pin;
	}

	private byte[] randomPadding() {
		int nIC = iccPublicKey.getModulusLength();
		byte[] padding = new byte[nIC - 17];
		new SecureRandom().nextBytes(padding);
		return padding;
	}

	private byte[] encrypt(byte[] pinData) throws CryptoException {
		Crypto crypto = CryptoFactory.getInstance().newCrypto();
		return crypto.encrypt(iccPublicKey, pinData);
	}
}