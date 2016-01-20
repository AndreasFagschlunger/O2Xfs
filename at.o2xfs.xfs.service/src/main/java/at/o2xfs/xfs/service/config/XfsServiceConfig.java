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

package at.o2xfs.xfs.service.config;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.XfsAPI;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.cmd.XfsInfoCommand;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class XfsServiceConfig {

	private static final Logger LOG = LoggerFactory.getLogger(XfsServiceConfig.class);

	private static final ConfigKey KEY_TIMEOUT_WFSGETINFO = new ConfigKey("WFSGetInfo", "TimeOut");
	private static final ConfigKey KEY_TIMEOUT_WFSEXECUTE = new ConfigKey("WFSExecute", "TimeOut");

	private static XfsServiceConfig instance = null;

	private final Properties properties;

	private XfsServiceConfig() {
		properties = new Properties();
		load();
	}

	private void load() {
		final String method = "load()";
		File file = new File("xfs.properties");
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(file);
			properties.load(inStream);
		} catch (IOException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error loading Properties: " + file.getAbsolutePath(), e);
			}
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					if (LOG.isErrorEnabled()) {
						LOG.error(method, "Error closing stream: " + file.getAbsolutePath(), e);
					}
				}
			}
		}
	}

	private String getValue(ConfigKey key) {
		String value = properties.getProperty(key.getKey());
		return value;
	}

	public Integer getInteger(ConfigKey key) {
		return getInteger(key, null);
	}

	public Integer getInteger(ConfigKey key, Integer defaultValue) {
		final String method = "getInteger(ConfigKey, Integer)";
		Integer result = defaultValue;
		String value = getValue(key);
		if (value != null) {
			try {
				result = Integer.valueOf(value);
			} catch (NumberFormatException e) {
				if (LOG.isErrorEnabled()) {
					LOG.error(method, "Error parsing Integer: key=" + key + ",value=" + value, e);
				}
			}
		}
		return result;
	}

	public Long getLong(ConfigKey key) {
		return getLong(key, null);
	}

	public Long getLong(ConfigKey key, Long defaultValue) {
		final String method = "getLong(ConfigKey, Long)";
		Long result = defaultValue;
		String value = getValue(key);
		if (value != null) {
			try {
				result = Long.valueOf(value);
			} catch (NumberFormatException e) {
				if (LOG.isErrorEnabled()) {
					LOG.error(method, "Error parsing Long: key=" + key + ",value=" + value, e);
				}
			}
		}
		return result;
	}

	public boolean getBoolean(ConfigKey configKey) {
		boolean result = false;
		String s = getValue(configKey);
		if (s != null) {
			result = Boolean.parseBoolean(s);
		}
		return result;
	}

	public Class<?> getClass(ConfigKey key) {
		String className = getValue(key);
		if (className != null) {
			try {
				return Class.forName(className);
			} catch (ClassNotFoundException e) {
				final String method = "getClass(ConfigKey)";
				if (LOG.isErrorEnabled()) {
					LOG.error(method, "Error getting class: " + className, e);
				}
			}
		}
		return null;
	}

	public Long getXfsInfoTimeout(XfsInfoCommand<?> command) {
		return getLong(KEY_TIMEOUT_WFSGETINFO, Long.valueOf(XfsAPI.WFS_INDEFINITE_WAIT));
	}

	public Long getXfsExecuteTimeout(XfsExecuteCommand<?> command) {
		return getLong(KEY_TIMEOUT_WFSEXECUTE, Long.valueOf(XfsAPI.WFS_INDEFINITE_WAIT));
	}

	public <T> T newInstance(ConfigKey configKey, Class<T> type) {
		String className = getValue(configKey);
		if (className != null) {
			try {
				Class<?> clazz = Class.forName(className.trim());
				return type.cast(clazz.newInstance());
			} catch (Exception e) {
				final String method = "newInstance(ConfigKey, Class<T>)";
				if (LOG.isErrorEnabled()) {
					LOG.error(method, "Error creating new instance from class: " + className, e);
				}
			}
		}
		return null;
	}

	public static XfsServiceConfig getInstance() {
		if (instance == null) {
			synchronized (XfsServiceConfig.class) {
				if (instance == null) {
					instance = new XfsServiceConfig();
				}
			}
		}
		return instance;
	}
}