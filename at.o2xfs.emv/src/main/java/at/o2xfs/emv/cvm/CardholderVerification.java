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

package at.o2xfs.emv.cvm;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;
import at.o2xfs.emv.AIP;
import at.o2xfs.emv.DataNotFoundException;
import at.o2xfs.emv.EMVTag;
import at.o2xfs.emv.EMVTransaction;
import at.o2xfs.emv.TVR;
import at.o2xfs.emv.TerminateSessionException;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

public class CardholderVerification {

	private static final Logger LOG = LoggerFactory
			.getLogger(CardholderVerification.class);

	private final EMVTransaction transaction;

	private final List<CVRule> cvRules;

	private final CVMResults cvmResults;

	private BigInteger xAmount = null;

	private BigInteger yAmount = null;

	public CardholderVerification(EMVTransaction transaction) {
		this.transaction = transaction;
		cvRules = new ArrayList<CVRule>();
		cvmResults = new CVMResults();
	}

	public void perform() throws TerminateSessionException {
		final String method = "perform()";
		AIP aip = new AIP(
				transaction
						.getMandatoryData(EMVTag.APPLICATION_INTERCHANGE_PROFILE));
		if (!aip.isCardholderVerification()) {
			cvmResults.noCVMPerformed();
		} else {
			try {
				byte[] cvm = getCVMList();
				xAmount = new BigInteger(Bytes.left(cvm, 4));
				yAmount = new BigInteger(Bytes.mid(cvm, 4, 4));
				parseCVMList(Bytes.right(cvm, cvm.length - 8));
				processCVMList();
				if (CVMResult.FAILED.equals(cvmResults.getResult())) {
					transaction.getTVR().getByte3()
							.cardholderVerificationNotSuccessful();
				}
				transaction.getTSI().getByte1()
						.cardholderVerificationWasPerformed();
			} catch (CVMListNotPresentException e) {
				if (LOG.isInfoEnabled()) {
					LOG.info(method, "CVM List is not present", e);
				}
				TVR tvr = transaction.getTVR();
				tvr.getByte1().iccDataMissing();
				cvmResults.noCVMPerformed();
			}
		}
		putCVMResults();
	}

	private void putCVMResults() {
		transaction.putData(EMVTag.CARDHOLDER_VERIFICATION_METHOD_RESULTS,
				cvmResults.getBytes());
	}

	private byte[] getCVMList() throws CVMListNotPresentException {
		try {
			byte[] cvm = transaction
					.getData(EMVTag.CARDHOLDER_VERIFICATION_METHOD_LIST);
			if (cvm.length <= 8) {
				throw new CVMListNotPresentException(
						"No Cardholder Verification Rules: " + Hex.encode(cvm));
			}
			return cvm;
		} catch (DataNotFoundException e) {
			throw new CVMListNotPresentException(e);
		}
	}

	private void parseCVMList(byte[] cvm) throws TerminateSessionException {
		final String method = "parseCVMList(byte[])";
		if (cvm.length % 2 != 0) {
			throw new TerminateSessionException("Odd number of bytes: "
					+ Hex.encode(cvm));
		}
		for (int offset = 0; offset < cvm.length;) {
			byte[] rule = new byte[2];
			System.arraycopy(cvm, offset, rule, 0, rule.length);
			offset += rule.length;
			CVRule cvRule = new CVRule(rule);
			if (LOG.isInfoEnabled()) {
				LOG.info(method,
						"CVRule: " + cvRule.getCVMCode() + ", Condition: "
								+ Hex.encode(cvRule.getCVMConditionCode()));
			}
			cvRules.add(cvRule);
		}
	}

	private void processCVMList() {
		final String method = "processCVMList()";
		cvmResults.noCVMPerformed(); // no conditions satisfied
		cvmResults.setResult(CVMResult.FAILED);
		for (CVRule cvRule : cvRules) {
			if (!checkCondition(cvRule)) {
				continue;
			}
			try {
				CVMethod cvMethod = CVMethods.getCVMethod(cvRule.getCVMCode());
				boolean isSupported = cvMethod.isSupported(transaction);
				if (LOG.isDebugEnabled()) {
					LOG.debug(method, "cvMethod=" + cvMethod + ",isSupported="
							+ isSupported);
				}
				if (!isSupported) {
					continue;
				}
				CVMResult cvmResult = cvMethod.perform(transaction);
				if (LOG.isDebugEnabled()) {
					LOG.debug(method, "cvMethod=" + cvMethod + ",cvmResult="
							+ cvmResult);
				}
				cvmResults.setCVRule(cvRule);
				cvmResults.setResult(cvmResult);
				if (CVMResult.SUCCESSFUL.equals(cvmResult)
						|| CVMResult.UNKNOWN.equals(cvmResult)) {
					break;
				} else if (CVMCode.FAIL_CVM_PROCESSING == cvRule.getCVMCode()) {
					break;
				}
			} catch (NoSuchCVMethodException e) {
				if (LOG.isInfoEnabled()) {
					LOG.info(method,
							"Unrecognised CVM: " + Hex.encode(e.getCVMCode()));
				}
				if (cvRule.getCVMConditionCode() != 0x03) {
					TVR tvr = transaction.getTVR();
					tvr.getByte3().unrecognisedCVM();
				}
			}
			if (cvRule.failIfUnsuccessful()) {
				break;
			}
		}
	}

	private boolean checkCondition(CVRule cvRule) {
		final String method = "checkCondition(CVRule)";
		int conditionCode = cvRule.getCVMConditionCode();
		CVMCondition cvmCondition = CVMConditions.getCondition(conditionCode);
		if (cvmCondition == null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method,
						"Unknown Condition: " + Hex.encode(conditionCode));
			}
			return false;
		}
		ConditionData conditionData = new ConditionData.Builder()
				.cvRule(cvRule).xAmount(xAmount).yAmount(yAmount)
				.transaction(transaction).build();
		boolean satisfied = cvmCondition.isSatisfied(conditionData);
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "conditionCode=" + Hex.encode(conditionCode)
					+ ",satisfied=" + satisfied);
		}
		return satisfied;
	}
}