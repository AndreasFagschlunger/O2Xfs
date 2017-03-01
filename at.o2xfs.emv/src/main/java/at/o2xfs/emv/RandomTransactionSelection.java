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
import java.util.Random;

import at.o2xfs.emv.util.BinaryNumber;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

final class RandomTransactionSelection {

	private static final Logger LOG = LoggerFactory
			.getLogger(RandomTransactionSelection.class);

	private final EMVTransaction transaction;

	/**
	 * Amount, Authorised
	 */
	private BigInteger amount = null;

	/**
	 * Terminal Floor Limit
	 */
	private BigInteger floorLimit = null;

	/**
	 * Target Percentage to be Used for Random Selection
	 */
	private int targetPercentage = -1;

	/**
	 * Maximum Target Percentage to be used for Biased Random Selection
	 */
	private int maxTargetPercentage = -1;

	/**
	 * Threshold Value for Biased Random Selection
	 */
	private BigInteger thresholdValue = null;

	private int randomNumber = -1;

	RandomTransactionSelection(EMVTransaction transaction) {
		this.transaction = transaction;
	}

	void perform() {
		final String method = "perform()";
		try {
			prerequisites();
			if (amount.compareTo(thresholdValue) < 0) {
				randomSelection();
			} else {
				biasedRandomSelection();
			}
		} catch (DataNotFoundException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Data not found: " + e.getEMVTag(), e);
			}
		}
	}

	private void prerequisites() throws DataNotFoundException {
		final String method = "prerequisites()";
		amount = BinaryNumber.toBigInteger(transaction
				.getData(EMVTag.AMOUNT_AUTHORISED_BINARY));
		floorLimit = BinaryNumber.toBigInteger(transaction
				.getData(EMVTag.TERMINAL_FLOOR_LIMIT));
		RandomTransactionSelectionData selectionData = transaction
				.getApplication().getRandomTransactionSelectionData();
		targetPercentage = selectionData.getTargetPercentage();
		maxTargetPercentage = selectionData.getMaxTargetPercentage();
		thresholdValue = BinaryNumber.toBigInteger(selectionData
				.getThresholdValue());
		if (thresholdValue.compareTo(floorLimit) >= 0) {
			if (LOG.isWarnEnabled()) {
				LOG.warn(method, "Threshold Value (" + thresholdValue
						+ ") is greater or equals to floor limit ("
						+ floorLimit + ")");
			}
			thresholdValue = BigInteger.ZERO;
		}
		randomNumber = generateRandomNumber();
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "Amount: " + amount + ", Floor Limit: "
					+ floorLimit + ", Target Percentage: " + targetPercentage
					+ ", Max Target Percentage: " + maxTargetPercentage
					+ ", Threshold Value: " + thresholdValue
					+ ", Random Number: " + randomNumber);
		}
	}

	private void biasedRandomSelection() {
		double factor = interpolationFactor();
		int transactionTargetPercent = (int) (((maxTargetPercentage - targetPercentage) * factor) + targetPercentage);
		if (randomNumber <= transactionTargetPercent) {
			selectForOnlineProcessing();
		}
	}

	private double interpolationFactor() {
		double dividend = amount.subtract(thresholdValue).doubleValue();
		double divisor = floorLimit.subtract(thresholdValue).doubleValue();
		return dividend / divisor;
	}

	private void randomSelection() {
		final String method = "randomSelection()";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "Target Percentage: " + targetPercentage
					+ ", Random number: " + randomNumber);
		}
		if (randomNumber <= targetPercentage) {
			selectForOnlineProcessing();
		}
	}

	private void selectForOnlineProcessing() {
		TVR tvr = transaction.getTVR();
		tvr.getByte4().transactionSelectedRandomlyForOnlineProcessing();
	}

	private int generateRandomNumber() {
		return new Random().nextInt(99) + 1;
	}
}