/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.xfs.pin;

import at.o2xfs.xfs.XfsConstant;

public enum PINFormat implements XfsConstant {

	/**
	 * PIN left justified, filled with padding characters, PIN length 4-16
	 * digits
	 */
	WFS_PIN_FORM3624(0x0001L),

	/**
	 * PIN is preceded by 0x00 and the length of the PIN (0x04 to 0x0C), filled
	 * with padding character 0x0F to the right, PIN length 4-12 digits, XORed
	 * with PAN (Primary Account Number, minimum 12 digits without check number)
	 */
	ANSI(0x0002L),

	/**
	 * PIN is preceded by 0x00 and the length of the PIN (0x04 to 0x0C), filled
	 * with padding character 0x0F to the right, PIN length 4-12 digits, XORed
	 * with PAN (Primary Account Number, no minimum length specified, missing
	 * digits are filled with 0x00)
	 */
	ISO0(0x0004L),

	/**
	 * PIN is preceded by 0x01 and the length of the PIN (0x04 to 0x0C), padding
	 * characters are taken from a transaction field (10 digits)
	 */
	ISO1(0x0008L),

	/**
	 * (similar to {@link #WFS_PIN_FORM3624}), PIN only 4 digits
	 */
	ECI2(0x0010L),

	/**
	 * PIN is preceded by the length (digit), PIN length 4-6 digits, padded with
	 * 0x00
	 */
	ECI3(0x0020L),

	/**
	 * same as {@link #ECI3}
	 */
	VISA(ECI3.getValue()),

	/**
	 * PIN is padded with the padding character and may be not encrypted, single
	 * encrypted or double encrypted.
	 */
	DIEBOLD(0x0080L),

	/**
	 * PIN is preceded by the two-digit coordination number, padded with the
	 * padding character and may be not encrypted, single encrypted or double
	 * encrypted.
	 */
	DIEBOLDCO(0x0100L),

	/**
	 * 
	 */
	VISA3(0x0200L),

	/**
	 * 
	 */
	BANKSYS(0x0400L),

	/**
	 * 
	 */
	EMV(0x0800L),

	/**
	 * 
	 */
	ISO3(0x2000L),

	/**
	 * 
	 */
	AP(0x4000L);

	private final long value;

	private PINFormat(final long value) {
		this.value = value;
	}

	@Override
	public long getValue() {
		return value;
	}

}