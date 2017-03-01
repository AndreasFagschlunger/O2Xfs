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

package at.o2xfs.emv.icc.atr;

import at.o2xfs.common.Bit;
import at.o2xfs.common.Hex;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

public class ATRValidator {

	private static final Logger LOG = LoggerFactory
			.getLogger(ATRValidator.class);

	private final ATR atr;

	public ATRValidator(final ATR atr) {
		if (atr == null) {
			throw new NullPointerException("ATR must not be null");
		}
		this.atr = atr;
	}

	public void validate() throws ATRValidatorException {
		validateTA1();
		validateTD1();
		validateTA2();
		validateTB2();
		validateTC2();
		validateTD2();
		validateTA3();
		validateTC3();
	}

	private void validateTA1() throws ATRValidatorException {
		if (!atr.containsTA(1) || !atr.containsTA(2)) {
			return;
		}
		final byte ta1 = atr.getTA(1);
		if (ta1 < 0x11 || ta1 > 0x13) {
			throw new ATRValidatorException("TA1 out of range: "
					+ Hex.encode(ta1));
		}
	}

	private void validateTD1() throws ATRValidatorException {
		if (!atr.containsTD(1)) {
			return;
		}
		final byte td1 = atr.getTD(1);
		final byte loNibble = (byte) (td1 & 0xF);
		if (loNibble != 0 && loNibble != 1) {
			throw new ATRValidatorException(
					"TD1 low-order nibble must contain either 0 or 1: TD1="
							+ Hex.encode(td1));
		}
	}

	private void validateTA2() throws ATRValidatorException {
		if (!atr.containsTA(2)) {
			return;
		}
		final byte ta2 = atr.getTA(2);
		if (Bit.isSet(ta2, Bit.B5)) {
			throw new ATRValidatorException("Bit 5 in TA2 is set: "
					+ Hex.encode(ta2));
		}
	}

	private void validateTB2() throws ATRValidatorException {
		if (!atr.containsTB(2)) {
			return;
		}
		final byte tb2 = atr.getTB(2);
		throw new ATRValidatorException("TB2 is present: " + Hex.encode(tb2));
	}

	private void validateTC2() throws ATRValidatorException {
		if (!atr.containsTC(2)) {
			return;
		}
		final byte tc2 = atr.getTC(2);
		if (tc2 == 0) {
			throw new ATRValidatorException("Invalid value for TC2: "
					+ Hex.encode(tc2));
		}
	}

	private void validateTD2() throws ATRValidatorException {
		if (!atr.containsTD(2)) {
			return;
		}
		final byte td2 = atr.getTD(2);
		final byte loNibble = (byte) (td2 & 0xF);
		if (loNibble == 0x1) {
			return;
		}
		final byte td1 = atr.getTD(1);
		if ((td1 & 0xF) == 0 && td2 == 0xE) {
			return;
		}
		throw new ATRValidatorException("Invalid value for TD2: "
				+ Hex.encode(td2));
	}

	private void validateTA3() throws ATRValidatorException {
		if (!atr.containsTA(3)) {
			return;
		}
		final int ta3 = atr.getTA(3) & 0xFF;
		if (ta3 <= 0x0F || ta3 == 0xFF) {
			throw new ATRValidatorException("Invalid value for TA3: "
					+ Hex.encode((byte) ta3));
		}
	}

	private void validateTC3() throws ATRValidatorException {
		if (!atr.containsTC(3)) {
			return;
		}
		final byte tc3 = atr.getTC(3);
		if (tc3 != 0) {
			throw new ATRValidatorException("Invalid value for TC3: "
					+ Hex.encode(tc3));
		}
	}
}