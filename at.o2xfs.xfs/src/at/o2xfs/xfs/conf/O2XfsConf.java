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

package at.o2xfs.xfs.conf;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.DWORD;
import at.o2xfs.win32.HKEY;
import at.o2xfs.win32.ZSTR;
import at.o2xfs.xfs.XfsException;

/**
 * @author Andreas Fagschlunger
 */
public class O2XfsConf {

	private final static Logger LOG = LoggerFactory.getLogger(O2XfsConf.class);

	private class ValuePair implements Map.Entry<String, String> {

		private String name = null;

		private String data = null;

		private ValuePair(final String name, final String data) {
			this.name = name;
			this.data = data;
		}

		@Override
		public String getKey() {
			return name;
		}

		@Override
		public String getValue() {
			return data;
		}

		@Override
		public String setValue(String value) {
			throw new UnsupportedOperationException();
		}
	}

	private final static int SIZE_LIMIT = 256;

	public final static HKEY WFS_CFG_HKEY_XFS_ROOT = new HKEY(1L);

	/**
	 * @since 3.0
	 */
	public final static HKEY WFS_CFG_HKEY_MACHINE_XFS_ROOT = new HKEY(2L);

	/**
	 * @since 3.0
	 */
	public final static HKEY WFS_CFG_HKEY_USER_DEFAULT_XFS_ROOT = new HKEY(3L);

	private List<HKEY> openKeys = null;

	private static O2XfsConf instance = null;

	private O2XfsConf() {
		System.loadLibrary("O2XfsConf");
		openKeys = new ArrayList<HKEY>();
	}

	public static O2XfsConf getInstance() {
		if (instance == null) {
			synchronized (O2XfsConf.class) {
				if (instance == null) {
					instance = new O2XfsConf();
				}
			}
		}
		return instance;
	}

	/**
	 * Closes the specified key.
	 * 
	 * @param hKey
	 *            Handle to the currently open key that is to be closed.
	 * @throws XfsException
	 */
	public void wfmCloseKey(final HKEY hKey) throws XfsException {
		final int errorCode = wfmCloseKey(hKey.buffer());
		XfsException.throwFor(errorCode);
		synchronized (openKeys) {
			openKeys.remove(hKey);
		}
	}

	private native int wfmCloseKey(ByteBuffer hKey);

	/**
	 * Opens the specified key.
	 */
	public HKEY wfmOpenKey(final HKEY hKey, final String subKey)
			throws XfsException {
		HKEY hkResult = new HKEY();
		final int errorCode = wfmOpenKey(hKey.buffer(), (subKey == null ? null
				: new ZSTR(subKey).buffer()), hkResult.buffer());
		XfsException.throwFor(errorCode);
		synchronized (openKeys) {
			openKeys.add(hkResult);
		}
		return hkResult;
	}

	private native int wfmOpenKey(final ByteBuffer hKey,
			final ByteBuffer lpszSubKey, final ByteBuffer phkResult);

	/**
	 * Retrieves the data for the value with the specified name, within the
	 * specified open key.
	 */
	public String wfmQueryValue(final HKEY hKey, final String valueName)
			throws XfsException {
		final ZSTR data = new ZSTR(SIZE_LIMIT, true);
		final int errorCode = wfmQueryValue(hKey.buffer(),
				new ZSTR(valueName).buffer(), data.buffer());
		XfsException.throwFor(errorCode);
		return data.toString();
	}

	private native int wfmQueryValue(ByteBuffer hKey, ByteBuffer lpszValueName,
			ByteBuffer lpszData);

	/**
	 * Enumerates the subkeys of the specified open key. Retrieves information
	 * about one subkey each time it is called.
	 * 
	 * @param hKey
	 *            Handle to a currently open key, or the predefined handle
	 *            value: {@link #WFS_CFG_HKEY_XFS_ROOT}
	 * @return the name of the subkey
	 */
	public String wfmEnumKey(final HKEY key, final DWORD iSubKey)
			throws XfsException {
		final ZSTR name = new ZSTR(SIZE_LIMIT, true);
		final int errorCode = wfmEnumKey(key.buffer(), iSubKey.buffer(),
				name.buffer());
		XfsException.throwFor(errorCode);
		return name.toString();
	}

	private native int wfmEnumKey(ByteBuffer hKey, ByteBuffer iSubKey,
			ByteBuffer lpszName);

	/**
	 * Enumerates the values of the specified open key. Retrieves the name and
	 * data for one value each time it is called.
	 */
	public Map.Entry<String, String> wfmEnumValue(final HKEY hKey,
			final DWORD iValue) throws XfsException {
		ZSTR value = new ZSTR(SIZE_LIMIT, true);
		ZSTR data = new ZSTR(SIZE_LIMIT, true);
		final int errorCode = wfmEnumValue(hKey.buffer(), iValue.buffer(),
				value.buffer(), data.buffer());
		XfsException.throwFor(errorCode);
		return new ValuePair(value.toString(), data.toString());
	}

	private native int wfmEnumValue(ByteBuffer hKey, ByteBuffer iValue,
			ByteBuffer lpszValue, ByteBuffer lpszData);

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		final String method = "finalize()";
		synchronized (openKeys) {
			if (openKeys.size() > 0) {
				if (LOG.isWarnEnabled()) {
					LOG.warn(method, "Closing " + openKeys.size()
							+ " open key(s)");
				}
			}
			while (openKeys.size() > 0) {
				HKEY hKey = openKeys.get(0);
				try {
					wfmCloseKey(hKey);
				} catch (XfsException e) {
					if (LOG.isErrorEnabled()) {
						LOG.error(method, "Error closing HKEY: " + hKey, e);
					}
				}
			}
		}
	}

}
