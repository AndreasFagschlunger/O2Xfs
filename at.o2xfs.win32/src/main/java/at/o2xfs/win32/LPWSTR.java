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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class LPWSTR
		extends Pointer
		implements ValueType<String> {

	public void set(LPWSTR value) {
		set(value.get());
	}

	@Override
	public void set(String value) {
		pointTo(WString.valueOf(value));
	}

	@Override
	public String get() {
		if (!NULL.equals(this)) {
			for (int size = 2;; size += 2) {
				byte[] bytes = buffer(size).get();
				if (bytes[bytes.length - 1] == 0 && bytes[bytes.length - 2] == 0) {
					return new String(bytes, 0, size - 2, StandardCharsets.UTF_16LE);
				}
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(get()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LPWSTR) {
			return new EqualsBuilder().append(get(), ((LPWSTR) obj).get()).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return String.valueOf(get());
	}
}