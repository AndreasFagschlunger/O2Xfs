/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.emv;

import at.o2xfs.common.Bit;
import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Candidate {

	static class CandidateBuilder {

		private final byte[] dfName;

		private final String label;

		private final List<String> languagePreferences;

		private byte[] applicationPreferredName = Bytes.EMPTY;

		private Integer issuerCodeTableIndex = null;

		private int applicationPriorityIndicator = 0;

		private byte[] pdol = Bytes.EMPTY;

		public CandidateBuilder(byte[] dfName, String label) {
			this.dfName = Bytes.copy(dfName);
			this.label = label;
			languagePreferences = new ArrayList<String>();
		}

		public CandidateBuilder languagePreferences(final List<String> aLanguagePreferences) {
			languagePreferences.addAll(aLanguagePreferences);
			return this;
		}

		public CandidateBuilder preferredName(byte[] preferredName, Integer codeTableIndex) {
			if (preferredName == null) {
				throw new NullPointerException("preferredName must not be null");
			} else if (codeTableIndex == null) {
				throw new NullPointerException("codeTableIndex must not be null");
			}
			issuerCodeTableIndex = codeTableIndex;
			applicationPreferredName = Bytes.copy(preferredName);
			return this;
		}

		public CandidateBuilder priorityIndicator(int aApplicationPriorityIndicator) {
			this.applicationPriorityIndicator = aApplicationPriorityIndicator;
			return this;
		}

		public CandidateBuilder pdol(byte[] aPdol) {
			this.pdol = aPdol;
			return this;
		}

		public Candidate build() {
			return new Candidate(this);
		}

	}

	private final byte[] dfName;

	private final String label;

	private final List<String> languagePreferences;

	private final byte[] applicationPreferredName;

	private final Integer issuerCodeTableIndex;

	private final int applicationPriorityIndicator;

	private final byte[] pdol;

	private Candidate(CandidateBuilder builder) {
		this.dfName = builder.dfName;
		this.label = builder.label;
		languagePreferences = builder.languagePreferences;
		issuerCodeTableIndex = builder.issuerCodeTableIndex;
		applicationPreferredName = builder.applicationPreferredName;
		applicationPriorityIndicator = builder.applicationPriorityIndicator;
		pdol = builder.pdol;
	}

	public byte[] getRID() {
		return Bytes.left(dfName, 5);
	}

	public byte[] getDFName() {
		return Bytes.copy(dfName);
	}

	public String getLabel() {
		return label;
	}

	public List<String> getLanguagePreferences() {
		return new ArrayList<String>(languagePreferences);
	}

	public Integer getIssuerCodeTableIndex() {
		return issuerCodeTableIndex;
	}

	public byte[] getApplicationPreferredName() {
		return Bytes.copy(applicationPreferredName);
	}

	public boolean isConfirmationRequired() {
		return Bit.isSet((byte) applicationPriorityIndicator, Bit.B8);
	}

	public int getPriority() {
		return applicationPriorityIndicator;
	}

	public byte[] getPDOL() {
		return Bytes.copy(pdol);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("dfName", Hex.encode(dfName))
										.append("label", label)
										.append("languagePreferences", languagePreferences)
										.append("applicationPreferredName", Hex.encode(applicationPreferredName))
										.append("issuerCodeTableIndex", issuerCodeTableIndex)
										.append("applicationPriorityIndicator", applicationPriorityIndicator)
										.append("pdol", Hex.encode(pdol))
										.toString();
	}
}