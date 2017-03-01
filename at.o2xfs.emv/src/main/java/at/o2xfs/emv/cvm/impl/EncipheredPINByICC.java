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

package at.o2xfs.emv.cvm.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import at.o2xfs.common.Hex;
import at.o2xfs.emv.DataNotFoundException;
import at.o2xfs.emv.EMVTag;
import at.o2xfs.emv.EMVTransaction;
import at.o2xfs.emv.ProcessingState;
import at.o2xfs.emv.ProcessingStateException;
import at.o2xfs.emv.TVR;
import at.o2xfs.emv.Terminal;
import at.o2xfs.emv.TerminalCVMCapabilities;
import at.o2xfs.emv.TerminalCapabilities;
import at.o2xfs.emv.cert.CertStore;
import at.o2xfs.emv.cert.CertStoreException;
import at.o2xfs.emv.cert.ICCPINEnciphermentPublicKeyCertificate;
import at.o2xfs.emv.cert.ICCPublicKeyCertificate;
import at.o2xfs.emv.cert.IssuerPublicKeyCertificate;
import at.o2xfs.emv.crypto.CAPublicKey;
import at.o2xfs.emv.cvm.CVMResult;
import at.o2xfs.emv.cvm.PINEnciphermentException;
import at.o2xfs.emv.icc.GetChallengeCommand;
import at.o2xfs.emv.icc.ICReader;
import at.o2xfs.emv.icc.RAPDU;
import at.o2xfs.emv.pinpad.PINEntryBypassedException;
import at.o2xfs.emv.pinpad.PINPad;
import at.o2xfs.emv.pinpad.PINPadException;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

public class EncipheredPINByICC extends OfflinePINCVMethod {

	private static final Logger LOG = LoggerFactory
			.getLogger(EncipheredPINByICC.class);

	private CertStore certStore = null;

	private PINPad pinPad = null;

	@Override
	public boolean isSupported(EMVTransaction transaction) {
		Terminal terminal = transaction.getTerminal();
		TerminalCapabilities tCapabilities = terminal.getTerminalCapabilities();
		TerminalCVMCapabilities cvmCapabilities = tCapabilities
				.getCVMCapabilities();
		return cvmCapabilities.isEncipheredPINForOfflineVerification();
	}

	@Override
	protected CVMResult doPerform() {
		final String method = "doPerform()";
		try {
			certStore = new CertStore(transaction);
			pinPad = transaction.getPINPad();
			checkPINTryCounter();
			if (isICCPINEnciphermentPublicKey()) {
				loadCAPublicKey();
				loadIssuerPublicKey();
				loadICCPINEnciphermentPublicKey();
			} else if (isICCPublicKey()) {
				loadCAPublicKey();
				loadIssuerPublicKey();
				loadICCPublicKey();
			} else {
				throw new PINPadException("No public key found");
			}
			byte[] challenge = getChallenge();
			byte[] pinData = pinPad.getEncipheredPIN(challenge);
			verify(0x88, pinData);
			return CVMResult.SUCCESSFUL;
		} catch (PINEntryBypassedException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "Enciphered PIN entry has been bypassed", e);
			}
			TVR tvr = transaction.getTVR();
			tvr.getByte3().pinWasNotEntered();
		} catch (PINPadException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "PIN pad error", e);
			}
			TVR tvr = transaction.getTVR();
			tvr.getByte3().pinPadNotPresent();
		} catch (PINEnciphermentException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "PIN encipherment has failed", e);
			}
		}
		return CVMResult.FAILED;
	}

	private boolean isICCPINEnciphermentPublicKey() {
		List<EMVTag> dataObjects = new ArrayList<EMVTag>();
		dataObjects.add(EMVTag.CERTIFICATION_AUTHORITY_PUBLIC_KEY_INDEX);
		dataObjects.add(EMVTag.ISSUER_PUBLIC_KEY_CERTIFICATE);
		dataObjects.add(EMVTag.ISSUER_PUBLIC_KEY_EXPONENT);
		dataObjects.add(EMVTag.ICC_PIN_ENCIPHERMENT_PUBLIC_KEY_CERTIFICATE);
		dataObjects.add(EMVTag.ICC_PIN_ENCIPHERMENT_PUBLIC_KEY_EXPONENT);
		return checkDataObjects(dataObjects);
	}

	private boolean isICCPublicKey() {
		List<EMVTag> dataObjects = new ArrayList<EMVTag>();
		dataObjects.add(EMVTag.CERTIFICATION_AUTHORITY_PUBLIC_KEY_INDEX);
		dataObjects.add(EMVTag.ISSUER_PUBLIC_KEY_CERTIFICATE);
		dataObjects.add(EMVTag.ISSUER_PUBLIC_KEY_EXPONENT);
		dataObjects.add(EMVTag.ICC_PUBLIC_KEY_CERTIFICATE);
		dataObjects.add(EMVTag.ICC_PUBLIC_KEY_EXPONENT);
		return checkDataObjects(dataObjects);
	}

	private boolean checkDataObjects(List<EMVTag> tags) {
		final String method = "checkDataObjects(List<EMVTag>)";
		for (EMVTag tag : tags) {
			try {
				transaction.getData(tag);
			} catch (DataNotFoundException e) {
				if (LOG.isDebugEnabled()) {
					LOG.debug(method, "Data not found: " + e.getEMVTag());
				}
				return false;
			}
		}
		return true;
	}

	private void loadCAPublicKey() throws PINPadException {
		try {
			CAPublicKey caPublicKey = certStore.getCAPublicKey();
			pinPad.loadCAPublicKey(caPublicKey);
		} catch (CertStoreException e) {
			throw new PINPadException(e);
		}
	}

	private void loadIssuerPublicKey() throws PINPadException {
		try {
			IssuerPublicKeyCertificate issuerPublicKeyCertificate = certStore
					.getIssuerPublicKeyCertificate();
			pinPad.loadIssuerPublicKey(issuerPublicKeyCertificate);
		} catch (CertStoreException e) {
			throw new PINPadException(e);
		}
	}

	private void loadICCPINEnciphermentPublicKey() throws PINPadException {
		try {
			ICCPINEnciphermentPublicKeyCertificate publicKeyCertificate = certStore
					.getICCPINEnciphermentPublicKeyCertificate();
			pinPad.loadICCPINEnciphermentPublicKey(publicKeyCertificate);
		} catch (CertStoreException e) {
			throw new PINPadException(e);
		}
	}

	private void loadICCPublicKey() throws PINPadException {
		try {
			ICCPublicKeyCertificate publicKeyCertificate = certStore
					.getICCPublicKeyCertificate();
			pinPad.loadICCPublicKey(publicKeyCertificate);
		} catch (CertStoreException e) {
			throw new PINPadException(e);
		}
	}

	private byte[] getChallenge() throws PINEnciphermentException {
		ICReader icReader = transaction.getICReader();
		GetChallengeCommand command = new GetChallengeCommand();
		try {
			RAPDU response = icReader.transmit(command);
			new ProcessingState(response.getSW()).assertSuccessful();
			byte[] data = response.getData();
			if (data.length != 8) {
				throw new PINEnciphermentException("Illegal Challenge: "
						+ Hex.encode(data));
			}
			return data;
		} catch (ProcessingStateException e) {
			throw new PINEnciphermentException(e);
		} catch (IOException e) {
			throw new PINEnciphermentException(e);
		}
	}

}