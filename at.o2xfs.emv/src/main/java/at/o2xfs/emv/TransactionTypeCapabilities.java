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

import at.o2xfs.common.Bit;
import at.o2xfs.common.Hex;

public class TransactionTypeCapabilities {

	private final byte b1;

	private final byte b2;

	private TransactionTypeCapabilities(byte b1, byte b2) {
		this.b1 = b1;
		this.b2 = b2;
	}

	/**
	 * Cash
	 *
	 * @return
	 */
	public boolean isCash() {
		return Bit.isSet(b1, Bit.B8);
	}

	/**
	 * Goods
	 *
	 * @return
	 */
	public boolean isGoods() {
		return Bit.isSet(b1, Bit.B7);
	}

	/**
	 * Services
	 *
	 * @return
	 */
	public boolean isServices() {
		return Bit.isSet(b1, Bit.B6);
	}

	/**
	 * Cashback
	 *
	 * @return
	 */
	public boolean isCashback() {
		return Bit.isSet(b1, Bit.B5);
	}

	/**
	 * Inquiry
	 *
	 * @return
	 */
	public boolean isInquiry() {
		return Bit.isSet(b1, Bit.B4);
	}

	/**
	 * Transfer
	 *
	 * @return
	 */
	public boolean isTransfer() {
		return Bit.isSet(b1, Bit.B3);
	}

	/**
	 * Payment
	 */
	public boolean isPayment() {
		return Bit.isSet(b1, Bit.B2);
	}

	/**
	 * Administrative
	 */
	public boolean isAdministrative() {
		return Bit.isSet(b1, Bit.B1);
	}

	/**
	 * Cash Deposit
	 */
	public boolean isCashDeposit() {
		return Bit.isSet(b2, Bit.B8);
	}

	public static final TransactionTypeCapabilities create(byte[] bytes) {
		if (bytes.length != 2) {
			throw new IllegalArgumentException("Illegal TransactionType: "
					+ Hex.encode(bytes));
		}
		return new TransactionTypeCapabilities(bytes[0], bytes[1]);
	}
}