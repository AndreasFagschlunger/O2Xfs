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

import java.io.IOException;

import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;
import at.o2xfs.emv.icc.CAPDU;
import at.o2xfs.emv.icc.RAPDU;
import at.o2xfs.emv.tlv.TLV;
import at.o2xfs.emv.tlv.TLVBuilder;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

final class ProcessingOptions {

	private static final Logger LOG = LoggerFactory
			.getLogger(ProcessingOptions.class);

	private final EMVTransaction transaction;

	private AIP aip = null;

	private AFL afl = null;

	ProcessingOptions(EMVTransaction transaction) {
		this.transaction = transaction;
	}

	boolean execute(Candidate candidate) throws TerminateSessionException,
			IOException {
		final String method = "execute(Candidate)";
		try {
			byte[] pdol = new DOLBuilder(transaction, transaction
					.getCandidate().getPDOL()).build();
			TLV template = TLVBuilder.newPrimitive(
					EMVTag.COMMAND_TEMPLATE.getTag(), pdol);
			final CAPDU command = new CAPDU(0x80, 0xA8, 0x00, 0x00,
					template.getBytes(), 0);
			final RAPDU response = transaction.getICReader().transmit(command);
			new ProcessingState(response.getSW()).assertSuccessful();
			TLV data = TLV.parse(response.getData());
			EMVTag tag = EMVTag.getByTag(data.getTag());
			switch (tag) {
				case RESPONSE_MESSAGE_TEMPLATE_FORMAT_1:
					parseFormat1(data);
					break;
				case RESPONSE_MESSAGE_TEMPLATE_FORMAT_2:
					parseFormat2(data);
					break;
				default:
					throw new TerminateSessionException("Invalid response: "
							+ Hex.encode(response.getData()));
			}
			transaction.putData(EMVTag.APPLICATION_INTERCHANGE_PROFILE,
					aip.getBytes());
			transaction.putData(EMVTag.APPLICATION_FILE_LOCATOR, afl.getAFL());
			return true;
		} catch (ConditionsOfUseNotSatisfiedException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info(method, e.getMessage());
			}
		} catch (ProcessingStateException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Illegal Status Bytes", e);
			}
			throw new TerminateSessionException(e);
		}
		return false;
	}

	private void parseFormat1(final TLV tlv) throws TerminateSessionException {
		byte[] format1 = tlv.getValue();
		aip = new AIP(Bytes.left(format1, 2));
		try {
			afl = new AFL(Bytes.right(format1, format1.length - 2));
		} catch (AFLException e) {
			throw new TerminateSessionException(e);
		}
	}

	private void parseFormat2(final TLV data) throws TerminateSessionException {
		try {
			Template format2 = new Template(data);
			TLV aipTag = format2
					.getMandatory(EMVTag.APPLICATION_INTERCHANGE_PROFILE);
			aip = new AIP(aipTag.getValue());
			TLV aflTag = format2.getMandatory(EMVTag.APPLICATION_FILE_LOCATOR);
			afl = new AFL(aflTag.getValue());
		} catch (AFLException e) {
			throw new TerminateSessionException(e);
		} catch (TLVConstraintViolationException e) {
			throw new TerminateSessionException(e);
		}
	}
}