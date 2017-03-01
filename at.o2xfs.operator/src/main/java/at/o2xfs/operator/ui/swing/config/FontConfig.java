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

package at.o2xfs.operator.ui.swing.config;

import java.io.File;
import java.io.FileReader;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

public class FontConfig {

	private static final Logger LOG = LoggerFactory.getLogger(FontConfig.class);

	private final static String FONT_NAME_SUFFIX = ".name";

	private final static String FONT_STYLE_SUFFIX = ".style";

	private final static String FONT_SIZE_SUFFIX = ".size";

	private Properties properties = null;

	public FontConfig(final File file) {
		properties = new Properties();
		try {
			properties.load(new FileReader(file));
		} catch (final Exception e) {
			final String method = "FontConfig(File)";
			if (LOG.isErrorEnabled()) {
				LOG.error(
						method,
						"Error loading Properties from file: "
								+ file.getAbsolutePath(), e);
			}
		}
		mergeUIDefaults();
	}

	private String getFontName(final Object key) {
		return properties.getProperty(key + FONT_NAME_SUFFIX);
	}

	private Integer getFontStyle(final Object key) {
		final String fontStyle = properties
				.getProperty(key + FONT_STYLE_SUFFIX);
		if (fontStyle != null) {
			return Integer.valueOf(fontStyle);
		}
		return null;
	}

	private Integer getFontSize(final Object key) {
		final String fontSize = properties.getProperty(key + FONT_SIZE_SUFFIX);
		if (fontSize != null) {
			return Integer.valueOf(fontSize);
		}
		return null;
	}

	private void mergeUIDefaults() {
		final String method = "mergeUIDefaults()";
		final Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			final Object key = keys.nextElement();
			final Object value = UIManager.get(key);
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "key=" + key + ",value=" + value);
			}
			if (value instanceof FontUIResource) {
				String fontName = getFontName(key);
				Integer fontStyle = getFontStyle(key);
				Integer fontSize = getFontSize(key);
				if (fontName != null || fontStyle != null || fontSize != null) {
					final FontUIResource font = (FontUIResource) value;
					if (fontName == null) {
						fontName = font.getName();
					}
					if (fontStyle == null) {
						fontStyle = Integer.valueOf(font.getStyle());
					}
					if (fontSize == null) {
						fontSize = Integer.valueOf(font.getSize());
					}
					final FontUIResource newFont = new FontUIResource(fontName,
							fontStyle.intValue(), fontSize.intValue());
					UIManager.put(key, newFont);
				}
			}
		}
	}
}