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

package at.o2xfs.xfs.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class XFSUtilsTest {

	@Test
	public void testGetVersionsRequired() {
		assertEquals(0x00010A02, XFSUtils.getVersionsRequired("1", "2.10"));
		assertEquals(0x0B020003, XFSUtils.getVersionsRequired("2.11", "3.0"));
		assertEquals(0x0B010003, XFSUtils.getVersionsRequired("1.11", "3"));
	}

	@Test
	public void test1_11() {
		assertEquals(0x0B01, XFSUtils.getVersion("1.11"));
	}

	@Test
	public void testGetVersion() {
		assertEquals(0x0001, XFSUtils.getVersion("1.0"));
		assertEquals(0x0001, XFSUtils.getVersion("1."));
		assertEquals(0x0000, XFSUtils.getVersion("0."));
		assertEquals(0x0B00, XFSUtils.getVersion(".11"));
		try {
			System.out.println(XFSUtils.getVersion("270"));
			fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			System.out.println(XFSUtils.getVersion("-1"));
			fail();
		} catch (IllegalArgumentException e) {
		}
		try {
			System.out.println(XFSUtils.getVersion(".256"));
			fail();
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testGetVersionAsString() {
		assertEquals("2.10", XFSUtils.getVersionAsString(0x0A02));
		assertEquals("1.0", XFSUtils.getVersionAsString(0x0001));
		assertEquals("3.11", XFSUtils.getVersionAsString(0x0B03));
		assertEquals("4.2", XFSUtils.getVersionAsString(0x0204));
	}

}
