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

import at.o2xfs.emv.EMVTransaction;

public final class ConditionData {

	static final class Builder {

		protected CVRule cvRule = null;

		protected BigInteger xAmount = null;

		protected BigInteger yAmount = null;

		protected EMVTransaction transaction = null;

		public Builder cvRule(CVRule cvRule) {
			this.cvRule = cvRule;
			return this;
		}

		public Builder xAmount(BigInteger xAmount) {
			this.xAmount = xAmount;
			return this;
		}

		public Builder yAmount(BigInteger yAmount) {
			this.yAmount = yAmount;
			return this;
		}

		public Builder transaction(EMVTransaction transaction) {
			this.transaction = transaction;
			return this;
		}

		public ConditionData build() {
			return new ConditionData(this);
		}
	}

	private final CVRule cvRule;

	private final BigInteger xAmount;

	private final BigInteger yAmount;

	private final EMVTransaction transaction;

	ConditionData(Builder builder) {
		if (builder.cvRule == null) {
			throw new NullPointerException("cvRule must not be null");
		}
		cvRule = builder.cvRule;
		if (builder.xAmount == null) {
			throw new NullPointerException("xAmount must not be null");
		}
		xAmount = builder.xAmount;
		if (builder.yAmount == null) {
			throw new NullPointerException("yAmount must not be null");
		}
		yAmount = builder.yAmount;
		if (builder.transaction == null) {
			throw new NullPointerException("transaction must not be null");
		}
		transaction = builder.transaction;
	}

	public CVRule getCVRule() {
		return cvRule;
	}

	public BigInteger getXAmount() {
		return xAmount;
	}

	public BigInteger getYAmount() {
		return yAmount;
	}

	public EMVTransaction getTransaction() {
		return transaction;
	}
}