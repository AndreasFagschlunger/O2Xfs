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

package at.o2xfs.xfs.v3_30.cdm;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.EnumSet;
import java.util.Set;

import org.junit.Test;

import at.o2xfs.win32.Buffer;
import at.o2xfs.win32.BufferFactory;
import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.cdm.CdmExecuteCommand;
import at.o2xfs.xfs.cdm.CdmType;
import at.o2xfs.xfs.cdm.ItemInfoType;
import at.o2xfs.xfs.v3_00.cdm.CdmCaps3;
import at.o2xfs.xfs.v3_10.cdm.CdmCaps310;
import at.o2xfs.xfs.v3_20.cdm.CdmCaps320;
import at.o2xfs.xfs.v3_30.BaseXfs330Test;

public class CdmCaps330Test extends BaseXfs330Test {

	@Test
	public final void test() {
		CdmCaps330 expected = new CdmCaps330(buildCdmCaps330().getPointer());
		CdmCaps330 actual = new CdmCaps330(expected);
		System.out.println(actual);
		assertEquals(expected, actual);
		assertTrue(actual.getItemInfoTypes().contains(ItemInfoType.SIGNATURE));
		assertTrue(actual.isBlacklist());
		assertTrue(actual.getSynchronizableCommands().length != 0);
	}

	private native Buffer buildCdmCaps330();

	@Test
	public void testBuilder() {
		Set<ItemInfoType> itemInfoTypes = EnumSet.of(ItemInfoType.SIGNATURE, ItemInfoType.SERIALNUMBER);
		CdmExecuteCommand[] synchronizableCommands = new CdmExecuteCommand[] { CdmExecuteCommand.DENOMINATE,
				CdmExecuteCommand.DISPENSE };
		CdmCaps330 capabilities = new CdmCaps330.Builder(new CdmCaps320.Builder(new CdmCaps310.Builder(
				new CdmCaps3.Builder().serviceClass(XfsServiceClass.CDM).type(CdmType.SELFSERVICEBILL))))
						.itemInfoTypes(itemInfoTypes)
						.blacklist(true)
						.synchronizableCommands(synchronizableCommands)
						.build(BufferFactory.getInstance());
		assertEquals(itemInfoTypes, capabilities.getItemInfoTypes());
		assertTrue(capabilities.isBlacklist());
		assertArrayEquals(synchronizableCommands, capabilities.getSynchronizableCommands());
	}
}
