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

package at.o2xfs.operator.config;

import java.awt.Rectangle;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

public class Config {

	private final static Logger LOG = LoggerFactory.getLogger(Config.class);

	private Properties properties = null;

	public Config() {
		properties = new Properties();
	}

	public Config(final Properties properties) {
		this.properties = properties;
	}

	public void load(final Reader reader) throws IOException {
		final String method = "load(Reader)";
		try {
			properties.load(reader);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (final IOException e) {
					LOG.error(method, "Error closing Reader", e);
				}
			}
		}
	}

	public String getValue(final String key) {
		final String value = properties.getProperty(key);
		if (LOG.isDebugEnabled()) {
			final String method = "getValue(String)";
			LOG.debug(method, "key=" + key + ",value=" + value);
		}
		return value;
	}

	public String getTrimmedValue(final String key) {
		final String value = getValue(key);
		if (value != null) {
			return value.trim();
		}
		return null;
	}

	public List<String> getValues(final String key) {
		final List<String> values = new ArrayList<String>();
		final String value = getValue(key);
		if (value != null) {
			values.addAll(Arrays.asList(value.split(",")));
		}
		return values;
	}

	public Map<String, String> getMap(final String key) {
		final Map<String, String> map = new HashMap<String, String>();
		final List<String> values = getValues(key);
		for (final String value : values) {
			final String[] entry = value.split(":");
			map.put(entry[0], entry[1]);
		}
		return map;
	}

	public <E extends Enum<E>> Set<E> getEnums(final String key,
			final Class<E> elementType) {
		final List<String> values = getValues(key);
		final EnumSet<E> enums = EnumSet.noneOf(elementType);
		for (String value : values) {
			value = value.trim();
			boolean match = false;
			for (final E e : elementType.getEnumConstants()) {
				if (e.name().equals(value)) {
					enums.add(e);
					match = true;
					break;
				}
			}
			if (!match) {
				if (LOG.isWarnEnabled()) {
					final String method = "getEnums(String, Class<E>)";
					LOG.warn(method, "Enum name " + value
							+ " does not match any " + elementType.getName()
							+ " enum constant");
				}
			}
		}
		return enums;
	}

	public Properties getProperties(final String keyPrefix) {
		final Properties result = new Properties();
		final Enumeration<String> keys = (Enumeration<String>) properties
				.propertyNames();
		final int prefixLength = keyPrefix.length() + 1;
		while (keys.hasMoreElements()) {
			final String key = keys.nextElement();
			if (key.startsWith(keyPrefix)) {
				final String newKey = key.substring(prefixLength);
				result.put(newKey, getValue(key));
			}
		}
		return result;
	}

	public boolean getBoolean(final String key) {
		return Boolean.parseBoolean(getValue(key));
	}

	public boolean getBoolean(final String key, final boolean defaultValue) {
		final String value = getValue(key);
		if (value != null) {
			return Boolean.parseBoolean(value);
		}
		return defaultValue;
	}

	public long getLong(final String key) {
		return Long.parseLong(getTrimmedValue(key));
	}

	public int getInt(final String key) {
		return Integer.parseInt(getTrimmedValue(key));
	}

	public Rectangle getRectangle(final String key) {
		final int x = getInt(key + ".x");
		final int y = getInt(key + ".y");
		final int width = getInt(key + ".width");
		final int height = getInt(key + ".height");
		return new Rectangle(x, y, width, height);
	}

}