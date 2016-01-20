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

package at.o2xfs.xfs.conf;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.DWORD;
import at.o2xfs.win32.HKEY;
import at.o2xfs.xfs.XfsException;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author Andreas Fagschlunger
 */
public class WFMValueIterator
		implements Iterator<Map.Entry<String, String>>, Iterable<Map.Entry<String, String>> {

	private final static Logger LOG = LoggerFactory.getLogger(WFMValueIterator.class);

	private O2XfsConf xfsConf = null;

	private HKEY hKey = null;

	private DWORD iValue = null;

	private Map.Entry<String, String> value = null;

	public WFMValueIterator(final HKEY hKey) {
		xfsConf = O2XfsConf.getInstance();
		this.hKey = hKey;
		iValue = new DWORD(0);
	}

	@Override
	public Iterator<Map.Entry<String, String>> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		final String method = "hasNext()";
		try {
			value = xfsConf.wfmEnumValue(hKey, iValue);
			return true;
		} catch (CfgNoMoreItemsException e) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "No more values: hKey=" + hKey + ",iValue=" + iValue, e);
			}
		} catch (XfsException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error enumerating value: hKey=" + hKey + ",iValue=" + iValue, e);
			}
		}
		return false;
	}

	@Override
	public Map.Entry<String, String> next() {
		if (value == null) {
			throw new NoSuchElementException("iValue: " + iValue);
		}
		iValue.set(iValue.longValue() + 1L);
		try {
			return value;
		} finally {
			value = null;
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}