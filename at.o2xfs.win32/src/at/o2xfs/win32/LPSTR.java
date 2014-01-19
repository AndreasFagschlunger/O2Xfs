/*
 * Copyright (c) 2012, Andreas Fagschlunger. All rights reserved.
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

/**
 * A pointer to a null-terminated string of 8-bit Windows (ANSI) characters.
 * 
 * {@link http://msdn.microsoft.com/en-us/library/aa383751%28v=vs.85%29.aspx}
 * 
 * @author Andreas Fagschlunger
 */
public class LPSTR extends Pointer implements ASCII {

	public LPSTR() {
		super();
	}

	/**
	 * Makes this <code>Pointer</code> point to a {@link ZSTR} with the
	 * specified <code>String</code> value or points to <code>NULL</code> if no
	 * <code>String</code> is given.
	 * 
	 * @param s
	 *            a <code>String</code> or <code>null</code>
	 */
	public void pointTo(String s) {
		if (s == null) {
			pointToNULL();
			return;
		}
		pointTo(new ZSTR(s));
	}

	private void pointToNULL() {
		put(new byte[getSize()]);
	}

	private String value() {
		for (int length = 1;; length++) {
			byte[] bytes = buffer(length).get();
			if (bytes[length - 1] == NUL) {
				return new String(bytes, 0, length - 1, US_ASCII);
			}
		}
	}

	@Override
	public String toString() {
		if (!NULL.equals(this)) {
			return value();
		}
		return "";
	}
}
