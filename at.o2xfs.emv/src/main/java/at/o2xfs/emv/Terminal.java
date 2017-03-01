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

import java.util.ArrayList;
import java.util.List;

public class Terminal {

	private final TerminalIdentification terminalIdentification;

	private final TerminalType terminalType;

	private final TerminalCapabilities terminalCapabilities;

	private final AdditionalTerminalCapabilities additionalCapabilities;

	private final CountryCode countryCode;

	private final MerchantIdentifier merchantIdentifier;

	private final List<Application> applications;

	public Terminal(TerminalIdentification terminalIdentification,
			TerminalType terminalType,
			TerminalCapabilities terminalCapabilities,
			AdditionalTerminalCapabilities additionalTerminalCapabilities,
			CountryCode countryCode, MerchantIdentifier merchantIdentifier,
			List<Application> applications) {
		this.terminalIdentification = terminalIdentification;
		this.terminalType = terminalType;
		this.terminalCapabilities = terminalCapabilities;
		this.additionalCapabilities = additionalTerminalCapabilities;
		this.countryCode = countryCode;
		this.merchantIdentifier = merchantIdentifier;
		this.applications = new ArrayList<Application>(applications);
	}

	public TerminalIdentification getTerminalIdentification() {
		return terminalIdentification;
	}

	public TerminalType getTerminalType() {
		return terminalType;
	}

	public TerminalCapabilities getTerminalCapabilities() {
		return terminalCapabilities;
	}

	public AdditionalTerminalCapabilities getAdditionalCapabilities() {
		return additionalCapabilities;
	}

	public CountryCode getCountryCode() {
		return countryCode;
	}

	public MerchantIdentifier getMerchantIdentifier() {
		return merchantIdentifier;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public boolean isATM() {
		return terminalType.isUnattended()
				&& terminalType.isFinancialInstitution()
				&& additionalCapabilities.getTransactionTypeCapabilities()
						.isCash();
	}
}