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

import at.o2xfs.emv.tlv.TLV;

public class PSE extends Template {

	private String dfName = null;

	private byte sfi = 0;

	private ProprietaryTemplate proprietaryTemplate = null;

	public PSE(final TLV fci) throws TLVConstraintViolationException {
		super(fci);
		parse();
	}

	private void parse() throws TLVConstraintViolationException {
		assertTag(EMVTag.FCI_TEMPLATE);
		parseDFName();
		parseProprietaryTemplate();
	}

	private void parseDFName() throws TLVConstraintViolationException {
		final TLV tlv = getMandatory(EMVTag.DEDICATED_FILE_NAME);
		dfName = new String(tlv.getValue());
	}

	private void parseProprietaryTemplate()
			throws TLVConstraintViolationException {
		TLV templateTag = getMandatory(EMVTag.FCI_PROPRIETARY_TEMPLATE);
		proprietaryTemplate = new ProprietaryTemplate(templateTag);
		parseSFI(proprietaryTemplate);

	}

	private void parseSFI(Template tpl) throws TLVConstraintViolationException {
		sfi = tpl.getMandatory(EMVTag.SFI).getValue()[0];
	}

	public String getDFName() {
		return dfName;
	}

	public ProprietaryTemplate getProprietaryTemplate() {
		return proprietaryTemplate;
	}

	public byte getSFI() {
		return sfi;
	}

}