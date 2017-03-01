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

public class TerminalDataOutputCapabilities {

	private final byte b1;

	private final byte b2;

	public TerminalDataOutputCapabilities(byte b1, byte b2) {
		this.b1 = b1;
		this.b2 = b2;
	}

	/**
	 * Print, attendant
	 */
	public boolean isPrintAttendant() {
		return Bit.isSet(b1, Bit.B8);
	}

	/**
	 * Print, cardholder
	 */
	public boolean isPrintCardholder() {
		return Bit.isSet(b1, Bit.B7);
	}

	/**
	 * Display, attendant
	 */
	public boolean isDisplayAttendant() {
		return Bit.isSet(b1, Bit.B6);
	}

	/**
	 * Display, cardholder
	 */
	public boolean isDisplayCardholder() {
		return Bit.isSet(b1, Bit.B5);
	}

	/**
	 * Code table 10
	 */
	public boolean isCodeTable10() {
		return Bit.isSet(b1, Bit.B2);
	}

	/**
	 * Code table 9
	 */
	public boolean isCodeTable9() {
		return Bit.isSet(b1, Bit.B1);
	}

	/**
	 * Code table 8
	 */
	public boolean isCodeTable8() {
		return Bit.isSet(b2, Bit.B8);
	}

	/**
	 * Code table 7
	 */
	public boolean isCodeTable7() {
		return Bit.isSet(b2, Bit.B7);
	}

	/**
	 * Code table 6
	 */
	public boolean isCodeTable6() {
		return Bit.isSet(b2, Bit.B6);
	}

	/**
	 * Code table 5
	 */
	public boolean isCodeTable5() {
		return Bit.isSet(b2, Bit.B5);
	}

	/**
	 * Code table 4
	 */
	public boolean isCodeTable4() {
		return Bit.isSet(b2, Bit.B4);
	}

	/**
	 * Code table 3
	 */
	public boolean isCodeTable3() {
		return Bit.isSet(b2, Bit.B3);
	}

	/**
	 * Code table 2
	 */
	public boolean isCodeTable2() {
		return Bit.isSet(b2, Bit.B2);
	}

	/**
	 * Code table 1
	 */
	public boolean isCodeTable1() {
		return Bit.isSet(b2, Bit.B1);
	}

}