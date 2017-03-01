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

import java.math.BigInteger;

import at.o2xfs.common.Bytes;
import at.o2xfs.emv.util.BinaryNumber;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

final class TerminalRiskManagement {

	private static final Logger LOG = LoggerFactory
			.getLogger(TerminalRiskManagement.class);

	private final EMVTransaction transaction;

	protected TerminalRiskManagement(EMVTransaction transaction) {
		this.transaction = transaction;
	}

	void perform() {
		// FIXME:
		// doFloorLimitChecking();
		new RandomTransactionSelection(transaction).perform();
		new VelocityChecking(transaction).doVelocityChecking();
	}

	private void doFloorLimitChecking() {
		final String method = "doFloorLimitChecking()";
		TransactionLogFactory factory = TransactionLogFactory.getFactory();
		TransactionLog transactionLog = factory.createTransactionLog();
		byte[] pan = transaction
				.getMandatoryData(EMVTag.APPLICATION_PRIMARY_ACCOUNT_NUMBER);
		byte[] panSequenceNumber = getPANSequenceNumber();
		BigInteger floorLimit = BinaryNumber.toBigInteger(transaction
				.getMandatoryData(EMVTag.TERMINAL_FLOOR_LIMIT));
		BigInteger authorisedAmount = BigInteger.ZERO; // FIXME
		try {
			BigInteger amount = transactionLog.find(pan, panSequenceNumber);
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "Amount from log: " + amount);
			}
			authorisedAmount = authorisedAmount.add(amount);
		} catch (TransactionNotFoundException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "No log entry found");
			}
		}
		if (authorisedAmount.compareTo(floorLimit) >= 0) {
			TVR tvr = transaction.getTVR();
			tvr.getByte4().transactionExceedsFloorLimit();
		}
	}

	private byte[] getPANSequenceNumber() {
		try {
			return transaction
					.getData(EMVTag.APPLICATION_PRIMARY_ACCOUNT_NUMBER_SEQUENCE_NUMBER);
		} catch (DataNotFoundException e) {
			return Bytes.EMPTY;
		}
	}
}