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

package at.o2xfs.emv.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;
import at.o2xfs.emv.TransactionLog;
import at.o2xfs.emv.TransactionNotFoundException;
import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

/**
 * @deprecated This class stores PANs in plain text, use hash values instead.
 *
 * @author Andreas Fagschlunger
 */
@Deprecated
class DefaultTransactionLog implements TransactionLog {

	private static class TransactionDateComparator implements
			Comparator<TransactionLogEntry> {

		@Override
		public int compare(TransactionLogEntry entry1,
				TransactionLogEntry entry2) {
			return entry1.getTransactionDate().compareTo(
					entry2.getTransactionDate());
		}
	}

	private static final Logger LOG = LoggerFactory
			.getLogger(DefaultTransactionLog.class);

	private static final String DATE_FORMAT = "yyMMdd";

	private static final String FILENAME = "transaction.log";

	private static final String SEPERATOR = "\t";

	private final List<TransactionLogEntry> logEntries;

	public DefaultTransactionLog() {
		logEntries = new ArrayList<TransactionLogEntry>();
		readTransactionLog();
	}

	@Override
	public BigInteger find(byte[] pan, byte[] panSequenceNumber)
			throws TransactionNotFoundException {
		List<TransactionLogEntry> matches = new ArrayList<TransactionLogEntry>();
		Date today = newToday();
		boolean modified = false;
		Iterator<TransactionLogEntry> i = logEntries.iterator();
		while (i.hasNext()) {
			TransactionLogEntry logEntry = i.next();
			if (logEntry.getTransactionDate().compareTo(today) < 0) {
				i.remove();
				modified = true;
			} else if (Arrays.equals(pan, logEntry.getPAN())
					&& Arrays.equals(panSequenceNumber,
							logEntry.getPANSequenceNumber())) {
				matches.add(logEntry);
			}
		}
		if (modified) {
			storeTransactionLog();
		}
		Collections.sort(matches, new TransactionDateComparator());
		if (matches.isEmpty()) {
			throw new TransactionNotFoundException(pan, panSequenceNumber);
		}
		return matches.get(0).getTransactionAmount();
	}

	private Date newToday() {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		return today.getTime();
	}

	@Override
	public void add(byte[] pan, byte[] panSequenceNumber,
			BigInteger transactionAmount) {
		TransactionLogEntry newEntry = new TransactionLogEntry(pan,
				panSequenceNumber, newToday(), transactionAmount);
		logEntries.add(newEntry);
		storeTransactionLog();
	}

	private void readTransactionLog() {
		final String method = "readTransactionLog()";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(FILENAME)));
			String line = null;
			while ((line = reader.readLine()) != null) {
				try {
					TransactionLogEntry logEntry = parseLogEntry(line);
					logEntries.add(logEntry);
				} catch (Exception e) {
					if (LOG.isErrorEnabled()) {
						LOG.error(method, "Error parsing line: " + line, e);
					}
				}
			}
		} catch (IOException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error reading file", e);
			}
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					if (LOG.isErrorEnabled()) {
						LOG.error(method, "Error closing stream", e);
					}
				}
			}
		}
	}

	private TransactionLogEntry parseLogEntry(String line)
			throws ParseException {
		String[] parts = line.split(SEPERATOR);
		Date transactionDate = new SimpleDateFormat(DATE_FORMAT)
				.parse(parts[0]);
		byte[] pan = Hex.decode(parts[1]);
		byte[] panSequenceNumber = Bytes.EMPTY;
		if (!parts[2].trim().isEmpty()) {
			panSequenceNumber = Hex.decode(parts[2]);
		}
		BigInteger transactionAmount = new BigInteger(parts[3]);
		return new TransactionLogEntry(pan, panSequenceNumber, transactionDate,
				transactionAmount);
	}

	private void storeTransactionLog() {
		final String method = "storeTransactionLog()";
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File(FILENAME));
			for (TransactionLogEntry logEntry : logEntries) {
				storeLogEntry(logEntry, writer);
			}
		} catch (IOException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error writing file", e);
			}
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private void storeLogEntry(TransactionLogEntry logEntry, PrintWriter writer) {
		String date = new SimpleDateFormat(DATE_FORMAT).format(logEntry
				.getTransactionDate());
		writer.print(date + SEPERATOR);
		String pan = Hex.encode(Bytes.leftPad(logEntry.getPAN(), 20));
		writer.print(pan + SEPERATOR);
		if (logEntry.getPANSequenceNumber().length == 0) {
			writer.print(" ");
		} else {
			writer.print(Hex.encode(logEntry.getPANSequenceNumber()));
		}
		writer.print(SEPERATOR);
		writer.println(logEntry.getTransactionAmount());
	}
}