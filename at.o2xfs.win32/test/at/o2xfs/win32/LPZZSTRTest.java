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

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class LPZZSTRTest {

	@Test
	public void testNoString() throws Exception {
		final String[] expecteds = {};
		String testString = "\0\0";
		ByteArray bytes = new ByteArray(testString.getBytes("US-ASCII"));
		LPZZSTR lpzzString = new LPZZSTR();
		lpzzString.allocate();
		lpzzString.pointTo(bytes);
		assertArrayEquals(expecteds, lpzzString.values());
	}

	@Test
	public void testOneString() throws Exception {
		final String[] expecteds = { "Hello World!" };
		String testString = expecteds[0] + "\0\0";
		ByteArray bytes = new ByteArray(testString.getBytes("US-ASCII"));
		LPZZSTR lpzzString = new LPZZSTR();
		lpzzString.allocate();
		lpzzString.pointTo(bytes);
		assertArrayEquals(expecteds, lpzzString.values());
	}

	@Test
	public void testThreeString() throws Exception {
		final String[] expecteds = { "A=1", "B=2", "C=3" };
		String testString = "A=1\0B=2\0C=3\0\0";
		ByteArray bytes = new ByteArray(testString.getBytes("US-ASCII"));
		LPZZSTR lpzzString = new LPZZSTR();
		lpzzString.allocate();
		lpzzString.pointTo(bytes);
		assertArrayEquals(expecteds, lpzzString.values());
	}
}
