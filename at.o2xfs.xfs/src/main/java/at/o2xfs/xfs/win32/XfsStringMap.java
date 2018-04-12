/*
 * Copyright (c) 2018, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.xfs.win32;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import at.o2xfs.win32.ByteArray;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.ValueType;

class XfsStringMap extends Pointer implements ValueType<Map<String, String>> {

	private static final char NUL = 0;

	private final Charset charset;

	private final int bytesPerChar;

	XfsStringMap(int bytesPerChar, Charset charset) {
		this.bytesPerChar = bytesPerChar;
		this.charset = charset;
	}

	@Override
	public void set(Map<String, String> value) {
		if (value.isEmpty()) {
			pointTo(NULL);
		} else {
			pointTo(new ByteArray(encode(value)));
		}
	}

	private byte[] encode(Map<String, String> value) {
		StringBuilder builder = new StringBuilder();
		for (Map.Entry<String, String> entry : value.entrySet()) {
			builder.append(entry.getKey()).append('=');
			if (entry.getValue() != null) {
				builder.append(entry.getValue());
			}
			builder.append(NUL);
		}
		builder.append(NUL);
		return builder.toString().getBytes(charset);
	}

	@Override
	public Map<String, String> get() {
		Map<String, String> result = Collections.emptyMap();
		if (!Pointer.NULL.equals(this)) {
			int length = bytesPerChar;
			byte[] buf;
			do {
				length += bytesPerChar;
				buf = buffer(length).get();
			} while (!isTerminated(buf));
			result = decode(new String(buf, 0, buf.length - (bytesPerChar * 2), charset));
		}
		return result;
	}

	private boolean isTerminated(byte[] buf) {
		boolean result = true;
		for (int i = bytesPerChar * 2; i >= 1; i--) {
			if (buf[buf.length - i] != NUL) {
				result = false;
				break;
			}
		}
		return result;
	}

	private Map<String, String> decode(String s) {
		Map<String, String> result = new LinkedHashMap<String, String>();
		for (String each : s.split("\0")) {
			int beginIndex = each.indexOf('=');
			if (beginIndex == -1) {
				result.put(each, null);
				continue;
			}
			String key = each.substring(0, beginIndex);
			String value = each.substring(beginIndex + 1);
			result.put(key, value);
		}
		return result;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(get()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof XfsStringMap) {
			return new EqualsBuilder().append(get(), ((XfsStringMap) obj).get()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return String.valueOf(get());
	}
}
