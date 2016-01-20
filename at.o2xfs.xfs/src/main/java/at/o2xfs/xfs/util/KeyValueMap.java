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

package at.o2xfs.xfs.util;

import at.o2xfs.win32.LPZZSTR;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.ZSTR;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andreas Fagschlunger
 */
public class KeyValueMap {

	private final static char NUL = '\0';

	public static Map<String, String> from(final LPZZSTR lpzzStr) {
		final String separator = "=";
		final Map<String, String> map = new HashMap<String, String>();
		if (!Pointer.NULL.equals(lpzzStr)) {
			for (final String s : lpzzStr.values()) {
				final int beginIndex = s.indexOf(separator);
				if (beginIndex == -1) {
					map.put(s, null);
					continue;
				}
				final String key = s.substring(0, beginIndex);
				final String value = s.substring(beginIndex + separator.length());
				map.put(key, value);
			}
		}
		return map;
	}

	public static ZSTR toZZString(final Map<String, String> map) {
		return toZZString(map, '=');
	}

	private static ZSTR toZZString(final Map<String, String> map, final char separator) {
		StringBuilder zzString = new StringBuilder();
		if (map.isEmpty()) {
			zzString.append(NUL);
		} else {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				final String key = entry.getKey();
				final String value = entry.getValue();
				if (key.indexOf(separator) != -1) {
					throw new IllegalArgumentException("Key '" + key + "' contains separator " + separator);
				}
				zzString.append(key);
				zzString.append(separator);
				zzString.append(value);
				zzString.append(NUL);
			}
		}
		return new ZSTR(zzString.toString());
	}
}