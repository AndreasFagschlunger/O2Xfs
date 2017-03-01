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

import java.io.IOException;

import at.o2xfs.emv.icc.CAPDU;
import at.o2xfs.emv.icc.ExternalAuthenticate;
import at.o2xfs.emv.icc.RAPDU;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

class IssuerAuthentication {

	private static final Logger LOG = LoggerFactory
			.getLogger(IssuerAuthentication.class);

	private final EMVTransaction transaction;

	public IssuerAuthentication(EMVTransaction transaction) {
		this.transaction = transaction;
	}

	void perform(byte[] issuerAuthenticationData)
			throws TerminateSessionException {
		final String method = "perform(byte[])";
		if (!transaction.getAIP().isIssuerAuthentication()) {
			LOG.info(method, "ICC does not support issuer authentication");
			return;
		}
		doExternalAuthenticate(issuerAuthenticationData);
	}

	private void doExternalAuthenticate(byte[] iad)
			throws TerminateSessionException {
		try {
			CAPDU command = new ExternalAuthenticate(iad);
			RAPDU response = transaction.getICReader().transmit(command);
			new ProcessingState(response.getSW()).assertSuccessful();
		} catch (ProcessingStateException e) {
			if (e instanceof ConditionsOfUseNotSatisfiedException) {
				throw new TerminateSessionException(e);
			}
			transaction.getTVR().getByte5().issuerAuthenticationFailed();
		} catch (IOException e) {
			throw new TerminateSessionException(e);
		}
		transaction.getTSI().getByte1().issuerAuthenticationWasPerformed();
	}
}