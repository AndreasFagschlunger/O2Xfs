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
import java.awt.Rectangle;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.config.Config;
import at.o2xfs.operator.task.xfs.pin.PINKeyUtil;
import at.o2xfs.operator.ui.input.VirtualKey;
import at.o2xfs.operator.ui.swing.menu.MenuButton;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.pin.WFSPINFDK;
import at.o2xfs.xfs.pin.WFSPINFUNCKEYDETAIL;
import at.o2xfs.xfs.service.XfsService;
import at.o2xfs.xfs.service.XfsServiceListener;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.pin.PINService;
import at.o2xfs.xfs.service.pin.cmd.PINFunctionKeysCommand;

public class UILayout implements XfsServiceListener {

	private final static Logger LOG = LoggerFactory.getLogger(UILayout.class);

	private UIFrame uiFrame = null;

	private Config config = null;

	private XfsServiceManager xfsManager = null;

	public UILayout(final UIFrame uiFrame) {
		this.uiFrame = uiFrame;
		config = new Config();
		loadConfig();
		initContentBounds();
		initButtons();
		if (isXfsEnabled()) {
			tryXfsLayout();
		}
	}

	private void loadConfig() {
		final String method = "loadConfig()";
		final SwingUIConfig uiConfig = uiFrame.getConfig();
		final String fileName = uiConfig.getValue(getClass(), "File");
		final Dimension resolution = uiFrame.getSize();
		File file = new File(uiConfig.getRoot(), resolution.width + "x"
				+ resolution.height);
		file = new File(file, fileName);
		try {
			config.load(new FileReader(file));
		} catch (final Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error loading configuration from file: "
						+ file.getAbsolutePath(), e);
			}
		}
	}

	private void initContentBounds() {
		final String key = "Content";
		final Rectangle contentBounds = config.getRectangle(key);
		uiFrame.setContentBounds(contentBounds);
	}

	private void initButtons() {
		final String method = "initButtons()";
		final int numberOfButtons = config.getInt("numberOfButtons");
		final boolean displayKeys = config.getBoolean("Display.Keys");
		for (int i = 0; i < numberOfButtons; i++) {
			final MenuButton menuButton = createMenuButton(i);
			menuButton.setShortcutsVisible(displayKeys);
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "Adding MenuButton " + i + ": " + menuButton);
			}
			uiFrame.getButtons().add(menuButton);
		}
	}

	private MenuButton createMenuButton(final int index) {
		final MenuButton menuButton = new MenuButton();
		menuButton.getButton().setBounds(getButtonBounds(index));
		for (final VirtualKey key : getButtonKeys(index)) {
			if (!isAlreadyAssigned(key)) {
				menuButton.addSupportedKey(key);
			}
		}
		return menuButton;
	}

	private boolean isAlreadyAssigned(final VirtualKey key) {
		final String method = "isAlreadyAssigned(VirtualKey)";
		final List<MenuButton> menuButtons = uiFrame.getButtons();
		for (final MenuButton menuButton : menuButtons) {
			if (menuButton.isSupportedKey(key)) {
				if (LOG.isWarnEnabled()) {
					LOG.warn(method, "Duplicate assignment of VirtualKey: "
							+ key);
				}
				return true;
			}
		}
		return false;
	}

	private Rectangle getButtonBounds(final int index) {
		final String key = "button[" + index + "]";
		final Rectangle bounds = config.getRectangle(key);
		return bounds;
	}

	private Set<VirtualKey> getButtonKeys(final int index) {
		final Set<VirtualKey> keys = config.getEnums("button[" + index
				+ "].keys", VirtualKey.class);
		return keys;
	}

	private boolean isXfsEnabled() {
		return config.getBoolean("XfsEnabled", false);
	}

	private void tryXfsLayout() {
		xfsManager = XfsServiceManager.getInstance();
		synchronized (this) {
			final PINService pinService = getPINService();
			if (pinService == null) {
				xfsManager.addXfsServiceListener(this);
			} else {
				initXfsLayout(pinService);
			}
		}
	}

	@Override
	public void xfsServiceStarted(final XfsService xfsService) {
		synchronized (this) {
			final PINService pinService = getPINService();
			if (pinService != null) {
				initXfsLayout(pinService);
				xfsManager.removeXfsServiceListener(this);
			}
		}
	}

	private PINService getPINService() {
		final String serviceName = config.getValue("XfsService");
		if (serviceName != null) {
			return xfsManager.getService(serviceName, PINService.class);
		} else {
			final List<PINService> pinServices = xfsManager
					.getServices(PINService.class);
			if (!pinServices.isEmpty()) {
				return pinServices.get(0);
			}
		}
		return null;
	}

	private void initXfsLayout(final PINService pinService) {
		final String method = "initXfsLayout(PINService)";
		WFSPINFUNCKEYDETAIL funcKeyDetail = null;
		try {
			funcKeyDetail = new PINFunctionKeysCommand(pinService).call();
		} catch (final XfsException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error getting function key information", e);
			}
			return;
		}
		final WFSPINFDK[] pinFDKs = funcKeyDetail.getFDKs();
		for (final WFSPINFDK pinFDK : pinFDKs) {
			applyXfsPosition(pinFDK);
		}
	}

	private void applyXfsPosition(final WFSPINFDK pinFDK) {
		final String method = "applyXfsPosition(WFSPINFDK)";
		final VirtualKey key = PINKeyUtil.getVirtualKey(pinFDK.getFDK());
		final MenuButton menuButton = getMenuButton(key);
		if (menuButton == null) {
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "Could not find MenuButton for VirtualKey: "
						+ key);
			}
			return;
		}
		final Dimension frameSize = uiFrame.getSize();
		final JButton button = menuButton.getButton();
		final Dimension buttonSize = button.getSize();
		final int x = calcXPosition(buttonSize, pinFDK.getXPosition(),
				frameSize.width);
		final int y = calcYPosition(buttonSize, pinFDK.getYPosition(),
				frameSize.height);
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "x=" + x + ",y=" + y + ",menuButton="
					+ menuButton);
		}
		button.setBounds(new Rectangle(x, y, buttonSize.width,
				buttonSize.height));
	}

	private int calcXPosition(final Dimension buttonSize, final int xPosition,
			final int width) {
		double x = ((width - buttonSize.getWidth()) / 100.0d) * xPosition;
		return (int) x;
	}

	private int calcYPosition(final Dimension buttonSize, final int yPosition,
			final int height) {
		double halfHeight = buttonSize.getHeight() / 2;
		double y = ((height - halfHeight) / 100.0d) * yPosition;
		y -= halfHeight;
		return (int) Math.max(0, (int) y);

	}

	private MenuButton getMenuButton(final VirtualKey key) {
		final List<MenuButton> menuButtons = uiFrame.getButtons();
		for (final MenuButton menuButton : menuButtons) {
			if (menuButton.isSupportedKey(key)) {
				return menuButton;
			}
		}
		return null;
	}

	protected int getUpButtonIndex() {
		return config.getInt("upButtonIndex");
	}

	protected int getDownButtonIndex() {
		return config.getInt("downButtonIndex");
	}

	protected int getBackButtonIndex() {
		return config.getInt("backButtonIndex");
	}

	protected int getNextButtonIndex() {
		return config.getInt("nextButtonIndex");
	}

}