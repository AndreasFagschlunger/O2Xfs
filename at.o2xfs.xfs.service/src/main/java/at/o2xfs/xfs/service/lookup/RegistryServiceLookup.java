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

package at.o2xfs.xfs.service.lookup;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.HKEY;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.XfsServiceClass;
import at.o2xfs.xfs.conf.O2XfsConf;

public class RegistryServiceLookup {

	private final Logger LOG = LoggerFactory
			.getLogger(RegistryServiceLookup.class);

	private final static String KEY_NAME_LOGICAL_SERVICES = "LOGICAL_SERVICES";

	private final static String VALUE_NAME_CLASS = "Class";

	private O2XfsConf xfsConf = null;

	private HKEY hKeys[] = null;

	public RegistryServiceLookup() {
		hKeys = new HKEY[] { O2XfsConf.WFS_CFG_HKEY_XFS_ROOT,
				O2XfsConf.WFS_CFG_HKEY_USER_DEFAULT_XFS_ROOT };
		xfsConf = O2XfsConf.getInstance();
	}

	private XfsServiceClass queryServiceClass(final HKEY hKey) {
		final String method = "queryServiceClass(HKEY)";
		try {
			final String className = xfsConf.wfmQueryValue(hKey,
					VALUE_NAME_CLASS);
			return XfsServiceClass.getForServiceClassName(className);
		} catch (XfsException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error querying key value: hKey=" + hKey, e);
			}
		}
		return null;
	}

	private XfsServiceClass retrievesServiceClass(final HKEY hKey,
			final String name) {
		final String method = "retrievesServiceClass(HKEY, String)";
		HKEY subKey = null;
		try {
			subKey = xfsConf.wfmOpenKey(hKey, name);
			return queryServiceClass(subKey);
		} catch (XfsException e) {
			LOG.error(method, "Error opening registry key: hKey=" + hKey
					+ ", name=" + name, e);
		} finally {
			if (subKey != null) {
				try {
					xfsConf.wfmCloseKey(subKey);
				} catch (XfsException e) {
					LOG.error(method, "Error closing registry key: subKey="
							+ subKey, e);
				}
			}
		}
		return null;
	}

	public Map<String, XfsServiceClass> lookup() {
		final String method = "lookup()";
		Map<String, XfsServiceClass> services = new HashMap<String, XfsServiceClass>();
		for (final HKEY hKey : hKeys) {
			HKEY subKey = null;
			try {
				subKey = xfsConf.wfmOpenKey(hKey, KEY_NAME_LOGICAL_SERVICES);
				final Enumeration<String> enumeration = new RegistryKeyEnumerator(
						subKey);
				while (enumeration.hasMoreElements()) {
					final String logicalName = enumeration.nextElement();
					if (services.containsKey(logicalName)) {
						continue;
					}
					final XfsServiceClass serviceClass = retrievesServiceClass(
							subKey, logicalName);
					if (serviceClass != null) {
						services.put(logicalName, serviceClass);
					}
				}
			} catch (XfsException e) {
				LOG.error(method, "Error opening registry Key: hKey=" + hKey, e);
			} finally {
				if (subKey != null) {
					try {
						xfsConf.wfmCloseKey(subKey);
					} catch (XfsException e) {
						LOG.error(method, "Error closing subKey: " + subKey, e);
					}
				}
			}
		}
		return services;
	}

}