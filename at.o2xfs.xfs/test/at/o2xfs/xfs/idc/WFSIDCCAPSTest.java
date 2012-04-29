/*
 * Copyright (c) 2012, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.xfs.idc;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import at.o2xfs.xfs.XfsVersion;

public class WFSIDCCAPSTest {

	@Test
	public void copyConstructorV2_00() {
		final WFSIDCCAPS expected = new WFSIDCCAPS(XfsVersion.V2_00);
		expected.allocate();
		expected.setCards(5);
		expected.setChipProtocols(EnumSet.of(IDCChipProtocol.WFS_IDC_CHIPT0,
				IDCChipProtocol.WFS_IDC_CHIPT1));
		expected.setCompound(true);
		final Map<String, String> extra = new HashMap<String, String>();
		extra.put("A", "1");
		extra.put("B", "2");
		expected.setExtra(extra);
		expected.setPowerOffOption(IDCPowerOption.WFS_IDC_EJECT);
		expected.setPowerOnOption(IDCPowerOption.WFS_IDC_RETAIN);
		expected.setReadTracks(EnumSet.of(IDCTrack.WFS_IDC_TRACK1,
				IDCTrack.WFS_IDC_TRACK2, IDCTrack.WFS_IDC_TRACK3,
				IDCTrack.WFS_IDC_CHIP));
		expected.setSecType(IDCSecType.WFS_IDC_SECCIM86);
		expected.setType(IDCType.WFS_IDC_TYPEMOTOR);
		expected.setWriteTracks(EnumSet.of(IDCTrack.WFS_IDC_TRACK2,
				IDCTrack.WFS_IDC_TRACK3));
		final WFSIDCCAPS actual = new WFSIDCCAPS(XfsVersion.V2_00, expected);
		Assert.assertEquals(expected, actual);
	}
}
