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
import at.o2xfs.emv.cvm.PINEnciphermentException;
import at.o2xfs.emv.pinpad.PINEntryBypassedException;
import at.o2xfs.emv.pinpad.PINPad;
import at.o2xfs.emv.pinpad.PINPadException;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

public class PlaintextPINCVMethod extends OfflinePINCVMethod {

	private static final Logger LOG = LoggerFactory
			.getLogger(PlaintextPINCVMethod.class);

	@Override
	public boolean isSupported(EMVTransaction transaction) {
		Terminal terminal = transaction.getTerminal();
		TerminalCapabilities tCapabilities = terminal.getTerminalCapabilities();
		TerminalCVMCapabilities cvmCapabilities = tCapabilities
				.getCVMCapabilities();
		return cvmCapabilities.isPlaintextPINForICCVerification();
	}

	@Override
	protected CVMResult doPerform() {
		final String method = "doPerform()";
		try {
			checkPINTryCounter();
			PINPad pinPad = transaction.getPINPad();
			byte[] pinBlock = pinPad.getPlaintextPIN();
			checkPlaintextPINBlock(pinBlock);
			verify(0x80, pinBlock);
			return CVMResult.SUCCESSFUL;
		} catch (PINEntryBypassedException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "Plaintext PIN entry has been bypassed", e);
			}
			TVR tvr = transaction.getTVR();
			tvr.getByte3().pinWasNotEntered();
		} catch (PINPadException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error getting plaintext PIN", e);
			}
			TVR tvr = transaction.getTVR();
			tvr.getByte3().pinPadNotPresent();
		} catch (PINEnciphermentException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error verifying plaintext PIN", e);
			}
		}
		return CVMResult.FAILED;
	}

	private void checkPlaintextPINBlock(byte[] pinBlock) throws PINPadException {
		if (pinBlock == null) {
			throw new PINPadException("PIN Block is null");
		} else if (pinBlock.length != 8) {
			throw new PINPadException("Illegal PIN Block size: "
					+ pinBlock.length);
		}
	}

}