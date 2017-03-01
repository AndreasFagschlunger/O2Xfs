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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;

final class AFL implements Iterable<AFL.Entry> {

	public static class Entry {

		private int sfi = 0;

		private int firstRecordNumber = 0;

		private int lastRecordNumber = 0;

		private int numberOfRecords = 0;

		private Entry() {
			return;
		}

		public int getSFI() {
			return sfi;
		}

		public int getFirstRecordNumber() {
			return firstRecordNumber;
		}

		public int getLastRecordNumber() {
			return lastRecordNumber;
		}

		public int getNumberOfRecords() {
			return numberOfRecords;
		}
	}

	private static final int ENTRY_SIZE = 4;

	private final byte[] afl;

	private final List<Entry> entries;

	public AFL(byte[] afl) throws AFLException {
		entries = new ArrayList<AFL.Entry>();
		this.afl = Bytes.copy(afl);
		parse();
	}

	private void parse() throws AFLException {
		if (afl.length == 0) {
			throw new AFLException("AFL must not be empty.");
		} else if ((afl.length % ENTRY_SIZE) != 0) {
			throw new AFLException("AFL (" + Hex.encode(afl)
					+ ") must be a multiple of " + ENTRY_SIZE);
		}
		for (int i = 0; i < afl.length; i += ENTRY_SIZE) {
			byte[] entry = new byte[ENTRY_SIZE];
			System.arraycopy(afl, i, entry, 0, ENTRY_SIZE);
			parseEntry(entry);
		}
	}

	private void parseEntry(byte[] entry) throws AFLException {
		Entry aflEntry = new Entry();
		aflEntry.sfi = Bytes.toInt(entry[0]) >> 3;
		if (aflEntry.sfi == 0 || aflEntry.sfi == 31) {
			throw new AFLException("Illegal SFI: " + aflEntry.sfi);
		}
		aflEntry.firstRecordNumber = Bytes.toInt(entry[1]);
		if (aflEntry.firstRecordNumber == 0) {
			throw new AFLException("First record number must not be zero.");
		}
		aflEntry.lastRecordNumber = Bytes.toInt(entry[2]);
		if (aflEntry.lastRecordNumber < aflEntry.firstRecordNumber) {
			throw new AFLException("Last record number ("
					+ aflEntry.lastRecordNumber
					+ ") must be greater or equal to first record number ("
					+ aflEntry.firstRecordNumber + ")");
		}
		aflEntry.numberOfRecords = Bytes.toInt(entry[3]);
		if (aflEntry.numberOfRecords > (aflEntry.lastRecordNumber - aflEntry.firstRecordNumber) + 1) {
			throw new AFLException(
					"Number of records participating in offline data authentication ("
							+ aflEntry.numberOfRecords
							+ ") is greater than the number of records ("
							+ aflEntry.firstRecordNumber + "-"
							+ aflEntry.lastRecordNumber + ")");
		}
		entries.add(aflEntry);
	}

	public byte[] getAFL() {
		return Bytes.copy(afl);
	}

	@Override
	public Iterator<Entry> iterator() {
		return entries.iterator();
	}
}