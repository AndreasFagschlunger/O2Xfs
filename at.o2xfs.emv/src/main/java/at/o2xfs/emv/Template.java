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

public class Template {

	protected final TLV template;

	public Template(final TLV template) {
		this.template = template;
	}

	public TLV getTLV() {
		return template;
	}

	/**
	 *
	 * @param tag
	 * @throws TLVConstraintViolationException
	 *             if this Template is not the expected Tag
	 */
	public void assertTag(EMVTag tag) throws TLVConstraintViolationException {
		assertTag(tag, template);
	}

	public void assertContainsTag(EMVTag tag)
			throws TLVConstraintViolationException {
		if (!containsTag(tag)) {
			throw new TLVConstraintViolationException("Missing Tag: " + tag,
					template);
		}
	}

	private void assertTag(EMVTag emvTag, TLV tlv)
			throws TLVConstraintViolationException {
		if (!emvTag.getTag().equals(tlv.getTag())) {
			throw new TLVConstraintViolationException("Expected "
					+ emvTag.getTag() + " but was " + tlv.getTag(), tlv);
		}
	}

	public TLV getMandatory(EMVTag emvTag)
			throws TLVConstraintViolationException {
		TLV tlv = findTag(emvTag);
		if (tlv == null) {
			throw new TLVConstraintViolationException("Tag not found: "
					+ emvTag, template);
		}
		return tlv;
	}

	public byte[] getMandatoryValue(EMVTag emvTag)
			throws TLVConstraintViolationException {
		return getMandatory(emvTag).getValue();
	}

	/**
	 * Returns the value for a specified Tag.
	 *
	 * @param tag
	 *            the Tag for which the value should be returned
	 * @return the value for the specified Tag or <code>null</code>
	 */
	public byte[] getValue(EMVTag tag) {
		final TLV tlv = findTag(tag);
		if (tlv != null) {
			return tlv.getValue();
		}
		return null;
	}

	/**
	 * Checks if the specified Tag exists in this Template.
	 *
	 * @param tag
	 *            Tag whose presence in this Template is to be tested
	 * @return true if this Template contains the specified Tag.
	 */
	public boolean containsTag(EMVTag tag) {
		return findTag(tag) != null;
	}

	// TODO: Check algorithm - recursive?
	public TLV findTag(EMVTag emvTag) {
		if (emvTag.getTag().equals(template.getTag())) {
			return template;
		}
		for (TLV child : template.getChildren()) {
			if (emvTag.getTag().equals(child.getTag())) {
				return child;
			}
		}
		return null;
	}
}