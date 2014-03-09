/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.common;

import org.junit.Assert;
import org.junit.Test;

public class ArrayUtilTest {

	@Test
	public final void noElements() {
		byte[] expecteds = new byte[] {};
		byte[] actuals = new byte[] {};
		ArrayUtil.reverse(actuals);
		Assert.assertArrayEquals(expecteds, actuals);
	}

	@Test
	public final void evenNumberOfElements() {
		byte[] expecteds = new byte[] { 4, 3, 2, 1 };
		byte[] actuals = new byte[] { 1, 2, 3, 4 };
		ArrayUtil.reverse(actuals);
		Assert.assertArrayEquals(expecteds, actuals);
	}

	@Test
	public final void oddNumberOfElements() {
		byte[] expecteds = new byte[] { 3, 2, 1 };
		byte[] actuals = new byte[] { 1, 2, 3 };
		ArrayUtil.reverse(actuals);
		Assert.assertArrayEquals(expecteds, actuals);
	}
}