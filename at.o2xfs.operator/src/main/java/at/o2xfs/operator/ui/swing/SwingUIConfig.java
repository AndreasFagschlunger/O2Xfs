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

package at.o2xfs.operator.ui.swing;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.config.Config;
import at.o2xfs.operator.ui.input.VirtualKey;
import at.o2xfs.operator.ui.swing.config.FontConfig;

public class SwingUIConfig extends Config {

	private final static Logger LOG = LoggerFactory
			.getLogger(SwingUIConfig.class);

	private final static String KEY_BUTTON_SIZE = "buttonSize";

	private final static String KEY_NUMBER_OF_BUTTONS = "numberOfButtons";

	private final static String KEY_BUTTON = "button";

	private final static String KEY_HOT_KEYS = "hotKeys";

	private final static String KEY_Y_POSITION = "yPosition";

	private final static String KEY_MONITOR_ID = "Monitor.ID";

	private File file = null;

	public SwingUIConfig(final File file) throws IOException {
		this.file = file;
		load(new FileReader(file));
		loadFonts();
	}

	private void loadFonts() {
		new FontConfig(new File(file.getParent(), "font.properties"));
	}

	private String getButtonKey(final String key, final int index) {
		return KEY_BUTTON + "[" + index + "]" + "." + key;
	}

	private Dimension getDimension(final String key) {
		final String value = getValue(key);
		if (value != null) {
			return parseDimension(value);
		}
		return null;
	}

	private Dimension parseDimension(final String s) {
		int xPos = s.indexOf('x');
		int width = Integer.parseInt(s.substring(0, xPos));
		int height = Integer.parseInt(s.substring(xPos + 1));
		return new Dimension(width, height);
	}

	private Integer getInteger(final String key) {
		final String value = getValue(key);
		if (value != null) {
			try {
				return Integer.valueOf(value);
			} catch (NumberFormatException e) {
				if (LOG.isErrorEnabled()) {
					final String method = "getInteger(String)";
					LOG.error(method, "Error parsing Integer: key=" + key
							+ ",value=" + value, e);
				}
			}
		}
		return null;
	}

	public File getRoot() {
		return file.getParentFile();
	}

	public String getValue(final Class<?> clazz, final String... key) {
		return getValue(clazz.getSimpleName() + "."
				+ StringUtils.join(key, '.'));
	}

	public Dimension getButtonSize() {
		return getDimension(KEY_BUTTON_SIZE);
	}

	public int getNumberOfButtons() {
		Integer numberOfButtons = getInteger(KEY_NUMBER_OF_BUTTONS);
		if (numberOfButtons != null) {
			return numberOfButtons.intValue();
		}
		return 0;
	}

	public List<VirtualKey> getHotKeys(final int buttonIndex) {
		final List<VirtualKey> hotKeys = new ArrayList<VirtualKey>();
		final String key = getButtonKey(KEY_HOT_KEYS, buttonIndex);
		hotKeys.addAll(getEnums(key, VirtualKey.class));
		return hotKeys;
	}

	public int getButtonYPosition(final int buttonIndex) {
		final String key = getButtonKey(KEY_Y_POSITION, buttonIndex);
		final Integer yPosition = getInteger(key);
		if (yPosition != null) {
			return yPosition.intValue();
		}
		return 0;
	}

	public GraphicsDevice getScreenDevice() {
		final GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		final String id = getValue(KEY_MONITOR_ID);
		if (id != null) {
			for (final GraphicsDevice gd : ge.getScreenDevices()) {
				if (gd.getIDstring().equals(id)) {
					return gd;
				}
			}
		}
		return ge.getDefaultScreenDevice();
	}
}