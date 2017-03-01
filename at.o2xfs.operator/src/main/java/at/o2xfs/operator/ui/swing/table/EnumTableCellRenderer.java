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

package at.o2xfs.operator.ui.swing.table;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import javax.swing.table.DefaultTableCellRenderer;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.ui.swing.i18n.Messages;
import at.o2xfs.operator.ui.swing.util.ColorParser;

public class EnumTableCellRenderer extends DefaultTableCellRenderer {

	private static final Logger LOG = LoggerFactory
			.getLogger(EnumTableCellRenderer.class);

	private Properties colorProperties = null;

	private Color defaultColor = null;

	public EnumTableCellRenderer() {
		colorProperties = new Properties();
		defaultColor = getForeground();
		loadProperties();
	}

	private void loadProperties() {
		Reader reader = null;
		try {
			// FIXME:
			reader = new FileReader(new File("config/enum.color.properties"));
			colorProperties.load(reader);
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private Color getColor(final Enum<?> e) {
		final String s = colorProperties.getProperty(e.name());
		if (s != null) {
			try {
				return ColorParser.parse(s);
			} catch (final IllegalArgumentException exc) {
				if (LOG.isWarnEnabled()) {
					final String method = "getColor(Enum<?>)";
					LOG.warn(method, "Invalid color: s=" + s + ", e=" + e, exc);
				}
			}
		}
		return defaultColor;
	}

	@Override
	protected void setValue(final Object value) {
		final Enum<?> e = (Enum<?>) value;
		final String text = Messages.getText(e);
		setText(text);
		setForeground(getColor(e));
	}

}