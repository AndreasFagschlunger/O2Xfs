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

import at.o2xfs.common.Bytes;
import at.o2xfs.emv.tlv.TLV;

public class ADF extends Template {

	private byte[] dfName = null;

	private ProprietaryTemplate proprietaryTemplate = null;

	private String label = null;

	private byte[] preferredName = null;

	private Integer priorityIndicator = null;

	private byte[] pdol = null;

	public ADF(final TLV template) throws TLVConstraintViolationException {
		super(template);
		parse();
	}

	private void parse() throws TLVConstraintViolationException {
		assertTag(EMVTag.FCI_TEMPLATE);
		dfName = getMandatoryValue(EMVTag.DEDICATED_FILE_NAME);
		parseProprietaryTemplate();
	}

	private void parseProprietaryTemplate()
			throws TLVConstraintViolationException {
		final TLV templateTag = getMandatory(EMVTag.FCI_PROPRIETARY_TEMPLATE);
		proprietaryTemplate = new ProprietaryTemplate(templateTag);
		parseLabel();
		parseApplicationPriorityIndicator();
		parsePreferredName();
		pdol = proprietaryTemplate
				.getValue(EMVTag.PROCESSING_OPTIONS_DATA_OBJECT_LIST);
	}

	private void parseLabel() throws TLVConstraintViolationException {
		final byte[] applicationLabel = proprietaryTemplate
				.getMandatoryValue(EMVTag.APPLICATION_LABEL);
		label = new String(applicationLabel);
	}

	private void parseApplicationPriorityIndicator() {
		byte[] priorityIndicator = proprietaryTemplate
				.getValue(EMVTag.APPLICATION_PRIORITY_INDICATOR);
		if (priorityIndicator == null) {
			return;
		}
		this.priorityIndicator = Integer.valueOf(Bytes
				.toInt(priorityIndicator[0]));
	}

	private void parsePreferredName() {
		preferredName = proprietaryTemplate
				.getValue(EMVTag.APPLICATION_PREFERRED_NAME);
	}

	public byte[] getDFName() {
		return Bytes.copy(dfName);
	}

	public ProprietaryTemplate getProprietaryTemplate() {
		return proprietaryTemplate;
	}

	public String getLabel() {
		return label;
	}

	public byte[] getPreferredName() {
		return preferredName;
	}

	public Integer getPriorityIndicator() {
		return priorityIndicator;
	}

	public byte[] getPDOL() {
		return pdol;
	}
}