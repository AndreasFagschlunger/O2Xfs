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

package at.o2xfs.emv;

import java.util.Arrays;

import at.o2xfs.common.ByteArrayBuilder;
import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;
import at.o2xfs.emv.crypto.Crypto;
import at.o2xfs.emv.crypto.CryptoException;
import at.o2xfs.emv.crypto.CryptoFactory;
import at.o2xfs.emv.dda.RecoveredSDAData;
import at.o2xfs.emv.tlv.TLV;

/**
 * Combined DDA/Application Cryptogram Generation (CDA)
 *
 * @author Andreas Fagschlunger
 */
final class CombinedDataAuthentication extends OfflineDynamicDataAuthentication {

	private Template generateACResponse;

	private ICCDynamicData iccDynamicData = null;

	public CombinedDataAuthentication(EMVTransaction transaction) {
		super(transaction);
	}

	public void perform() throws CDAFailedException {
		try {
			retrieveICCPublicKey();
		} catch (OfflineDynamicDataAuthenticationFailedException e) {
			throw new CDAFailedException(e);
		}
	}

	public void performDynamicSignatureVerification(Template generateACResponse)
			throws CDAFailedException {
		this.generateACResponse = generateACResponse;
		CryptogramInformationData cid = parseCryptogramInformationData();
		if (isDeclineTransaction(cid)) {
			return;
		}
		try {
			retrieveICCPublicKey();
			recoverData(getSignedDynamicApplicationData());
			iccDynamicData = ICCDynamicData.parse(recoveredData
					.get(RecoveredSDAData.ICC_DYNAMIC_DATA));
			checkCryptogramInformationData();
			checkRecoveredDataHashResult();
			checkTransactionDataHashCode();
			transaction.putData(EMVTag.ICC_DYNAMIC_NUMBER,
					iccDynamicData.getDynamicNumber());
			transaction.putData(EMVTag.APPLICATION_CRYPTOGRAM,
					iccDynamicData.getCryptogram());
		} catch (OfflineDynamicDataAuthenticationFailedException e) {
			throw new CDAFailedException(e);
		}
	}

	private CryptogramInformationData parseCryptogramInformationData()
			throws CDAFailedException {
		try {
			byte[] value = generateACResponse
					.getMandatoryValue(EMVTag.CRYPTOGRAM_INFORMATION_DATA);
			return CryptogramInformationData.parse(value);
		} catch (TLVConstraintViolationException e) {
			throw new CDAFailedException(e);
		}
	}

	private boolean isDeclineTransaction(CryptogramInformationData cid) {
		return CryptogramType.AAC.equals(cid.getCryptogramType());
	}

	private byte[] getSignedDynamicApplicationData() throws CDAFailedException {
		try {
			return generateACResponse
					.getMandatoryValue(EMVTag.SIGNED_DYNAMIC_APPLICATION_DATA);
		} catch (TLVConstraintViolationException e) {
			throw new CDAFailedException(e);
		}
	}

	private void checkCryptogramInformationData() throws CDAFailedException {
		try {
			byte[] cidResponse = generateACResponse
					.getMandatoryValue(EMVTag.CRYPTOGRAM_INFORMATION_DATA);
			byte[] cidDynamicData = iccDynamicData
					.getCryptogramInformationData();
			if (!Arrays.equals(cidResponse, cidDynamicData)) {
				throw new CDAFailedException(
						"Cryptogram Information Data retrieved from the ICC Dynamic Data ("
								+ Hex.encode(cidDynamicData)
								+ ") is not equal to the Cryptogram Information Data obtained from the response to the GENERATE AC command ("
								+ Hex.encode(cidResponse) + ")");
			}
		} catch (TLVConstraintViolationException e) {
			throw new RuntimeException(e);
		}
	}

	private void checkRecoveredDataHashResult() throws CDAFailedException {
		try {
			byte[] hashInput = createRecoveredDataHashInput();
			Crypto crypto = CryptoFactory.getInstance().newCrypto();
			byte[] hashResult = crypto.digest(recoveredData
					.get(RecoveredSDAData.HASH_ALGORITHM_INDICATOR), hashInput);
			if (!Arrays.equals(hashResult,
					recoveredData.get(RecoveredSDAData.HASH_RESULT))) {
				throw new CDAFailedException("Hash mismatch");
			}
		} catch (CryptoException e) {
			throw new CDAFailedException(e);
		}
	}

	private byte[] createRecoveredDataHashInput() {
		byte[] unpredictableNumber = transaction
				.getMandatoryData(EMVTag.UNPREDICTABLE_NUMBER);
		return Bytes.concat(recoveredData.concatenate(
				RecoveredSDAData.SIGNED_DATA_FORMAT,
				RecoveredSDAData.PAD_PATTERN), unpredictableNumber);
	}

	private void checkTransactionDataHashCode() throws CDAFailedException {
		try {
			byte[] hashInput = createTransactionDataHashInput();
			Crypto crypto = CryptoFactory.getInstance().newCrypto();
			byte[] hashCode = crypto.digest(recoveredData
					.get(RecoveredSDAData.HASH_ALGORITHM_INDICATOR), hashInput);
			if (!Arrays.equals(hashCode, iccDynamicData.getHashCode())) {
				throw new CDAFailedException(
						"Transaction Data Hash Code mismatch");
			}
		} catch (CryptoException e) {
			throw new CDAFailedException(e);
		}
	}

	private byte[] createTransactionDataHashInput() {
		ByteArrayBuilder input = new ByteArrayBuilder().append(buildPDOL())
				.append(buildCDOL1());
		for (TLV child : generateACResponse.getTLV().getChildren()) {
			if (EMVTag.SIGNED_DYNAMIC_APPLICATION_DATA.getTag().equals(
					child.getTag())) {
				continue;
			}
			input.append(child.getBytes());
		}
		return input.build();
	}

	private byte[] buildPDOL() {
		return new DOLBuilder(transaction, transaction.getCandidate().getPDOL())
				.build();
	}

	private byte[] buildCDOL1() {
		return new DOLBuilder(
				transaction,
				transaction
						.getMandatoryData(EMVTag.CARD_RISK_MANAGEMENT_DATA_OBJECT_LIST_1))
				.build();
	}
}