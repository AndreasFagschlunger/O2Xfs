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

import at.o2xfs.emv.dda.DDAFailedException;
import at.o2xfs.emv.dda.DynamicDataAuthentication;
import at.o2xfs.emv.sda.SDAFailedException;
import at.o2xfs.emv.sda.StaticDataAuthentication;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

class OfflineDataAuthentication {

	private static final Logger LOG = LoggerFactory
			.getLogger(OfflineDataAuthentication.class);

	private final EMVTransaction transaction;

	protected OfflineDataAuthentication(EMVTransaction transaction) {
		this.transaction = transaction;
	}

	public void perform() throws TerminateSessionException {
		Terminal terminal = transaction.getTerminal();
		TerminalCapabilities terminalCapabilities = terminal
				.getTerminalCapabilities();
		TerminalSecurityCapabilities securityCapabilities = terminalCapabilities
				.getSecurityCapabilities();
		final AIP aip = new AIP(
				transaction
						.getMandatoryData(EMVTag.APPLICATION_INTERCHANGE_PROFILE));
		if (aip.isCDA() && securityCapabilities.isCDA()) {
			performCDA();
		} else if (aip.isDDA() && securityCapabilities.isDDA()) {
			performDDA();
		} else if (aip.isSDA() && securityCapabilities.isSDA()) {
			performSDA();
		} else {
			transaction.getTVR().getByte1()
					.offlineDataAuthenticationWasNotPerformed();
		}
	}

	private void performCDA() {
		final String method = "performCDA()";
		try {
			new CombinedDataAuthentication(transaction).perform();
		} catch (CDAFailedException e) {
			transaction.getTVR().getByte1().cdaFailed();
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "CDA failed", e);
			}
		}
	}

	private void performDDA() throws TerminateSessionException {
		final String method = "performDDA()";
		try {
			new DynamicDataAuthentication(transaction).perform();
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "DDA successful");
			}
		} catch (DDAFailedException e) {
			transaction.getTVR().getByte1().ddaFailed();
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "DDA failed", e);
			}
		}
		transaction.getTSI().getByte1().offlineDataAuthenticationWasPerformed();
	}

	private void performSDA() {
		final String method = "performSDA()";
		try {
			new StaticDataAuthentication(transaction).perform();
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "SDA successful");
			}
		} catch (SDAFailedException e) {
			transaction.getTVR().getByte1().sdaFailed();
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "SDA failed", e);
			}
		}
		transaction.getTSI().getByte1().offlineDataAuthenticationWasPerformed();
	}
}