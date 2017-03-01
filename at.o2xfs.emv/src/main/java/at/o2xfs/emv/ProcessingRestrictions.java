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

import at.o2xfs.common.Hex;
import at.o2xfs.emv.ExpirationDate.ExpirationDateException;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

class ProcessingRestrictions {

	private static final Logger LOG = LoggerFactory
			.getLogger(ProcessingRestrictions.class);

	private final EMVTransaction transaction;

	public ProcessingRestrictions(EMVTransaction transaction) {
		this.transaction = transaction;
	}

	public void perform() throws TerminateSessionException {
		checkApplicationVersionNumber();
		checkApplicationUsageControl();
		checkApplicationEffectiveDate();
		checkApplicationExpirationDate();
	}

	private void checkApplicationVersionNumber() {
		final String method = "checkApplicationVersionNumber()";
		try {
			byte[] iccVersion = transaction
					.getData(EMVTag.APPLICATION_VERSION_NUMBER_ICC);
			// TODO: Mandatory?
			byte[] terminalVersion = transaction
					.getData(EMVTag.APPLICATION_VERSION_NUMBER_TERMINAL);
			if (LOG.isInfoEnabled()) {
				LOG.info(
						method,
						"Application Version Numbers ICC: "
								+ Hex.encode(iccVersion) + ", Terminal: "
								+ Hex.encode(terminalVersion));
			}
			if (!Arrays.equals(iccVersion, terminalVersion)) {
				TVR tvr = transaction.getTVR();
				tvr.getByte2().differentApplicationVersions();
			}
		} catch (DataNotFoundException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "Data not found: " + e.getEMVTag());
			}
		}
	}

	private void checkApplicationUsageControl() {
		final String method = "checkApplicationUsageControl()";
		try {
			Terminal terminal = transaction.getTerminal();
			ApplicationUsageControl auc = new ApplicationUsageControl(
					transaction.getData(EMVTag.APPLICATION_USAGE_CONTROL));
			if (terminal.isATM() && !auc.validAtATMs()) {
				serviceNotAllowed("Not valid at ATMs");
			} else if (!terminal.isATM()
					&& !auc.validAtTerminalsOtherThanATMs()) {
				serviceNotAllowed("Not valid at terminals other than ATMs");
			} else if (transaction.containsData(EMVTag.ISSUER_COUNTRY_CODE)) {
				TransactionType transactionType = new TransactionType(
						transaction.getMandatoryData(EMVTag.TRANSACTION_TYPE));
				if (transactionType.isCashTransaction()) {
					if (isDomestic() && !auc.validForDomesticCash()) {
						serviceNotAllowed("Not valid for domestic cash transactions");
					} else if (!isDomestic()
							&& !auc.validForInternationalCash()) {
						serviceNotAllowed("Not valid for international cash transactions");
					}
				} else if (transactionType.isPurchase()) {
					if (isDomestic()) {
						if (hasCashbackAmount()
								&& !auc.isDomesticCashbackAllowed()) {
							serviceNotAllowed("Domestic cashback is not allowed");
						} else if (!auc.validForDomesticGoods()
								&& !auc.validForDomesticServices()) {
							serviceNotAllowed("Not valid for domestic goods and services");
						} else if (!auc.validForDomesticGoods()) {
							serviceNotAllowed("Not valid for domestic goods");
						} else if (!auc.validForDomesticServices()) {
							serviceNotAllowed("Not valid for domestic services");
						}
					} else {
						if (hasCashbackAmount()
								&& !auc.isInternationalCashbackAllowed()) {
							serviceNotAllowed("International cashback is not allowed");
						} else if (!auc.validForInternationalGoods()
								&& !auc.validForInternationalServices()) {
							serviceNotAllowed("Not valid for international goods and services");
						} else if (!auc.validForInternationalGoods()) {
							serviceNotAllowed("Not valid for international goods");
						} else if (!auc.validForInternationalServices()) {
							serviceNotAllowed("Not valid for international services");
						}
					}
				}

			}
		} catch (DataNotFoundException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "Application Usage Control is not present");
			}
		}
	}

	private boolean hasCashbackAmount() {
		return transaction.containsData(EMVTag.AMOUNT_OTHER_BINARY)
				|| transaction.containsData(EMVTag.AMOUNT_AUTHORISED_NUMERIC);
	}

	private boolean isDomestic() {
		byte[] terminalCountryCode = transaction
				.getMandatoryData(EMVTag.TERMINAL_COUNTRY_CODE);
		byte[] issuerCountryCode = transaction
				.getMandatoryData(EMVTag.ISSUER_COUNTRY_CODE);
		return Arrays.equals(terminalCountryCode, issuerCountryCode);
	}

	private void serviceNotAllowed(String description) {
		if (LOG.isInfoEnabled()) {
			final String method = "serviceNotAllowed(String)";
			LOG.info(method, description);
		}
		TVR tvr = transaction.getTVR();
		tvr.getByte2().requestedServiceNotAllowed();
	}

	private void checkApplicationEffectiveDate()
			throws TerminateSessionException {
		final String method = "checkApplicationEffectiveDate()";
		try {
			byte[] effectiveDate = transaction
					.getData(EMVTag.APPLICATION_EFFECTIVE_DATE);
			if (!new ExpirationDate(effectiveDate).hasExpired()) {
				TVR tvr = transaction.getTVR();
				tvr.getByte2().applicationNotYetEffective();
			}
		} catch (DataNotFoundException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "Application Effective Date is not present");
			}
		} catch (ExpirationDateException e) {
			throw new TerminateSessionException(e);
		}
	}

	private void checkApplicationExpirationDate()
			throws TerminateSessionException {
		try {
			byte[] applicationExpirationDate = transaction
					.getMandatoryData(EMVTag.APPLICATION_EXPIRATION_DATE);
			if (new ExpirationDate(applicationExpirationDate).hasExpired()) {
				TVR tvr = transaction.getTVR();
				tvr.getByte2().expiredApplication();
			}
		} catch (ExpirationDateException e) {
			throw new TerminateSessionException(e);
		}
	}
}