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

package at.o2xfs.win32;

import java.nio.charset.StandardCharsets;

/**
 * A null-terminated ASCII string.
 *
 * @author Andreas Fagschlunger
 */
public class ZSTR extends CharArray implements ASCII, ValueType<byte[]> {

	public ZSTR(final int length) {
		this(length, false);
	}

	public ZSTR(final int length, final boolean allocate) {
		super(length);
		if (length < 1) {
			throw new IllegalArgumentException("Illegal length: " + length + ", must be >= 1");
		}
		if (allocate) {
			allocate();
		}
	}

	public ZSTR(final String s) {
		this(s.length() + 1, true);
		put((s + NUL).getBytes(StandardCharsets.US_ASCII));
	}

	@Override
	public void set(byte[] value) {
		this.put(value);
	}

	@Override
	public byte[] get() {
		return getBytes();
	}

	@Override
	public String toString() {
		String result = new String(getBytes(), StandardCharsets.US_ASCII);
		if (result.indexOf(NUL) != -1) {
			result = result.substring(0, result.indexOf(NUL));
		}
		return result;
	}
}