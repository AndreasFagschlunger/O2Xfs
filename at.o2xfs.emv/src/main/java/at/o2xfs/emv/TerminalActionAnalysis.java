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

import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

final class TerminalActionAnalysis {

	private static final Logger LOG = LoggerFactory
			.getLogger(TerminalActionAnalysis.class);

	private final EMVTransaction transaction;

	private TerminalActionCodes terminalActionCodes = null;

	private byte[] tvr = null;

	TerminalActionAnalysis(EMVTransaction transaction) {
		this.transaction = transaction;
		terminalActionCodes = transaction.getApplication()
				.getTerminalActionCodes();
	}

	CryptogramType perform(boolean unableToGoOnline) {
		tvr = transaction
				.getMandatoryData(EMVTag.TERMINAL_VERIFICATION_RESULTS);
		TerminalType terminalType = transaction.getTerminal().getTerminalType();
		final CryptogramType result;
		if (isDenialOfTransaction()) {
			result = CryptogramType.AAC;
		} else if (terminalType.isOfflineOnly() || unableToGoOnline) {
			if (transactionShallBeRejected()) {
				result = CryptogramType.AAC;
			} else {
				result = CryptogramType.TC;
			}
		} else if (isCompleteTransactionOnline()) {
			result = CryptogramType.ARQC;
		} else {
			result = CryptogramType.TC;
		}
		setAuthorisationResponseCode(result, unableToGoOnline);
		return result;
	}

	private void setAuthorisationResponseCode(CryptogramType cryptogramType,
			boolean unableToGoOnline) {
		final String method = "setAuthorisationResponseCode(CryptogramType, boolean)";
		AuthorisationResponseCode arc = null;
		switch (cryptogramType) {
			case AAC:
				if (unableToGoOnline) {
					arc = AuthorisationResponseCode.UNABLE_TO_GO_ONLINE_OFFLINE_DECLINED;
				} else {
					arc = AuthorisationResponseCode.OFFLINE_DECLINED;
				}
				break;
			case TC:
				if (unableToGoOnline) {
					arc = AuthorisationResponseCode.UNABLE_TO_GO_ONLINE_OFFLINE_APPROVED;
				} else {
					arc = AuthorisationResponseCode.UNABLE_TO_GO_ONLINE_OFFLINE_DECLINED;
				}
				break;
			default:
				return;
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "Authorisation Response Code: " + arc);
		}
		transaction.putData(EMVTag.AUTHORISATION_RESPONSE_CODE, arc.getBytes());
	}

	private boolean isDenialOfTransaction() {
		final String method = "isDenialOfTransaction()";
		byte[] iac = getIssuerActionCodeDenial();
		byte[] tac = terminalActionCodes.getDenial();
		if (LOG.isDebugEnabled()) {
			logActionCodes(method, iac, tac);
		}
		if (matchesWithTVR(iac)) {
			if (LOG.isInfoEnabled()) {
				LOG.info(method,
						"Reject offline by issuer: TVR: " + Hex.encode(tvr)
								+ ", IAC: " + Hex.encode(iac));
			}
			return true;
		} else if (matchesWithTVR(tac)) {
			if (LOG.isInfoEnabled()) {
				LOG.info(method,
						"Reject offline by acquirer: TVR: " + Hex.encode(tvr)
								+ ", IAC: " + Hex.encode(tac));
			}
			return true;
		}
		return false;
	}

	private void logActionCodes(String method, byte[] iac, byte[] tac) {
		LOG.debug(method, "TVR: " + Bytes.toBinaryString(tvr, ' '));
		LOG.debug(method, "IAC: " + Bytes.toBinaryString(iac, ' '));
		LOG.debug(method, "TAC: " + Bytes.toBinaryString(tac, ' '));
	}

	private byte[] getIssuerActionCodeDenial() {
		final String method = "getIssuerActionCodeDenial()";
		try {
			return transaction.getData(EMVTag.ISSUER_ACTION_CODE_DENIAL);
		} catch (DataNotFoundException e) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "Issuer Action Code - Denial does not exist");
			}
			return defaultIssuerActionCodeDenial();
		}
	}

	private byte[] defaultIssuerActionCodeDenial() {
		return new byte[tvr.length];
	}

	private boolean isCompleteTransactionOnline() {
		final String method = "isCompleteTransactionOnline()";
		TerminalType terminalType = transaction.getTerminal().getTerminalType();
		if (terminalType.isOnlineOnly()) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "Online-only terminal");
			}
			return true;
		}
		byte[] iac = getIssuerActionCodeOnline();
		byte[] tac = terminalActionCodes.getOnline();
		if (LOG.isDebugEnabled()) {
			logActionCodes(method, iac, tac);
		}
		if (matchesWithTVR(iac)) {
			if (LOG.isInfoEnabled()) {
				LOG.info(method,
						"Complete online by issuer: TVR: " + Hex.encode(tvr)
								+ ", IAC: " + Hex.encode(iac));
			}
			return true;
		} else if (matchesWithTVR(tac)) {
			if (LOG.isInfoEnabled()) {
				LOG.info(method,
						"Complete online by acquirer: TVR: " + Hex.encode(tvr)
								+ ", IAC: " + Hex.encode(tac));
			}
			return true;
		}
		return false;
	}

	private byte[] getIssuerActionCodeOnline() {
		final String method = "getIssuerActionCodeOnline()";
		try {
			return transaction.getData(EMVTag.ISSUER_ACTION_CODE_ONLINE);
		} catch (DataNotFoundException e) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "Issuer Action Code - Online does not exist");
			}
			return defaultIssuerActionCodeOnlineAndDefault();
		}
	}

	private byte[] defaultIssuerActionCodeOnlineAndDefault() {
		return Bytes.leftPad(Bytes.EMPTY, tvr.length, 0xFF);
	}

	private boolean matchesWithTVR(byte[] actionCode) {
		for (int i = 0; i < tvr.length; i++) {
			for (int mask = 1; mask <= 128; mask *= 2) {
				if ((tvr[i] & mask) == mask) {
					if ((actionCode[i] & mask) == mask) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean transactionShallBeRejected() {
		final String method = "transactionShallBeRejected()";
		byte[] iac = getIssuerActionCodeDefault();
		byte[] tac = terminalActionCodes.getDefault();
		if (LOG.isDebugEnabled()) {
			logActionCodes(method, iac, tac);
		}
		if (matchesWithTVR(iac)) {
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "Reject by issuer: TVR: " + Hex.encode(tvr)
						+ ", IAC: " + Hex.encode(iac));
			}
			return true;
		}
		if (matchesWithTVR(tac)) {
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "Reject by acquirer: TVR: " + Hex.encode(tvr)
						+ ", IAC: " + Hex.encode(tac));
			}
			return true;
		}
		return false;
	}

	private byte[] getIssuerActionCodeDefault() {
		final String method = "getIssuerActionCodeDefault()";
		try {
			return transaction.getData(EMVTag.ISSUER_ACTION_CODE_DEFAULT);
		} catch (DataNotFoundException e) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "Issuer Action Code - Default does not exist");
			}
			return defaultIssuerActionCodeOnlineAndDefault();
		}
	}

}