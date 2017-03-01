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

import java.io.IOException;

import at.o2xfs.emv.EMVTag;
import at.o2xfs.emv.EMVTransaction;
import at.o2xfs.emv.ProcessingState;
import at.o2xfs.emv.ProcessingStateException;
import at.o2xfs.emv.TVR;
import at.o2xfs.emv.cvm.CVMResult;
import at.o2xfs.emv.cvm.CVMethod;
import at.o2xfs.emv.cvm.PINEnciphermentException;
import at.o2xfs.emv.icc.CAPDU;
import at.o2xfs.emv.icc.GetDataCommand;
import at.o2xfs.emv.icc.RAPDU;
import at.o2xfs.emv.icc.VerifyCommand;
import at.o2xfs.emv.tlv.TLV;
import at.o2xfs.emv.util.BinaryNumber;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

abstract class OfflinePINCVMethod implements CVMethod {

	private static final Logger LOG = LoggerFactory
			.getLogger(OfflinePINCVMethod.class);

	protected EMVTransaction transaction = null;

	@Override
	public final CVMResult perform(EMVTransaction transaction) {
		this.transaction = transaction;
		return doPerform();
	}

	protected abstract CVMResult doPerform();

	protected void checkPINTryCounter() throws PINEnciphermentException {
		final String method = "checkPINTryCounter()";
		CAPDU command = GetDataCommand.create(EMVTag.PIN_TRY_COUNTER.getTag());
		try {
			RAPDU response = transaction.getICReader().transmit(command);
			new ProcessingState(response.getSW()).assertSuccessful();
			int ptc = BinaryNumber.toInt(TLV.parse(response.getData())
					.getValue());
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "PTC: " + ptc);
			}
			if (ptc == 0) {
				TVR tvr = transaction.getTVR();
				tvr.getByte3().pinTryLimitExceeded();
				throw new PINEnciphermentException("PTC is 0");
			}
		} catch (ProcessingStateException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Get PTC failed", e);
			}
		} catch (IOException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Get PTC failed", e);
			}
		}
	}

	protected void verify(int p2, byte[] pinData)
			throws PINEnciphermentException {
		try {
			CAPDU command = new VerifyCommand(p2, pinData);
			RAPDU response = transaction.getICReader().transmit(command);
			new ProcessingState(response.getSW()).assertSuccessful();
		} catch (ProcessingStateException e) {
			checkPINTryLimit(e.getSW());
			throw new PINEnciphermentException(e);
		} catch (IOException e) {
			throw new PINEnciphermentException(e);
		}
	}

	private void checkPINTryLimit(int sw) {
		final String method = "checkPINTryLimit(int)";
		boolean exceeded = false;
		if (sw == 0x6983 || sw == 0x6984) {
			exceeded = true;
		} else if ((sw & 0x63C0) == 0x63C0) {
			int ptc = sw & 0x0F;
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "New PTC: " + ptc);
			}
			exceeded = ptc == 0;
		}
		if (exceeded) {
			TVR tvr = transaction.getTVR();
			tvr.getByte3().pinTryLimitExceeded();
		}
	}
}