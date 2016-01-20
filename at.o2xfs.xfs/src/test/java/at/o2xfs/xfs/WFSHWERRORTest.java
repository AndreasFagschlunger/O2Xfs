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

package at.o2xfs.xfs;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class WFSHWERRORTest {

	@Test
	public void copyConstructorV2_00() {
		final WFSHWERROR expected = new WFSHWERROR(XfsVersion.V2_00);
		expected.allocate();
		expected.setAppID("appID");
		final byte[] description = new byte[128];
		new Random().nextBytes(description);
		expected.setDescription(description);
		expected.setLogicalName("logicalName");
		expected.setWorkstationName("workstationName");
		Assert.assertEquals(expected, new WFSHWERROR(XfsVersion.V2_00, expected));
	}

	@Test
	public void copyConstructorV3_00() {
		final WFSHWERROR expected = new WFSHWERROR(XfsVersion.V3_00);
		expected.allocate();
		expected.setAction(XfsErrorAction.WFS_ERR_ACT_RESET);
		expected.setAppID("appID");
		final byte[] description = new byte[128];
		new Random().nextBytes(description);
		expected.setDescription(description);
		expected.setLogicalName("logicalName");
		expected.setPhysicalName("physicalName");
		expected.setWorkstationName("workstationName");
		Assert.assertEquals(expected, new WFSHWERROR(XfsVersion.V3_00, expected));
	}

	@Test
	public void emptyDescription() {
		final WFSHWERROR hwError = new WFSHWERROR(XfsVersion.V2_00);
		hwError.allocate();
		hwError.setDescription(new byte[0]);
		System.out.println(hwError);
		Assert.assertArrayEquals(new byte[0], hwError.getDescription());
	}

	@Test
	public void nullDescription() {
		final WFSHWERROR hwError = new WFSHWERROR(XfsVersion.V3_00);
		hwError.allocate();
		Assert.assertNull(hwError.getDescription());
	}
}
