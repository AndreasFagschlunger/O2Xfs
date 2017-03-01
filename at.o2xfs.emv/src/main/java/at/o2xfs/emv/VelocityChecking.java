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

import at.o2xfs.emv.icc.GetDataCommand;
import at.o2xfs.emv.icc.RAPDU;
import at.o2xfs.emv.tlv.TLV;
import at.o2xfs.emv.util.BinaryNumber;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

final class VelocityChecking {

	private static final Logger LOG = LoggerFactory
			.getLogger(VelocityChecking.class);

	private final EMVTransaction transaction;

	VelocityChecking(EMVTransaction transaction) {
		this.transaction = transaction;
	}

	void doVelocityChecking() {
		final String method = "doVelocityChecking()";
		try {
			int lowerLimit = BinaryNumber.toInt(transaction
					.getData(EMVTag.LOWER_CONSECUTIVE_OFFLINE_LIMIT));
			int upperLimit = BinaryNumber.toInt(transaction
					.getData(EMVTag.UPPER_CONSECUTIVE_OFFLINE_LIMIT));
			int lastOnlineATC = readLastOnlineATC();
			if (lastOnlineATC == 0) {
				indicateNewCard();
			}
			int atc = readATC();
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "ATC: " + atc + ", Last Online ATC: "
						+ lastOnlineATC);
			}
			int consecutiveOffline = atc - lastOnlineATC;
			TVR tvr = transaction.getTVR();
			if (consecutiveOffline > lowerLimit) {
				tvr.getByte4().lowerConsecutiveOfflineLimitExceeded();
			}
			if (consecutiveOffline > upperLimit) {
				tvr.getByte4().upperConsecutiveOfflineLimitExceeded();
			}
		} catch (IOException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error reading data from ICC", e);
			}
		} catch (ProcessingStateException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error reading data from ICC", e);
			}
			TVR tvr = transaction.getTVR();
			tvr.getByte4().lowerConsecutiveOfflineLimitExceeded();
			tvr.getByte4().upperConsecutiveOfflineLimitExceeded();
		} catch (DataNotFoundException e) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "Data not found: " + e.getEMVTag());
			}
		}
	}

	private int readATC() throws ProcessingStateException, IOException {
		final String method = "readATC()";
		try {
			return BinaryNumber
					.toInt(read(EMVTag.APPLICATION_TRANSACTION_COUNTER));
		} catch (ProcessingStateException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "ATC is missing");
			}
			TVR tvr = transaction.getTVR();
			tvr.getByte1().iccDataMissing();
			throw e;
		}
	}

	private int readLastOnlineATC() throws ProcessingStateException,
			IOException {
		final String method = "readLastOnlineATC()";
		try {
			return BinaryNumber.toInt(read(EMVTag.LAST_ONLINE_ATC_REGISTER));
		} catch (ProcessingStateException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "Last Online ATC Register is missing");
			}
			throw e;
		}
	}

	private byte[] read(EMVTag emvTag) throws IOException,
			ProcessingStateException {
		GetDataCommand getDataCommand = GetDataCommand.create(emvTag.getTag());
		RAPDU response = transaction.getICReader().transmit(getDataCommand);
		new ProcessingState(response.getSW()).assertSuccessful();
		return TLV.parse(response.getData()).getValue();
	}

	private void indicateNewCard() {
		final String method = "indicateNewCard()";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, "New card");
		}
		TVR tvr = transaction.getTVR();
		tvr.getByte2().newCard();
	}
}