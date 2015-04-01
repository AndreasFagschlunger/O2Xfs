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

import junit.framework.Assert;

import org.junit.Test;

public class XfsVersionTest {

	@Test
	public void version_00() {
		final XfsVersion version = new XfsVersion(".00");
		Assert.assertEquals(0x0000, version.intValue());
		Assert.assertEquals("0.00", version.toString());
	}

	@Test
	public void version1_00() {
		final XfsVersion version = new XfsVersion("1.00");
		Assert.assertEquals(0x0001, version.intValue());
		Assert.assertEquals("1.00", version.toString());
	}

	@Test
	public void version1_11() {
		final XfsVersion version = new XfsVersion("1.11");
		Assert.assertEquals(0x0B01, version.intValue());
		Assert.assertEquals("1.11", version.toString());
	}

	@Test
	public void version2_00() {
		final XfsVersion version = new XfsVersion("2.0");
		Assert.assertEquals(0x0002, version.intValue());
		Assert.assertEquals("2.00", version.toString());
	}

	@Test
	public void version2_3() {
		final XfsVersion version = new XfsVersion("2.3");
		Assert.assertEquals(0x0302, version.intValue());
		Assert.assertEquals("2.03", version.toString());
	}

	@Test
	public void version2_20() {
		final XfsVersion version = new XfsVersion("2.20");
		Assert.assertEquals(0x1402, version.intValue());
		Assert.assertEquals("2.20", version.toString());
	}

	@Test
	public void version3_11() {
		final XfsVersion version = new XfsVersion("3.11");
		Assert.assertEquals(0x0B03, version.intValue());
		Assert.assertEquals("3.11", version.toString());
	}

	@Test
	public void version255_255() {
		XfsVersion version = new XfsVersion("255.255");
		Assert.assertEquals(0xFFFF, version.intValue());
		Assert.assertEquals("255.255", version.toString());
	}

	@Test
	public void compareMajor() {
		final XfsVersion v2_00 = new XfsVersion("2.00");
		final XfsVersion v3_00 = new XfsVersion("3.00");
		Assert.assertEquals(false, v2_00.isGE(v3_00));
		Assert.assertEquals(true, v3_00.isGE(v2_00));
		Assert.assertEquals(true, v3_00.isGE(v3_00));
	}

	@Test
	public void compareMinor() {
		final XfsVersion v3_03 = new XfsVersion("3.03");
		final XfsVersion v3_11 = new XfsVersion("3.11");
		Assert.assertEquals(false, v3_03.isGE(v3_11));
		Assert.assertEquals(true, v3_03.isGE(v3_03));
		Assert.assertEquals(true, v3_11.isGE(v3_03));
	}

	@Test
	public void compareLessThan() {
		final XfsVersion v3_00 = new XfsVersion("3.00");
		final XfsVersion v2_00 = new XfsVersion("2.00");
		Assert.assertEquals(true, v2_00.isLT(v3_00));
		Assert.assertEquals(false, v3_00.isLT(v2_00));
		Assert.assertEquals(false, v2_00.isLT(v2_00));
	}
}
