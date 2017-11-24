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

package at.o2xfs.xfs.v3_30.idc;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.win32.LPSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.Struct;
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.WORD;

public class EmvClessUI330 extends Struct {

	protected final WORD messageId = new WORD();
	protected final WORD status = new WORD();
	protected final ULONG holdTime = new ULONG();
	protected final WORD valueQualifier = new WORD();
	protected final LPSTR value = new LPSTR();
	protected final LPSTR currencyCode = new LPSTR();
	protected final LPSTR languagePreferenceData = new LPSTR();

	protected EmvClessUI330() {
		add(messageId);
		add(status);
		add(holdTime);
		add(valueQualifier);
		add(value);
		add(currencyCode);
		add(languagePreferenceData);
	}

	public EmvClessUI330(Pointer p) {
		this();
		assignBuffer(p);
	}

	public EmvClessUI330(EmvClessUI330 copy) {
		this();
		allocate();
		set(copy);
	}

	protected void set(EmvClessUI330 copy) {
		messageId.set(copy.getMessageId());
		status.set(copy.getStatus());
		holdTime.set(copy.getHoldTime());
		valueQualifier.set(copy.getValueQualifier());
		value.set(copy.getValue());
		currencyCode.set(copy.getCurrencyCode());
		languagePreferenceData.set(copy.getLanguagePreferenceData());
	}

	public int getMessageId() {
		return messageId.get();
	}

	public int getStatus() {
		return status.get();
	}

	public long getHoldTime() {
		return holdTime.get();
	}

	public int getValueQualifier() {
		return valueQualifier.get();
	}

	public String getValue() {
		return value.get();
	}

	public String getCurrencyCode() {
		return currencyCode.get();
	}

	public String getLanguagePreferenceData() {
		return languagePreferenceData.get();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getMessageId())
				.append(getStatus())
				.append(getHoldTime())
				.append(getValueQualifier())
				.append(getValue())
				.append(getCurrencyCode())
				.append(getLanguagePreferenceData())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EmvClessUI330) {
			EmvClessUI330 emvClessUI330 = (EmvClessUI330) obj;
			return new EqualsBuilder()
					.append(getMessageId(), emvClessUI330.getMessageId())
					.append(getStatus(), emvClessUI330.getStatus())
					.append(getHoldTime(), emvClessUI330.getHoldTime())
					.append(getValueQualifier(), emvClessUI330.getValueQualifier())
					.append(getValue(), emvClessUI330.getValue())
					.append(getCurrencyCode(), emvClessUI330.getCurrencyCode())
					.append(getLanguagePreferenceData(), emvClessUI330.getLanguagePreferenceData())
					.isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("messageId", getMessageId())
				.append("status", getStatus())
				.append("holdTime", getHoldTime())
				.append("valueQualifier", getValueQualifier())
				.append("value", getValue())
				.append("currencyCode", getCurrencyCode())
				.append("languagePreferenceData", getLanguagePreferenceData())
				.toString();
	}
}
