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