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

import at.o2xfs.emv.ApplicationData.ApplicationDataBuilder;
import at.o2xfs.emv.icc.CAPDU;
import at.o2xfs.emv.icc.RAPDU;
import at.o2xfs.emv.icc.ReadRecord;
import at.o2xfs.emv.tlv.TLV;

final class ReadApplicationData {

	private final EMVTransaction transaction;

	private ApplicationDataBuilder builder = null;

	ReadApplicationData(EMVTransaction transaction) {
		this.transaction = transaction;
	}

	ApplicationData perform() throws IOException, TerminateSessionException {
		try {
			builder = new ApplicationDataBuilder();
			AFL afl = new AFL(
					transaction
							.getMandatoryData(EMVTag.APPLICATION_FILE_LOCATOR));
			for (AFL.Entry entry : afl) {
				readRecords(entry);
			}
			return builder.build();
		} catch (AFLException e) {
			throw new RuntimeException(e);
		}
	}

	private void readRecords(final AFL.Entry entry) throws IOException,
			TerminateSessionException {
		int recordsRead = 1;
		for (int recordNumber = entry.getFirstRecordNumber(); recordNumber <= entry
				.getLastRecordNumber(); recordNumber++, recordsRead++) {
			try {
				int p2 = entry.getSFI() << 3 | 0x04;
				final CAPDU command = new ReadRecord(recordNumber, p2);
				final RAPDU response = transaction.getICReader().transmit(
						command);
				new ProcessingState(response.getSW()).assertSuccessful();
				TLV tlv = TLV.parse(response.getData());
				builder.addRecord(tlv);
				if (recordsRead <= entry.getNumberOfRecords()) {
					builder.addAuthenticationData(entry.getSFI(), recordNumber,
							tlv);
				}
			} catch (DuplicatePrimitiveDataException e) {
				throw new TerminateSessionException(e);
			} catch (ProcessingStateException e) {
				throw new TerminateSessionException(e);
			}
		}
	}
}