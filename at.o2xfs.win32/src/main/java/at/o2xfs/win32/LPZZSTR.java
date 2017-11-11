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
import java.util.Arrays;

/**
 * Pointer class for a double-null-terminated string.
 *
 * @author Andreas Fagschlunger
 */
public class LPZZSTR extends Pointer implements ASCII, ValueType<String[]> {

	public LPZZSTR() {
		super();
	}

	public LPZZSTR(Pointer p) {
		super(p.getBuffer());
	}

	public LPZZSTR(String[] value) {
		super();
		allocate();
		set(value);
	}

	@Override
	public void set(String[] value) {
		int length = 2;
		for (String each : value) {
			length += each.length() + 1;
		}
		byte[] dest = new byte[length];
		int destPos = 0;
		for (String each : value) {
			byte[] src = each.getBytes(StandardCharsets.US_ASCII);
			System.arraycopy(src, 0, dest, destPos, src.length);
			destPos += src.length + 1;
		}
		pointTo(new ByteArray(dest));
	}

	@Override
	public String[] get() {
		final String zzString = readZZString();
		return zzString.split("" + NUL);
	}

	private String readZZString() {
		if (NULL.equals(this)) {
			return null;
		}
		for (int length = 2;; length++) {
			byte[] bytes = buffer(length).get();
			if (bytes[bytes.length - 1] == NUL && bytes[bytes.length - 2] == NUL) {
				return new String(bytes, StandardCharsets.US_ASCII);
			}
		}
	}

	@Override
	public String toString() {
		if (NULL.equals(this)) {
			return "";
		}
		return Arrays.toString(get());
	}
}