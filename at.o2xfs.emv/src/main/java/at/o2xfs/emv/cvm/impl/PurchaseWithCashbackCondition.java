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

import at.o2xfs.emv.EMVTag;
import at.o2xfs.emv.EMVTransaction;
import at.o2xfs.emv.TransactionType;
import at.o2xfs.emv.cvm.CVMCondition;
import at.o2xfs.emv.cvm.ConditionData;

/**
 * "If purchase with cashback" applies to purchase transactions where the
 * cardholder receives cash in addition to goods or services.
 *
 * @author Andreas Fagschlunger
 *
 */
public class PurchaseWithCashbackCondition implements CVMCondition {

	@Override
	public boolean isSatisfied(ConditionData conditionData) {
		EMVTransaction transaction = conditionData.getTransaction();
		TransactionType transactionType = new TransactionType(
				transaction.getMandatoryData(EMVTag.TRANSACTION_TYPE));
		return transactionType.isPurchase()
				&& (transaction.containsData(EMVTag.AMOUNT_OTHER_BINARY) || transaction
						.containsData(EMVTag.AMOUNT_OTHER_NUMERIC));
	}
}