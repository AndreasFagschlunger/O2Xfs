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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.EnumSet;

import org.junit.Test;

import at.o2xfs.win32.Buffer;
import at.o2xfs.xfs.ptr.CoercivityType;
import at.o2xfs.xfs.ptr.ControlPassbookAction;
import at.o2xfs.xfs.ptr.PrintSides;
import at.o2xfs.xfs.v3_20.BaseXfs320Test;

public class PtrCapabilities320Test extends BaseXfs320Test {

	@Test
	public final void test() {
		PtrCapabilities320 expected = new PtrCapabilities320(buildPtrCapabilities320().getPointer());
		PtrCapabilities320 actual = new PtrCapabilities320(expected);
		System.out.println(actual);
		assertEquals(expected, actual);
		assertFalse(actual.getWindowsPrinter().isPresent());
		assertEquals(EnumSet.of(CoercivityType.LOW, CoercivityType.HIGH, CoercivityType.AUTO),
				actual.getCoercivityTypes());
		assertEquals(EnumSet.of(ControlPassbookAction.TURNFORWARD, ControlPassbookAction.TURNBACKWARD),
				actual.getControlPassbook());
		assertEquals(PrintSides.DUAL, actual.getPrintSides());
		assertTrue(actual.isAntiFraudModule());
	}

	private native Buffer buildPtrCapabilities320();
}
