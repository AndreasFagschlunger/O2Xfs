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
import at.o2xfs.emv.icc.CAPDU;
import at.o2xfs.emv.icc.RAPDU;
import at.o2xfs.emv.tlv.TLV;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

class InitiateApplicationProcessing {

	private static final Logger LOG = LoggerFactory.getLogger(InitiateApplicationProcessing.class);

	private final EMVTransaction transaction;

	private final Candidate candidate;

	public InitiateApplicationProcessing(EMVTransaction transaction, Candidate candidate) {
		this.transaction = transaction;
		if (candidate == null) {
			throw new NullPointerException("Candidate must not be null");
		}
		this.candidate = candidate;
	}

	public boolean perform() throws IOException, TerminateSessionException {
		if (selectCandidate()) {
			ProcessingOptions processingOptions = new ProcessingOptions(transaction);
			return processingOptions.execute(candidate);
		}
		return false;
	}

	private boolean selectCandidate() throws IOException {
		final String method = "selectCandidate()";
		try {
			CAPDU command = SelectCommand.selectFirstByName(candidate.getDFName());
			RAPDU response = transaction.getICReader().transmit(command);
			new ProcessingState(response.getSW()).assertSuccessful();
			ADF file = new ADF(TLV.parse(response.getData()));
			if (Arrays.equals(candidate.getDFName(), file.getDFName())) {
				transaction.putData(EMVTag.DEDICATED_FILE_NAME, file.getDFName());
				return true;
			}
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "DF Name returned by ICC (" + Hex.encode(file.getDFName())
									+ " does not match AID ("
									+ Hex.encode(candidate.getDFName())
									+ ")");
			}
		} catch (ProcessingStateException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error selecting Candidate: " + Hex.encode(candidate.getDFName()), e);
			}
		} catch (TLVConstraintViolationException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Invalid response", e);
			}
		}
		return false;
	}
}