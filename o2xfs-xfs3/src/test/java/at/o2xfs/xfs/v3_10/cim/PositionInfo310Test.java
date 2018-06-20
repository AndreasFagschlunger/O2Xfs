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

package at.o2xfs.xfs.v3_10.cim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import at.o2xfs.win32.Buffer;
import at.o2xfs.xfs.cim.AdditionalBunches;
import at.o2xfs.xfs.cim.Position;
import at.o2xfs.xfs.v3_10.BaseXfs310Test;

public class PositionInfo310Test extends BaseXfs310Test {

	@Test
	public final void test() {
		PositionInfo310 expected = new PositionInfo310(buildPositionInfo310().getPointer());
		PositionInfo310 actual = new PositionInfo310(expected);
		System.out.println(actual);
		assertEquals(expected, actual);
	}

	private native Buffer buildPositionInfo310();

	@Test
	public void issue67() {
		PositionInfo310.Builder builder = new PositionInfo310.Builder(Position.OUTFRONT);
		PositionInfo310 positionInfo = new PositionInfo310.Builder(Position.OUTFRONT).build();
		assertNotNull(positionInfo.getAdditionalBunches());
		for (AdditionalBunches expected : AdditionalBunches.values()) {
			assertEquals(expected, builder.additionalBunches(expected).build().getAdditionalBunches().get());
		}
	}
}
