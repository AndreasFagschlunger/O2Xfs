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

public final class ApplicationUsageControl {

	private final byte byte1;

	private final byte byte2;

	public ApplicationUsageControl(byte[] auc) {
		byte1 = auc[0];
		byte2 = auc[1];
	}

	/**
	 * Valid for domestic cash transactions
	 */
	public boolean validForDomesticCash() {
		return Bit.isSet(byte1, Bit.B8);
	}

	/**
	 * Valid for international cash
	 */
	public boolean validForInternationalCash() {
		return Bit.isSet(byte1, Bit.B7);
	}

	/**
	 * Valid for domestic goods
	 */
	public boolean validForDomesticGoods() {
		return Bit.isSet(byte1, Bit.B6);
	}

	/**
	 * Valid for international goods
	 */
	public boolean validForInternationalGoods() {
		return Bit.isSet(byte1, Bit.B5);
	}

	/**
	 * Valid for domestic services
	 */
	public boolean validForDomesticServices() {
		return Bit.isSet(byte1, Bit.B4);
	}

	/**
	 * Valid for international services
	 */
	public boolean validForInternationalServices() {
		return Bit.isSet(byte1, Bit.B3);
	}

	/**
	 * Valid at ATMs
	 */
	public boolean validAtATMs() {
		return Bit.isSet(byte1, Bit.B2);
	}

	/**
	 * Valid at terminals other than ATMs
	 */
	public boolean validAtTerminalsOtherThanATMs() {
		return Bit.isSet(byte1, Bit.B1);
	}

	/**
	 * Domestic cashback allowed
	 */
	public boolean isDomesticCashbackAllowed() {
		return Bit.isSet(byte2, Bit.B8);
	}

	/**
	 * International cashback allowed
	 */
	public boolean isInternationalCashbackAllowed() {
		return Bit.isSet(byte2, Bit.B7);
	}
}