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

package at.o2xfs.emv.tlv;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import at.o2xfs.common.Bit;
import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;

public final class TLV {

	private final byte[] tlv;

	private Tag tag = null;

	private int length = 0;

	private int lengthSize = 0;

	private byte[] value = null;

	private final List<TLV> children = new ArrayList<TLV>();

	private TLV(byte[] tlv, int offset) {
		if (offset >= tlv.length) {
			throw new TLVParseException("Unexpected end of TLV: "
					+ Hex.encode(tlv) + ", Offset: " + offset);
		}
		parse(tlv, offset);
		this.tlv = Bytes.mid(tlv, offset, tag.size() + lengthSize
				+ value.length);
	}

	private void parse(byte[] tlv, int offset) {
		try {
			tag = Tag.parse(tlv, offset);
			offset += tag.size();
			parseLength(tlv, offset);
			offset += lengthSize;
			parseValue(tlv, offset);
		} catch (TagParseException e) {
			throw new TLVParseException("Error parsing TLV: " + Hex.encode(tlv)
					+ ", Offset: " + offset, e);
		}
	}

	private void parseValue(byte[] tlv, int offset) {
		value = new byte[length];
		if (offset + length > tlv.length) {
			throw new TLVParseException("Unexpected end of value field, TLV: "
					+ Hex.encode(tlv) + ", Offset: " + offset);
		}
		System.arraycopy(tlv, offset, value, 0, length);
		if (tag.isConstructed()) {
			for (; offset < tlv.length;) {
				if (tlv[offset] == 0) {
					offset++;
					continue;
				}
				final TLV child = new TLV(tlv, offset);
				children.add(child);
				offset += child.tag.size() + child.lengthSize + child.length;
			}
		}
	}

	private void parseLength(final byte[] tlv, int offset) {
		if (offset >= tlv.length) {
			throw new TLVParseException("Missing length field, TLV: "
					+ Hex.encode(tlv) + ", Offset: " + offset);
		}
		length = Bytes.toInt(tlv[offset++]);
		if (!Bit.isSet((byte) length, Bit.B8)) {
			lengthSize = 1;
			return;
		}
		lengthSize = length & 0x7F;
		length = 0;
		for (int i = 0; i < lengthSize; i++) {
			length <<= 8;
			if (offset + 1 >= tlv.length) {
				throw new TLVParseException(
						"Unexpected end of length field, TLV: "
								+ Hex.encode(tlv) + ", Offset: " + offset);
			}
			length |= Bytes.toInt(tlv[offset++]);
		}
		lengthSize++;
	}

	public Tag getTag() {
		return tag;
	}

	public TLV getChild(int index) {
		return children.get(index);
	}

	public List<TLV> getChildren() {
		return new ArrayList<TLV>(children);
	}

	public byte[] getBytes() {
		return Bytes.copy(tlv);
	}

	public byte[] getValue() {
		return Bytes.copy(value);
	}

	public void print(PrintStream s) {
		print(s, "");
	}

	private void print(PrintStream s, String prefix) {
		s.print(prefix + tag);
		if (!tag.isConstructed()) {
			s.print(' ');
			s.print(Hex.encode(value));
		}
		s.print(" (" + length + ")");
		s.println();
		for (TLV child : children) {
			child.print(s, prefix + "\t");
		}
	}

	@Override
	public String toString() {
		return Hex.encode(tlv);
	}

	public static final TLV parse(byte[] tlv) throws TLVParseException {
		return new TLV(tlv, 0);
	}
}