/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.xfs.service.lookup;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.DWORD;
import at.o2xfs.win32.HKEY;
import at.o2xfs.xfs.XfsError;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.conf.O2XfsConf;

import java.util.Enumeration;
import java.util.NoSuchElementException;

public class RegistryKeyEnumerator
		implements Enumeration<String> {

	private final static Logger LOG = LoggerFactory.getLogger(RegistryKeyEnumerator.class);

	private O2XfsConf xfsConf = null;

	private HKEY hKey = null;

	private DWORD iSubKey = null;

	private String name = null;

	public RegistryKeyEnumerator(final HKEY hKey) {
		this.hKey = hKey;
		iSubKey = new DWORD(0L);
		xfsConf = O2XfsConf.getInstance();
	}

	@Override
	public boolean hasMoreElements() {
		final String method = "hasMoreElements()";
		try {
			name = xfsConf.wfmEnumKey(hKey, iSubKey);
			return true;
		} catch (final XfsException e) {
			switch ((XfsError) e.getError()) {
				case WFS_ERR_CFG_NO_MORE_ITEMS:
					if (LOG.isDebugEnabled()) {
						LOG.debug(method, "No more Items: hKey=" + hKey + ", iSubKey=" + iSubKey, e);
					}
					break;
				default:
					LOG.error(method, "Error enumerating subkey: hKey=" + hKey + ", iSubKey=" + iSubKey, e);
			}
		}
		return false;
	}

	@Override
	public String nextElement() {
		if (name == null) {
			throw new NoSuchElementException();
		}
		iSubKey.set(iSubKey.longValue() + 1);
		return name;
	}

}