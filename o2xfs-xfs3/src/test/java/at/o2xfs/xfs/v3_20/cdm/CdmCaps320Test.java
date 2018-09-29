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

package at.o2xfs.xfs.v3_20.cdm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.EnumSet;

import org.junit.Test;

import at.o2xfs.win32.Buffer;
import at.o2xfs.win32.BufferFactory;
import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.cdm.CdmType;
import at.o2xfs.xfs.cdm.ExchangeType;
import at.o2xfs.xfs.cdm.MoveItems;
import at.o2xfs.xfs.cdm.Position;
import at.o2xfs.xfs.cdm.RetractArea;
import at.o2xfs.xfs.cdm.RetractStackerActions;
import at.o2xfs.xfs.v3_00.cdm.CdmCaps3;
import at.o2xfs.xfs.v3_10.cdm.CdmCaps310;
import at.o2xfs.xfs.v3_20.BaseXfs320Test;

public class CdmCaps320Test extends BaseXfs320Test {

	@Test
	public final void test() {
		CdmCaps320 expected = new CdmCaps320(buildCdmCaps320().getPointer());
		CdmCaps320 actual = new CdmCaps320(expected);
		System.out.println(actual);
		assertTrue(actual.isAntiFraudModule());
		assertEquals(expected, actual);
	}

	private native Buffer buildCdmCaps320();

	@Test
	public void testBuilder() {
		CdmCaps320 capabilities = new CdmCaps320.Builder(new CdmCaps310.Builder(new CdmCaps3.Builder()
				.serviceClass(XfsServiceClass.CDM)
				.type(CdmType.SELFSERVICEBILL)
				.maxDispenseItems(40)
				.compound(false)
				.shutter(true)
				.shutterControl(false)
				.retractAreas(EnumSet.of(RetractArea.RETRACT, RetractArea.TRANSPORT))
				.retractTransportActions(EnumSet.of(RetractStackerActions.RETRACT))
				.retractStackerActions(EnumSet.of(RetractStackerActions.RETRACT))
				.safeDoor(false)
				.cashBox(false)
				.intermediateStacker(true)
				.itemsTakenSensor(true)
				.positions(EnumSet.of(Position.FRONT))
				.moveItems(EnumSet.of(MoveItems.FROMCU, MoveItems.TOTRANSPORT))
				.exchangeType(EnumSet.of(ExchangeType.BYHAND))).powerSaveControl(true))
						.antiFraudModule(true)
						.build(BufferFactory.getInstance());
		assertTrue(capabilities.isAntiFraudModule());
	}
}
