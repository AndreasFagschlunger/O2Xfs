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

import java.util.EnumSet;

import org.junit.Test;

import at.o2xfs.xfs.cdm.CashUnitType;
import at.o2xfs.xfs.cim.CashInItemType;
import at.o2xfs.xfs.cim.CashInType;
import at.o2xfs.xfs.cim.CashUnitStatus;

public class CashInfo310Test {

	@Test
	public final void testBuilder() {
		PhysicalCashUnit310[] physicalCashUnits = new PhysicalCashUnit310[1];
		physicalCashUnits[0] = new PhysicalCashUnit310.Builder("SLOT1", new char[] { '1', '2', '3', '4', '5' },
				CashUnitStatus.OK)
						.initialCount(1L)
						.dispensedCount(2L)
						.presentedCount(3L)
						.retractedCount(4L)
						.rejectCount(5L)
						.build();
		CashIn310 cashIn = new CashIn310.Builder(9, CashInType.CDMSPECIFIC, EnumSet.of(CashInItemType.INDIVIDUAL),
				new char[] { '1', '2', '3', '4', '5' }, new char[] { 'E', 'U', 'R' }, CashUnitStatus.OK,
				physicalCashUnits, CashUnitType.BILLCASSETTE).build();
		System.out.println(cashIn);
	}
}
