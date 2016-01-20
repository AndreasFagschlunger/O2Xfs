/*
 * Copyright (c) 2012, Andreas Fagschlunger. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.xfs;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class XFSVersionDWORDTest {

	@Test
	public void v1_00() {
		final XFSVersionDWORD versions = new XFSVersionDWORD("1.0");
		assertEquals(0x00010001L, versions.longValue());
	}

	@Test
	public void v1_00_2_10() {
		final XFSVersionDWORD versions = new XFSVersionDWORD("1.0", "2.10");
		assertEquals(0x00010A02L, versions.longValue());
	}

	@Test
	public void v1_11() {
		final XFSVersionDWORD versions = new XFSVersionDWORD("1.11");
		assertEquals(0x0B010B01L, versions.longValue());
	}

	@Test
	public void v2_11_3_00() {
		final XFSVersionDWORD versions = new XFSVersionDWORD("2.11", "3.0");
		assertEquals(0x0B020003L, versions.longValue());
	}

	@Test
	public void v1_11_3_00() {
		final XFSVersionDWORD versions = new XFSVersionDWORD("1.11", "3.0");
		assertEquals(0x0B010003L, versions.longValue());
	}

	@Test
	public void v255_255_255_255() {
		final XFSVersionDWORD versions = new XFSVersionDWORD("255.255", "255.255");
		assertEquals(0xFFFFFFFFL, versions.longValue());
	}
}
