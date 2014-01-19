package at.o2xfs.xfs.service.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

public final class XfsServiceConfig {

	private static final Logger LOG = LoggerFactory
			.getLogger(XfsServiceConfig.class);

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
				LOG.error(method,
						"Error loading Properties: " + file.getAbsolutePath(),
						e);
			}
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					if (LOG.isErrorEnabled()) {
						LOG.error(
								method,
								"Error closing stream: "
										+ file.getAbsolutePath(), e);
					}
				}
			}
		}
	}

	private String getValue(ConfigKey<?> configKey) {
		String value = properties.getProperty(configKey.getKey());
		return value;
	}

	public boolean getBoolean(ConfigKey<Boolean> configKey) {
		Boolean result = null;
		String s = getValue(configKey);
		if (s != null) {
			result = Boolean.parseBoolean(s);
		} else if (configKey.getDefaultValue() != null) {
			result = configKey.getDefaultValue();
		} else {
			result = Boolean.FALSE;
		}
		return result.booleanValue();
	}

	public Class<?> getClass(ConfigKey<Class<?>> configKey) {
		String className = getValue(configKey);
		if (className != null) {
			try {
				return Class.forName(className);
			} catch (ClassNotFoundException e) {
				final String method = "getClass(ConfigKey<Class<?>>)";
				if (LOG.isErrorEnabled()) {
					LOG.error(method, "Error getting class", e);
				}
			}
		}
		return configKey.getDefaultValue();
	}

	public <T> T newInstance(ConfigKey<String> configKey, Class<T> type) {
		String className = getValue(configKey);
		if (className == null) {
			className = configKey.getDefaultValue();
		}
		if (className != null) {
			try {
				Class<?> clazz = Class.forName(className.trim());
				return type.cast(clazz.newInstance());
			} catch (Exception e) {
				final String method = "getInstance(ConfigKey<String>, Class<T>)";
				if (LOG.isErrorEnabled()) {
					LOG.error(method, "", e);
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
