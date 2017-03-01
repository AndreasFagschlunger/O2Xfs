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

import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.common.Bytes;
import at.o2xfs.emv.tlv.Tag;

/**
 * Data Object List (DOL)
 *
 * @author Andreas Fagschlunger
 */
public class DOL implements Iterable<Tag> {

	private static class Entry {

		private final Tag tag;

		private final int length;

		private byte[] value = null;

		private Entry(Tag tag, int length) {
			this.tag = tag;
			this.length = length;
		}

		public Tag getTag() {
			return tag;
		}

		public byte[] getValue() {
			return value;
		}

		public void setValue(byte[] value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return new ToStringBuilder(this).append("tag", tag)
					.append("length", length).toString();
		}
	}

	private static class DOLIterator implements Iterator<Tag> {

		private final Iterator<Entry> entries;

		private DOLIterator(Iterator<Entry> entries) {
			this.entries = entries;
		}

		@Override
		public boolean hasNext() {
			return entries.hasNext();
		}

		@Override
		public Tag next() {
			return entries.next().getTag();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private final List<Entry> entries;

	public DOL(byte[] list) {
		entries = new ArrayList<Entry>();
		parseList(list);
	}

	private void parseList(byte[] list) {
		for (int offset = 0; offset < list.length;) {
			Tag tag = Tag.parse(list, offset);
			offset += tag.size();
			int length = Bytes.toInt(list[offset++]);
			entries.add(new Entry(tag, length));
		}
	}

	@Override
	public Iterator<Tag> iterator() {
		return new DOLIterator(entries.iterator());
	}

	/**
	 * @throws NoSuchEntryException
	 *             if the specified Tag does not exist in this DOL
	 */
	public void put(EMVTag emvTag, byte[] value) throws NoSuchEntryException {
		Entry entry = getEntry(emvTag.getTag());
		if (entry.length > value.length) {
			switch (emvTag.getFormat()) {
				case NUMERIC:
					value = Bytes.leftPad(value, entry.length);
					break;
				case COMPRESSED_NUMERIC:
					value = Bytes.rightPad(value, entry.length, 0xFF);
					break;
				default:
					value = Bytes.rightPad(value, entry.length);
			}
		} else if (entry.length < value.length) {
			if (Format.NUMERIC.equals(emvTag.getFormat())) {
				value = Bytes.right(value, entry.length);
			} else {
				value = Bytes.left(value, entry.length);
			}
		}
		entry.setValue(value);
	}

	private Entry getEntry(Tag tag) {
		for (Entry entry : entries) {
			if (entry.getTag().equals(tag)) {
				return entry;
			}
		}
		throw new NoSuchEntryException(tag);
	}

	public byte[] construct() {
		byte[] list = new byte[calcLength()];
		int index = 0;
		for (Entry entry : entries) {
			if (entry.getValue() != null) {
				byte[] value = entry.getValue();
				System.arraycopy(value, 0, list, index, value.length);
			}
			index += entry.length;
		}
		return list;
	}

	private int calcLength() {
		int length = 0;
		for (Entry entry : entries) {
			length += entry.length;
		}
		return length;
	}
}