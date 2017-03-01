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

public final class TVRByte4 extends TVRByte {

	TVRByte4(TVR tvr) {
		super(tvr, 3);
	}

	/**
	 * Transaction exceeds floor limit
	 */
	public void transactionExceedsFloorLimit() {
		setBit(Bit.B8, "Transaction exceeds floor limit");
	}

	/**
	 * Lower consecutive offline limit exceeded
	 */
	public void lowerConsecutiveOfflineLimitExceeded() {
		setBit(Bit.B7, "Lower consecutive offline limit exceeded");
	}

	/**
	 * Upper consecutive offline limit exceeded
	 */
	public void upperConsecutiveOfflineLimitExceeded() {
		setBit(Bit.B6, "Upper consecutive offline limit exceeded");
	}

	/**
	 * Transaction selected randomly for online processing
	 */
	public void transactionSelectedRandomlyForOnlineProcessing() {
		setBit(Bit.B5, "Transaction selected randomly for online processing");
	}

	/**
	 * Merchant forced transaction online
	 */
	public void merchantForcedTransactionOnline() {
		setBit(Bit.B4, "Merchant forced transaction online");
	}
}