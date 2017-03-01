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

import java.math.BigInteger;
import java.util.Arrays;

import at.o2xfs.emv.DataNotFoundException;
import at.o2xfs.emv.EMVTag;
import at.o2xfs.emv.EMVTransaction;
import at.o2xfs.emv.cvm.CVMCondition;
import at.o2xfs.emv.cvm.ConditionData;
import at.o2xfs.emv.util.NumericFormat;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

public abstract class AmountAuthorisedCondition implements CVMCondition {

	private static final Logger LOG = LoggerFactory
			.getLogger(AmountAuthorisedCondition.class);

	private EMVTransaction transaction = null;

	protected BigInteger xAmount = null;

	protected BigInteger yAmount = null;

	protected BigInteger amountAuthorised = null;

	@Override
	public final boolean isSatisfied(ConditionData conditionData) {
		final String method = "isSatisfied(ConditionData)";
		transaction = conditionData.getTransaction();
		try {
			if (isSameCurrency()) {
				xAmount = conditionData.getXAmount();
				yAmount = conditionData.getYAmount();
				amountAuthorised = amountAuthorised();
				return isSatisfied();
			}
		} catch (DataNotFoundException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "Required data not present: " + e.getEMVTag());
			}
		}
		return false;
	}

	protected abstract boolean isSatisfied();

	private boolean isSameCurrency() throws DataNotFoundException {
		byte[] transactionCurrencyCode = transaction
				.getData(EMVTag.TRANSACTION_CURRENCY_CODE);
		byte[] applicationCurrencyCode = transaction
				.getData(EMVTag.APPLICATION_CURRENCY_CODE);
		return Arrays.equals(transactionCurrencyCode, applicationCurrencyCode);
	}

	private BigInteger amountAuthorised() throws DataNotFoundException {
		byte[] amountAuthorised = transaction
				.getData(EMVTag.AMOUNT_AUTHORISED_NUMERIC);
		return NumericFormat.parse(amountAuthorised);
	}
}