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

package at.o2xfs.xfs;

import org.junit.Assert;
import org.junit.Test;

public class XfsVersionRangeTest {

	@Test
	public void fromV3_00ToV3_10() {
		XfsVersionRange range = new XfsVersionRange(XfsVersion.V3_00, XfsVersion.V3_10);
		Assert.assertFalse(range.contains(XfsVersion.V2_00));
		Assert.assertTrue(range.contains(XfsVersion.V3_00));
		Assert.assertTrue(range.contains(XfsVersion.V3_10));
		Assert.assertFalse(range.contains(XfsVersion.V3_20));
	}

	@Test
	public void fromV3_00ToNull() {
		XfsVersionRange range = new XfsVersionRange(XfsVersion.V3_00, null);
		Assert.assertFalse(range.contains(XfsVersion.V2_00));
		Assert.assertTrue(range.contains(XfsVersion.V3_00));
		Assert.assertTrue(range.contains(XfsVersion.V3_10));
		Assert.assertTrue(range.contains(XfsVersion.V3_20));
	}

	@Test
	public void fromNullToV3_10() {
		XfsVersionRange range = new XfsVersionRange(null, XfsVersion.V3_10);
		Assert.assertTrue(range.contains(XfsVersion.V2_00));
		Assert.assertTrue(range.contains(XfsVersion.V3_00));
		Assert.assertTrue(range.contains(XfsVersion.V3_10));
		Assert.assertFalse(range.contains(XfsVersion.V3_20));
	}
}