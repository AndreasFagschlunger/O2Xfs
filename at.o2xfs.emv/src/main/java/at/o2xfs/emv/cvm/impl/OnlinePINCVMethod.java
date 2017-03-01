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

import at.o2xfs.emv.EMVTransaction;
import at.o2xfs.emv.TVR;
import at.o2xfs.emv.Terminal;
import at.o2xfs.emv.TerminalCVMCapabilities;
import at.o2xfs.emv.TerminalCapabilities;
import at.o2xfs.emv.cvm.CVMResult;
import at.o2xfs.emv.cvm.CVMethod;
import at.o2xfs.emv.pinpad.PINEntryBypassedException;
import at.o2xfs.emv.pinpad.PINPad;
import at.o2xfs.emv.pinpad.PINPadException;

public class OnlinePINCVMethod implements CVMethod {

	@Override
	public boolean isSupported(EMVTransaction transaction) {
		Terminal terminal = transaction.getTerminal();
		TerminalCapabilities tCapabilities = terminal.getTerminalCapabilities();
		TerminalCVMCapabilities cvmCapabilities = tCapabilities
				.getCVMCapabilities();
		return cvmCapabilities.isEncipheredPINForOnlineVerification();
	}

	@Override
	public CVMResult perform(EMVTransaction transaction) {
		PINPad pinPad = transaction.getPINPad();
		TVR tvr = transaction.getTVR();
		try {
			pinPad.getOnlinePIN();
			tvr.getByte3().onlinePINEntered();
			return CVMResult.UNKNOWN;
		} catch (PINEntryBypassedException e) {
			tvr.getByte3().pinWasNotEntered();
		} catch (PINPadException e) {
			tvr.getByte3().pinPadNotPresent();
		}
		return CVMResult.FAILED;
	}
}