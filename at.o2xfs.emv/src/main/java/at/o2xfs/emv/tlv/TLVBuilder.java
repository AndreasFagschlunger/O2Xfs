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

import java.util.ArrayList;
import java.util.List;

import at.o2xfs.common.Bit;
import at.o2xfs.common.Bytes;

public final class TLVBuilder {

	private final Tag tag;

	private final List<byte[]> values;

	private TLVBuilder(Tag tag) {
		this.tag = tag;
		values = new ArrayList<byte[]>();
	}

	public TLVBuilder add(TLV tlv) {
		values.add(tlv.getBytes());
		return this;
	}

	public TLV build() {
		byte[] t = tag.getBytes();
		byte[] v = Bytes.concat(values);
		byte[] l = encodeLength(v.length);
		byte[] tlv = new byte[tag.size() + l.length + v.length];
		int offset = 0;
		System.arraycopy(t, 0, tlv, offset, t.length);
		offset += t.length;
		System.arraycopy(l, 0, tlv, offset, l.length);
		offset += l.length;
		System.arraycopy(v, 0, tlv, offset, v.length);
		return TLV.parse(tlv);
	}

	private byte[] encodeLength(int l) {
		if (l <= 127) {
			return new byte[] { (byte) l };
		}
		byte[] length = new byte[5];
		int count = 0;
		int vLength = l;
		int i = length.length - 1;
		do {
			count++;
			length[i--] = (byte) vLength;
			vLength >>>= 8;
		} while (vLength != 0);
		length[i] = (byte) (count | Bit.B8);
		return Bytes.right(length, count + 1);
	}

	public static final TLVBuilder newConstructed(Tag tag) {
		if (!tag.isConstructed()) {
			throw new IllegalArgumentException("Tag must not be primitive");
		}
		return new TLVBuilder(tag);
	}

	public static final TLV newPrimitive(Tag tag, byte[] value) {
		if (tag.isConstructed()) {
			throw new IllegalArgumentException("Tag must not be constructed");
		}
		TLVBuilder primitive = new TLVBuilder(tag);
		primitive.values.add(value);
		return primitive.build();
	}
}