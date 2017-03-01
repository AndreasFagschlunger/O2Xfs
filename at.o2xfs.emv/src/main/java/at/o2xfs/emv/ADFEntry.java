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

public class ADFEntry extends Template {

	private byte[] adfName = null;

	private String label = null;

	private byte[] preferredName = null;

	private Integer priorityIndicator = null;

	public ADFEntry(final TLV template) throws TLVConstraintViolationException {
		super(template);
		parse();
	}

	private void parse() throws TLVConstraintViolationException {
		assertTag(EMVTag.APPLICATION_TEMPLATE);
		parseADFName();
		parseLabel();
		parsePreferredName();
		parsePriorityIndicator();
	}

	private void parseADFName() throws TLVConstraintViolationException {
		adfName = getMandatory(EMVTag.ADF).getValue();
	}

	private void parseLabel() throws TLVConstraintViolationException {
		final byte[] bytes = getMandatory(EMVTag.APPLICATION_LABEL).getValue();
		label = new String(bytes);
	}

	private void parsePreferredName() {
		preferredName = getValue(EMVTag.APPLICATION_PREFERRED_NAME);
	}

	private void parsePriorityIndicator() {
		byte[] priorityIndicator = getValue(EMVTag.APPLICATION_PRIORITY_INDICATOR);
		if (priorityIndicator == null) {
			return;
		}
		this.priorityIndicator = Integer.valueOf(Bytes
				.toInt(priorityIndicator[0]));
	}

	public byte[] getADFName() {
		return adfName;
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
}