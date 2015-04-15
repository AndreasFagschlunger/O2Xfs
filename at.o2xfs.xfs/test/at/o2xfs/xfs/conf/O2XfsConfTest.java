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

package at.o2xfs.xfs.conf;

import at.o2xfs.win32.HKEY;
import at.o2xfs.xfs.XfsException;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class O2XfsConfTest {

	private O2XfsConf xfsConf = null;

	@Before
	public void setUp() {
		xfsConf = O2XfsConf.getInstance();
	}

	@Test
	public void dumpConfTree() throws XfsException {
		dump(O2XfsConf.WFS_CFG_HKEY_XFS_ROOT, "");
	}

	private void dump(final HKEY hKey, final String path) throws XfsException {
		for (String subKey : new WFMKeyIterator(hKey)) {
			System.out.println(path + subKey);
			HKEY hSubKey = null;
			try {
				hSubKey = xfsConf.wfmOpenKey(hKey, subKey);
				for (final Map.Entry<String, String> entry : new WFMValueIterator(hSubKey)) {
					System.out.println(entry.getKey() + "=" + entry.getValue());
				}
				dump(hSubKey, path + subKey + "\\");
			} finally {
				try {
					xfsConf.wfmCloseKey(hSubKey);
				} catch (XfsException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
