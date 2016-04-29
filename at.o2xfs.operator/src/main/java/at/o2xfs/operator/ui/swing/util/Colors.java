package at.o2xfs.operator.ui.swing.util;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

public enum Colors {

	INSTANCE;

	private static final Logger LOG = LoggerFactory.getLogger(Colors.class);

	public static final String ERROR_ID = "ERROR";
	public static final String WARN_ID = "WARN";
	public static final String INFO_ID = "INFO";

	private final Properties properties = new Properties();

	private final Map<String, Color> colors = new HashMap<>();

	private Colors() {
		loadProperties();
	}

	private void loadProperties() {
		try {
			properties.load(Files.newInputStream(Paths.get("config", "colors.properties")));
		} catch (IOException e) {
			LOG.error("loadProperties()", "Error loading properties", e);
		}
	}

	private Color doGetColor(String colorId) {
		String method = "doGetColor(String)";
		Color result = Color.BLACK;
		if (colors.containsKey(colorId)) {
			result = colors.get(colorId);
		} else {
			String value = properties.getProperty(colorId);
			if (value != null) {
				try {
					result = ColorParser.parse(value);
				} catch (IllegalArgumentException e) {
					LOG.warn(method, "Illegal color: " + value, e);
				}
			}
		}
		return result;
	}

	public static Color getColor(String colorId) {
		return INSTANCE.doGetColor(colorId);
	}
}
