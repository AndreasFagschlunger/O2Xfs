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

package at.o2xfs.emv.dda;

import static at.o2xfs.emv.dda.RecoveredSDAData.HASH_ALGORITHM_INDICATOR;
import static at.o2xfs.emv.dda.RecoveredSDAData.HASH_RESULT;
import static at.o2xfs.emv.dda.RecoveredSDAData.ICC_DYNAMIC_DATA;
import static at.o2xfs.emv.dda.RecoveredSDAData.PAD_PATTERN;
import static at.o2xfs.emv.dda.RecoveredSDAData.SIGNED_DATA_FORMAT;

import at.o2xfs.common.Bytes;
import at.o2xfs.emv.DOL;
import at.o2xfs.emv.DataNotFoundException;
import at.o2xfs.emv.EMVTag;
import at.o2xfs.emv.EMVTransaction;
import at.o2xfs.emv.NoSuchEntryException;
import at.o2xfs.emv.OfflineDynamicDataAuthentication;
import at.o2xfs.emv.OfflineDynamicDataAuthenticationFailedException;
import at.o2xfs.emv.ProcessingState;
import at.o2xfs.emv.ProcessingStateException;
import at.o2xfs.emv.TLVConstraintViolationException;
import at.o2xfs.emv.Template;
import at.o2xfs.emv.TerminateSessionException;
import at.o2xfs.emv.crypto.Crypto;
import at.o2xfs.emv.crypto.CryptoException;
import at.o2xfs.emv.crypto.CryptoFactory;
import at.o2xfs.emv.icc.CAPDU;
import at.o2xfs.emv.icc.InternalAuthenticate;
import at.o2xfs.emv.icc.RAPDU;
import at.o2xfs.emv.tlv.TLV;

import java.io.IOException;
import java.util.Arrays;

public class DynamicDataAuthentication
		extends OfflineDynamicDataAuthentication {

	private byte[] authenticationData = null;

	public DynamicDataAuthentication(EMVTransaction transaction) {
		super(transaction);
	}

	public void perform() throws DDAFailedException, TerminateSessionException {
		try {
			retrieveICCPublicKey();
			processDDOL();
			byte[] signedDynamicApplicationData = internalAuthenticate();
			recoverData(signedDynamicApplicationData);
			checkHashResult();
			storeICCDynamicNumber();
		} catch (OfflineDynamicDataAuthenticationFailedException e) {
			throw new DDAFailedException(e);
		}
	}

	private void processDDOL() throws DDAFailedException {
		try {
			byte[] ddol = transaction.getData(EMVTag.DYNAMIC_DATA_AUTHENTICATION_DATA_OBJECT_LIST);
			DOL objectList = new DOL(ddol);
			objectList.put(EMVTag.UNPREDICTABLE_NUMBER, transaction.getMandatoryData(EMVTag.UNPREDICTABLE_NUMBER));
			authenticationData = objectList.construct();
		} catch (NoSuchEntryException e) {
			throw new DDAFailedException("DDOL does not include the Unpredictable Number");
		} catch (DataNotFoundException e) {
			// TODO: default DDOL
			throw new DDAFailedException("ICC does not contain a DDOL");
		}
	}

	private byte[] internalAuthenticate() throws DDAFailedException, TerminateSessionException {
		try {
			CAPDU command = new InternalAuthenticate(authenticationData);
			RAPDU response = transaction.getICReader().transmit(command);
			new ProcessingState(response.getSW()).assertSuccessful();
			TLV tlv = TLV.parse(response.getData());
			if (EMVTag.RESPONSE_MESSAGE_TEMPLATE_FORMAT_1.getTag().equals(tlv.getTag())) {
				return tlv.getValue();
			}
			return new Template(tlv).getMandatoryValue(EMVTag.SIGNED_DYNAMIC_APPLICATION_DATA);
		} catch (IOException e) {
			throw new DDAFailedException(e);
		} catch (ProcessingStateException e) {
			throw new DDAFailedException(e);
		} catch (TLVConstraintViolationException e) {
			throw new TerminateSessionException(e);
		}
	}

	private void checkHashResult() throws OfflineDynamicDataAuthenticationFailedException {
		byte[] hashInput = createHashInput();
		try {
			CryptoFactory cryptoFactory = CryptoFactory.getInstance();
			Crypto crypto = cryptoFactory.newCrypto();
			byte[] hashResult = crypto.digest(recoveredData.get(HASH_ALGORITHM_INDICATOR), hashInput);
			if (!Arrays.equals(hashResult, recoveredData.get(HASH_RESULT))) {
				throw new OfflineDynamicDataAuthenticationFailedException("Hash mismatch");
			}
		} catch (CryptoException e) {
			throw new OfflineDynamicDataAuthenticationFailedException(e);
		}
	}

	private byte[] createHashInput() {
		byte[] dataElements = recoveredData.concatenate(SIGNED_DATA_FORMAT, PAD_PATTERN);
		return Bytes.concat(dataElements, authenticationData);
	}

	private void storeICCDynamicNumber() {
		byte[] iccDynamicData = recoveredData.get(ICC_DYNAMIC_DATA);
		int length = Bytes.toInt(iccDynamicData[0]);
		byte[] iccDynamicNumber = new byte[length];
		System.arraycopy(iccDynamicData, 1, iccDynamicNumber, 0, length);
		transaction.putData(EMVTag.ICC_DYNAMIC_NUMBER, iccDynamicNumber);
	}
}