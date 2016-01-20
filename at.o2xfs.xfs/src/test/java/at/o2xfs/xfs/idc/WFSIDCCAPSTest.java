/*
 * Copyright (c) 2012, Andreas Fagschlunger. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.xfs.idc;

import at.o2xfs.xfs.XfsVersion;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class WFSIDCCAPSTest {

	@Test
	public void copyConstructorV2_00() {
		final WFSIDCCAPS expected = new WFSIDCCAPS(XfsVersion.V2_00);
		expected.allocate();
		expected.setCards(5);
		expected.setChipProtocols(EnumSet.of(IDCChipProtocol.T0, IDCChipProtocol.T1));
		expected.setCompound(true);
		final Map<String, String> extra = new HashMap<String, String>();
		extra.put("A", "1");
		extra.put("B", "2");
		expected.setExtra(extra);
		expected.setPowerOffOption(IDCPowerOption.EJECT);
		expected.setPowerOnOption(IDCPowerOption.RETAIN);
		expected.setReadTracks(EnumSet.of(IDCTrack.TRACK1, IDCTrack.TRACK2, IDCTrack.TRACK3, IDCTrack.CHIP));
		expected.setSecType(IDCSecType.CIM86);
		expected.setType(IDCType.MOTOR);
		expected.setWriteTracks(EnumSet.of(IDCTrack.TRACK2, IDCTrack.TRACK3));
		final WFSIDCCAPS actual = new WFSIDCCAPS(XfsVersion.V2_00, expected);
		Assert.assertEquals(expected, actual);
	}
}
