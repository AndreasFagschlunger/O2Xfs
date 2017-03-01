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

import at.o2xfs.common.Bytes;
import at.o2xfs.emv.tlv.TLV;

public class ProprietaryTemplate extends Template {

	private final List<String> languagePreferences;

	private Integer issuerCodeTableIndex = null;

	ProprietaryTemplate(TLV template) throws TLVConstraintViolationException {
		super(template);
		languagePreferences = new ArrayList<String>();
		parse();
	}

	private void parse() throws TLVConstraintViolationException {
		assertTag(EMVTag.FCI_PROPRIETARY_TEMPLATE);
		parseLanguagePreference();
		parseIssuerCodeTableIndex();
	}

	private void parseLanguagePreference() {
		byte[] languagePreference = getValue(EMVTag.LANGUAGE_PREFERENCE);
		if (languagePreference == null) {
			return;
		}
		final String value = new String(languagePreference);
		for (int i = 0; i < value.length(); i += 2) {
			languagePreferences.add(value.substring(i, i + 2));
		}
	}

	private void parseIssuerCodeTableIndex() {
		byte[] issuerCodeTableIndex = getValue(EMVTag.ISSUER_CODE_TABLE_INDEX);
		if (issuerCodeTableIndex == null) {
			return;
		}
		this.issuerCodeTableIndex = Integer.valueOf(Bytes
				.toInt(issuerCodeTableIndex[0]));
	}

	public List<String> getLanguagePreferences() {
		return languagePreferences;
	}

	public Integer getIssuerCodeTableIndex() {
		return issuerCodeTableIndex;
	}
}