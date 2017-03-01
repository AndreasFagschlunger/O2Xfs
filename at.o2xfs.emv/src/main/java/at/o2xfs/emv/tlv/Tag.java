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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import at.o2xfs.common.Bit;
import at.o2xfs.common.Bytes;
import at.o2xfs.common.Hex;

public final class Tag {

	private final byte[] tag;

	private Tag(byte[] tag) {
		this.tag = Bytes.copy(tag);
	}

	public boolean isConstructed() {
		return Bit.isSet(tag[0], Bit.B6);
	}

	public byte[] getBytes() {
		return Bytes.copy(tag);
	}

	public int size() {
		return tag.length;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(tag).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tag) {
			Tag t = (Tag) obj;
			return new EqualsBuilder().append(tag, t.tag).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return Hex.encode(tag);
	}

	public static final Tag parse(byte[] bytes) throws TagParseException {
		return parse(bytes, 0);
	}

	public static final Tag parse(byte[] bytes, final int offset)
			throws TagParseException {
		try {
			int length = 1;
			if ((bytes[offset] & 0x1F) == 0x1F) {
				do {
					length++;
				} while (Bit.isSet(bytes[offset + length - 1], Bit.B8));
			}
			byte[] tag = new byte[length];
			System.arraycopy(bytes, offset, tag, 0, length);
			return new Tag(tag);
		} catch (IndexOutOfBoundsException e) {
			throw new TagParseException("Unexpected end of Tag: "
					+ Hex.encode(bytes) + ", Offset: " + offset, e);
		}
	}

	public static final Tag create(int tag) {
		byte[] bytes = new byte[4];
		int index = bytes.length - 1;
		do {
			bytes[index] = (byte) tag;
			if (bytes[index] == 0) {
				break;
			}
			tag >>>= 8;
			index--;
		} while (true);
		return new Tag(Bytes.right(bytes, bytes.length - index - 1));
	}
}