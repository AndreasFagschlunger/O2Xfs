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

package at.o2xfs.xfs.cam.v320;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import at.o2xfs.win32.Buffer;

@Ignore
public class CamStructV3_20Test {

	static {
		System.loadLibrary("at.o2xfs.win32");
		System.loadLibrary("at.o2xfs.xfs.test");
	}

	@Test
	public void testCamStatusV3_20() {
		Buffer buffer = createCamStatusV3_20();
		CamStatusV3_20 status = new CamStatusV3_20(buffer.getPointer());
		System.out.println(status);
		CamStatusV3_20 copy = new CamStatusV3_20(status);
		Assert.assertEquals(status, copy);
	}

	@Test
	public void testCamCapsV3_20() {
		Buffer buffer = createCamCapsV3_20();
		CamCapsV3_20 capabilities = new CamCapsV3_20(buffer.getPointer());
		System.out.println(capabilities);
		CamCapsV3_20 copy = new CamCapsV3_20(capabilities);
		Assert.assertEquals(capabilities, copy);
	}

	@Test
	public void testTakepictEx() {
		TakepictEx takepictEx = new TakepictEx(createTakepictEx().getPointer());
		System.out.println(takepictEx);
		TakepictEx copy = new TakepictEx(takepictEx);
		Assert.assertEquals(takepictEx, copy);
	}

	private native Buffer createCamStatusV3_20();

	private native Buffer createCamCapsV3_20();

	private native Buffer createTakepictEx();
}