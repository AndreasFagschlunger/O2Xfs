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

import at.o2xfs.common.Hex;
import at.o2xfs.emv.Candidate.CandidateBuilder;
import at.o2xfs.emv.icc.CAPDU;
import at.o2xfs.emv.icc.ICReader;
import at.o2xfs.emv.icc.RAPDU;
import at.o2xfs.emv.tlv.TLV;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class CandidateList {

	private static final Logger LOG = LoggerFactory.getLogger(CandidateList.class);

	private final Terminal terminal;

	private final ICReader icReader;

	private final List<Candidate> candidates;

	protected CandidateList(Terminal terminal, ICReader icReader) {
		this.terminal = terminal;
		this.icReader = icReader;
		candidates = new ArrayList<Candidate>();
	}

	public List<Candidate> build() throws TerminateSessionException, IOException {
		tryPSE();
		if (candidates.isEmpty()) {
			tryAIDs();
		}
		return new ArrayList<Candidate>(candidates);
	}

	private void tryPSE() throws TerminateSessionException, IOException {
		final List<Candidate> pseCandidates = new PSECandidateList(icReader).build();
		for (Candidate candidate : pseCandidates) {
			final byte[] dfName = candidate.getDFName();
			for (final Application application : terminal.getApplications()) {
				if (application.matches(dfName)) {
					candidates.add(candidate);
				}
			}
		}
	}

	private void tryAIDs() throws IOException, TerminateSessionException {
		for (Application application : terminal.getApplications()) {
			final byte[] aid = application.getAID();
			ADF adf = selectAID(SelectCommand.selectFirstByName(aid));
			if (adf == null) {
				continue;
			}
			if (Arrays.equals(aid, adf.getDFName())) {
				addCandidate(adf);
			} else if (application.matches(adf.getDFName())) {
				while (adf != null) {
					addCandidate(adf);
					adf = selectAID(SelectCommand.selectNextByName(aid));
				}
			}
		}
	}

	private ADF selectAID(CAPDU command) throws TerminateSessionException, IOException {
		final String method = "selectAID(CAPDU)";
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "command=" + command);
			}
			RAPDU response = icReader.transmit(command);
			new ProcessingState(response.getSW()).assertSuccessful();
			return new ADF(TLV.parse(response.getData()));
		} catch (FunctionNotSupportedException e) {
			throw new TerminateSessionException("p2=" + command.getP2() + ", AID: " + Hex.encode(command.getData()), e);
		} catch (FileNotFoundException e) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "File not found: p2=" + command.getP2() + ", AID: " + Hex.encode(command.getData()));
			}
		} catch (ProcessingStateException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(	method,
							"Error selecting file: p2=" + command.getP2() + ", AID: " + Hex.encode(command.getData()),
							e);
			}
		} catch (TLVConstraintViolationException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Illegal FCI: p2=" + command.getP2() + ", AID: " + Hex.encode(command.getData()), e);
			}
		}
		return null;
	}

	private void addCandidate(final ADF adf) {
		final CandidateBuilder builder = new Candidate.CandidateBuilder(adf.getDFName(), adf.getLabel());
		final ProprietaryTemplate template = adf.getProprietaryTemplate();
		builder.languagePreferences(template.getLanguagePreferences());
		if (adf.getPreferredName() != null && template.getIssuerCodeTableIndex() != null) {
			builder.preferredName(adf.getPreferredName(), template.getIssuerCodeTableIndex());
		}
		if (adf.getPriorityIndicator() != null) {
			builder.priorityIndicator(adf.getPriorityIndicator().intValue());
		}
		if (adf.getPDOL() != null) {
			builder.pdol(adf.getPDOL());
		}
		candidates.add(builder.build());
	}

}