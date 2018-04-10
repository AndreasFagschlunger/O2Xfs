/*
 * Copyright (c) 2018, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.xfs.v3_20.ptr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.o2xfs.win32.Buffer;
import at.o2xfs.xfs.ptr.AntiFraudModule;
import at.o2xfs.xfs.ptr.PaperType;
import at.o2xfs.xfs.ptr.PtrConstant;
import at.o2xfs.xfs.v3_20.BaseXfs320Test;

public class PtrStatus320Test extends BaseXfs320Test {

	@Test
	public final void test() {
		PtrStatus320 expected = new PtrStatus320(buildPtrStatus320().getPointer());
		PtrStatus320 actual = new PtrStatus320(expected);
		System.out.println(actual);
		assertEquals(expected, actual);
		assertEquals(PaperType.SINGLESIDED, actual.getPaperTypes()[PtrConstant.SUPPLYUPPER]);
		assertEquals(PaperType.DUALSIDED, actual.getPaperTypes()[PtrConstant.SUPPLYLOWER]);
		assertEquals(AntiFraudModule.OK, actual.getAntiFraudModule());
	}

	private native Buffer buildPtrStatus320();
}
