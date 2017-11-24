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

package at.o2xfs.xfs.v3_00.cim;

import static org.junit.Assert.assertEquals;

import java.util.EnumSet;
import java.util.Optional;

import org.junit.Test;

import at.o2xfs.win32.Buffer;
import at.o2xfs.xfs.cim.CashInItemType;
import at.o2xfs.xfs.cim.CashInType;
import at.o2xfs.xfs.cim.CashUnitStatus;
import at.o2xfs.xfs.v3_00.BaseXfs3Test;

public class CashInfo3Test extends BaseXfs3Test {

	@Test
	public final void test() {
		CashInfo3 expected = new CashInfo3(buildCashInfo3().getPointer());
		CashInfo3 actual = new CashInfo3(expected);
		System.out.println(actual);
		assertEquals(expected, actual);
	}

	private native Buffer buildCashInfo3();

	@Test
	public void testBuild() {
		NoteNumber3[] noteNumbers = new NoteNumber3[1];
		noteNumbers[0] = new NoteNumber3.Builder(1).count(1234).build();
		NoteNumberList3 noteNumberList = new NoteNumberList3.Builder(noteNumbers).build();
		PhysicalCashUnit3[] physicalCashUnits = new PhysicalCashUnit3[1];
		physicalCashUnits[0] = new PhysicalCashUnit3.Builder("SLOT1", new char[] { '1', '2', '3', '4', '5' },
				CashUnitStatus.OK).build();
		CashIn3[] cashIn = new CashIn3[1];
		cashIn[0] = new CashIn3.Builder(1, CashInType.RECYCLING, EnumSet.of(CashInItemType.INDIVIDUAL),
				new char[] { '1', '2', '3', '4', '5' }, new char[] { 'E', 'U', 'R' }, CashUnitStatus.OK,
				physicalCashUnits).value(10000L).noteNumberList(Optional.of(noteNumberList)).build();
		CashInfo3 cashInfo = new CashInfo3.Builder(cashIn).build();
		System.out.println(cashInfo);
	}
}
