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

import at.o2xfs.emv.Candidate.CandidateBuilder;
import at.o2xfs.emv.icc.CAPDU;
import at.o2xfs.emv.icc.ICReader;
import at.o2xfs.emv.icc.RAPDU;
import at.o2xfs.emv.icc.ReadRecord;
import at.o2xfs.emv.tlv.TLV;
import at.o2xfs.emv.util.Charsets;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

class PSECandidateList {

	private static final Logger LOG = LoggerFactory.getLogger(PSECandidateList.class);

	private static final String PSE = "1PAY.SYS.DDF01";

	private final ICReader icReader;

	private final List<Candidate> candidates;

	private PSE pse = null;

	protected PSECandidateList(ICReader icReader) {
		this.icReader = icReader;
		this.candidates = new ArrayList<Candidate>();
	}

	public List<Candidate> build() throws TerminateSessionException, IOException {
		final String method = "build()";
		try {
			selectPSE();
			readRecords();
		} catch (FileNotFoundException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "No PSE present");
			}
		} catch (FunctionNotSupportedException e) {
			throw new TerminateSessionException(e);
		} catch (ProcessingStateException e) {
			if (LOG.isWarnEnabled()) {
				LOG.warn(method, "Error selecting PSE", e);
			}
		} catch (TLVConstraintViolationException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Illegal PSE", e);
			}
		}
		return candidates;
	}

	private void selectPSE() throws IOException, ProcessingStateException, TLVConstraintViolationException {
		try {
			byte[] fileName = PSE.getBytes(Charsets.US_ASCII);
			final CAPDU command = SelectCommand.selectFirstByName(fileName);
			final RAPDU response = icReader.transmit(command);
			new ProcessingState(response.getSW()).assertSuccessful();
			pse = new PSE(TLV.parse(response.getData()));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private void readRecords() throws IOException {
		final String method = "readRecords()";
		int recordNumber = 1;
		try {
			for (; recordNumber <= 10; recordNumber++) {
				readRecord(recordNumber, pse.getSFI());
			}
		} catch (RecordNotFoundException e) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "Last record number: " + recordNumber);
			}
		} catch (ProcessingStateException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error reading record " + recordNumber, e);
			}
			candidates.clear();
		}
	}

	private void readRecord(int recordNumber, byte sfi) throws ProcessingStateException, IOException {
		final String method = "readRecord(int, byte)";
		int p2 = sfi << 3 | 0x04;
		final CAPDU command = new ReadRecord(recordNumber, p2);
		final RAPDU response = icReader.transmit(command);
		new ProcessingState(response.getSW()).assertSuccessful();
		final TLV psDirectory = TLV.parse(response.getData());
		if (!EMVTag.READ_RECORD.getTag().equals(psDirectory.getTag())) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Illegal Payment System Directory: " + psDirectory);
			}
			return;
		}
		for (TLV entry : psDirectory.getChildren()) {
			if (!EMVTag.APPLICATION_TEMPLATE.getTag().equals(entry.getTag())) {
				if (LOG.isDebugEnabled()) {
					LOG.debug(method, "Uknown record entry: " + entry.getTag());
				}
				continue;
			}
			try {
				final ADFEntry adfEntry = new ADFEntry(entry);
				final Candidate candidate = buildCandidate(adfEntry);
				candidates.add(candidate);
			} catch (TLVConstraintViolationException e) {
				if (LOG.isErrorEnabled()) {
					LOG.error(method, "Illegal directory entry", e);
				}
			}
		}
	}

	private Candidate buildCandidate(final ADFEntry adfEntry) {
		final CandidateBuilder builder = new CandidateBuilder(adfEntry.getADFName(), adfEntry.getLabel());
		final ProprietaryTemplate template = pse.getProprietaryTemplate();
		builder.languagePreferences(template.getLanguagePreferences());
		if (adfEntry.getPreferredName() != null && template.getIssuerCodeTableIndex() != null) {
			builder.preferredName(adfEntry.getPreferredName(), template.getIssuerCodeTableIndex().intValue());
		}
		if (adfEntry.getPriorityIndicator() != null) {
			builder.priorityIndicator(adfEntry.getPriorityIndicator().intValue());
		}
		return builder.build();
	}
}