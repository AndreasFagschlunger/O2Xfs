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
import java.util.Properties;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.xfs.service.util.ExceptionUtil;

public class ColorParser {

	private static final Logger LOG = LoggerFactory
			.getLogger(ColorParser.class);

	private static final String COLOR_KEYWORDS_PROPERTIES = "color.keywords.properties";

	private static ColorParser instance = null;

	private Properties colorKeywords = null;

	private ColorParser() {
		colorKeywords = new Properties();
		loadColorKeywords();
	}

	private void loadColorKeywords() {
		final String method = "loadColorKeywords()";
		try {
			colorKeywords.load(getClass().getResourceAsStream(
					COLOR_KEYWORDS_PROPERTIES));
		} catch (final IOException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error loading Properties: "
						+ COLOR_KEYWORDS_PROPERTIES, e);
			}
		}
	}

	private static ColorParser getInstance() {
		if (instance == null) {
			instance = new ColorParser();
		}
		return instance;
	}

	private Color parseKeyword(final String s) {
		final String method = "parseKeyword(String)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "s=" + s);
		}
		final String value = colorKeywords.getProperty(s);
		if (value == null) {
			return null;
		}
		return parseHex(value.trim());
	}

	private Color parseHex(final String s) {
		StringBuilder hex = new StringBuilder(s.substring(1));
		if (hex.length() == 3) {
			hex.insert(1, hex.charAt(0));
			hex.insert(3, hex.charAt(2));
			hex.insert(5, hex.charAt(4));
		} else if (hex.length() != 6) {
			return null;
		}
		final int r = Integer.parseInt(hex.substring(0, 2), 16);
		final int g = Integer.parseInt(hex.substring(2, 4), 16);
		final int b = Integer.parseInt(hex.substring(4, 6), 16);
		return new Color(r, g, b);
	}

	public static final Color parse(final String s) {
		if (s == null) {
			ExceptionUtil.nullArgument("s");
		}
		final String color = s.replaceAll("\\s", "");
		Color c = null;
		if (color.startsWith("#")) {
			c = getInstance().parseHex(color);
		} else {
			c = getInstance().parseKeyword(color);
		}
		if (c == null) {
			throw new IllegalArgumentException("Unknown color: " + s);
		}
		return c;
	}
}