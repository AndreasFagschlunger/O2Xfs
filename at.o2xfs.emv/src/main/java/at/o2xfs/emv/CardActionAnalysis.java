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

import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;
import at.o2xfs.emv.crypto.Crypto;
import at.o2xfs.emv.crypto.CryptoException;
import at.o2xfs.emv.crypto.CryptoFactory;
import at.o2xfs.emv.crypto.HashAlgorithm;
import at.o2xfs.emv.icc.CAPDU;
import at.o2xfs.emv.icc.RAPDU;
import at.o2xfs.emv.tlv.TLV;
import at.o2xfs.emv.tlv.Tag;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

import java.io.IOException;

final class CardActionAnalysis {

	private static final Logger LOG = LoggerFactory.getLogger(CardActionAnalysis.class);

	private final EMVTransaction transaction;

	CardActionAnalysis(EMVTransaction transaction) {
		this.transaction = transaction;
	}

	CryptogramInformationData firstGenerateAC(CryptogramType cryptogramType) throws TerminateSessionException,
																			IOException {
		final String method = "firstGenerateAC(CryptogramType)";
		try {
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "CryptogramType: " + cryptogramType);
			}
			byte[] cdol1 = buildCDOL(EMVTag.CARD_RISK_MANAGEMENT_DATA_OBJECT_LIST_1);
			boolean cdaSignature = isCDASignatureRequested(cryptogramType);
			CAPDU generateAC = GenerateACCommand.create(cryptogramType, cdaSignature, cdol1);
			RAPDU response = transaction.getICReader().transmit(generateAC);
			new ProcessingState(response.getSW()).assertSuccessful();
			Template template = parseResponseMessageTemplate(response.getData());
			CryptogramInformationData cid = CryptogramInformationData.parse(template.getMandatoryValue(EMVTag.CRYPTOGRAM_INFORMATION_DATA));
			verifyCryptogram(cryptogramType, cid.getCryptogramType());
			if (cdaSignature && !CryptogramType.AAC.equals(cid.getCryptogramType())) {
				performDynamicSignatureVerification(template);
			}
			transaction.putData(EMVTag.CRYPTOGRAM_INFORMATION_DATA,
								template.getMandatoryValue(EMVTag.CRYPTOGRAM_INFORMATION_DATA));
			transaction.putData(EMVTag.APPLICATION_TRANSACTION_COUNTER,
								template.getMandatoryValue(EMVTag.APPLICATION_TRANSACTION_COUNTER));
			if (template.getValue(EMVTag.APPLICATION_CRYPTOGRAM) != null) {
				transaction.putData(EMVTag.APPLICATION_CRYPTOGRAM, template.getValue(EMVTag.APPLICATION_CRYPTOGRAM));
			}
			if (template.getValue(EMVTag.ISSUER_APPLICATION_DATA) != null) {
				transaction.putData(EMVTag.ISSUER_APPLICATION_DATA, template.getValue(EMVTag.ISSUER_APPLICATION_DATA));
			}
			transaction.getTSI().getByte1().cardRiskManagementWasPerformed();
			return cid;
		} catch (ProcessingStateException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "GENERATE AC failed", e);
			}
			throw new TerminateSessionException(e);
		} catch (TLVConstraintViolationException e) {
			throw new TerminateSessionException(e);
		}
	}

	CryptogramInformationData secondGenerateAC(CryptogramType cryptogramType)	throws TerminateSessionException,
																				IOException {
		final String method = "secondGenerateAC(CryptogramType)";
		try {
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "CryptogramType: " + cryptogramType);
			}
			byte[] cdol2 = buildCDOL(EMVTag.CARD_RISK_MANAGEMENT_DATA_OBJECT_LIST_2);
			CAPDU command = GenerateACCommand.create(cryptogramType, false, cdol2);
			RAPDU response = transaction.getICReader().transmit(command);
			new ProcessingState(response.getSW()).assertSuccessful();
			Template data = parseResponseMessageTemplate(response.getData());
			CryptogramInformationData cid = CryptogramInformationData.parse(data.getMandatoryValue(EMVTag.CRYPTOGRAM_INFORMATION_DATA));
			return cid;
		} catch (ProcessingStateException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Second GENERATE AC failed", e);
			}
			throw new TerminateSessionException(e);
		} catch (TLVConstraintViolationException e) {
			throw new TerminateSessionException(e);
		}

	}

	private void verifyCryptogram(CryptogramType requestCryptogram, CryptogramType responseCryptogram) throws TerminateSessionException {
		if (responseCryptogram == null || responseCryptogram.compareTo(requestCryptogram) > 0) {
			throw new TerminateSessionException("ICC Error: requestCryptogram=" + requestCryptogram
												+ ",responseCryptogram="
												+ responseCryptogram);
		}
	}

	private boolean isCDASignatureRequested(CryptogramType cryptogramType) {
		if (!isCDASupported()) {
			return false;
		}
		TVR tvr = transaction.getTVR();
		if (!tvr.getByte1().isCDAFailed()) {
			if (CryptogramType.AAC.equals(cryptogramType)) {
				tvr.getByte1().offlineDataAuthenticationWasNotPerformed();
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean isCDASupported() {
		AIP aip = new AIP(transaction.getMandatoryData(EMVTag.APPLICATION_INTERCHANGE_PROFILE));
		TerminalCapabilities terminalCapabilities = transaction.getTerminal().getTerminalCapabilities();
		return aip.isCDA() && terminalCapabilities.getSecurityCapabilities().isCDA();
	}

	private Template parseResponseMessageTemplate(byte[] data) throws TerminateSessionException {
		TLV tlv = TLV.parse(data);
		if (EMVTag.RESPONSE_MESSAGE_TEMPLATE_FORMAT_1.getTag().equals(tlv.getTag())) {
			return parseFormat1(tlv.getValue());
		} else if (EMVTag.RESPONSE_MESSAGE_TEMPLATE_FORMAT_2.getTag().equals(tlv.getTag())) {
			return parseFormat2(tlv);
		}
		throw new TerminateSessionException("Illegal GENERATE AC Response Message: " + Hex.encode(data));
	}

	private Template parseFormat1(byte[] value) {
		TLV tlv = new GenerateACFormat1ResponseParser(value).parse();
		return new Template(tlv);
	}

	private Template parseFormat2(TLV tlv) throws TerminateSessionException {
		try {
			Template template = new Template(tlv);
			template.assertContainsTag(EMVTag.CRYPTOGRAM_INFORMATION_DATA);
			template.assertContainsTag(EMVTag.APPLICATION_TRANSACTION_COUNTER);
			return template;
		} catch (TLVConstraintViolationException e) {
			throw new TerminateSessionException(e);
		}
	}

	private byte[] buildCDOL(EMVTag cdol) {
		final String method = "buildCDOL(EMVTag)";
		DOL objectList = new DOL(transaction.getMandatoryData(cdol));
		for (Tag tag : objectList) {
			EMVTag emvTag = EMVTag.getByTag(tag);
			if (emvTag == null) {
				LOG.info(method, "Unkown Tag: " + tag);
				continue;
			}
			if (EMVTag.TRANSACTION_CERTIFICATE_HASH_VALUE.equals(emvTag)) {
				objectList.put(emvTag, generateTCHashValue());
			} else if (transaction.containsData(emvTag)) {
				objectList.put(emvTag, transaction.getMandatoryData(emvTag));
			} else {
				if (LOG.isDebugEnabled()) {
					LOG.debug(method, "No value for " + emvTag);
				}
			}
		}
		return objectList.construct();
	}

	private byte[] generateTCHashValue() {
		final String method = "generateTCHashValue()";
		try {
			byte[] hashInput = new DOLBuilder(transaction, getTDOL()).build();
			Crypto crypto = CryptoFactory.getInstance().newCrypto();
			return crypto.digest(HashAlgorithm.SHA_1.getIndicator(), hashInput);
		} catch (CryptoException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error generating TC Hash Value", e);
			}
		}
		return Bytes.EMPTY;
	}

	private byte[] getTDOL() {
		final String method = "getTDOL()";
		byte[] result;
		try {
			return transaction.getData(EMVTag.TRANSACTION_CERTIFICATE_DATA_OBJECT_LIST);
		} catch (DataNotFoundException e) {
			LOG.info(method, "TDOL not present");
			result = transaction.getApplication().getDefaultTDOL();
			if (result.length != 0) {
				transaction.getTVR().getByte5().defaultTDOLUsed();
			}
		}
		return result;
	}

	private void performDynamicSignatureVerification(Template response) {
		final String method = "performDynamicSignatureVerification(Template)";
		try {
			new CombinedDataAuthentication(transaction).performDynamicSignatureVerification(response);
		} catch (CDAFailedException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "CDA failed", e);
			}
			transaction.getTVR().getByte1().cdaFailed();
		}
	}
}