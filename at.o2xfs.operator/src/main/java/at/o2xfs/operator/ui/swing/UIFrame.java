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

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.O2XfsOperator;
import at.o2xfs.operator.task.TaskCommand;
import at.o2xfs.operator.ui.UIContent;
import at.o2xfs.operator.ui.input.InputDevice;
import at.o2xfs.operator.ui.input.InputDeviceListener;
import at.o2xfs.operator.ui.input.VirtualKey;
import at.o2xfs.operator.ui.swing.menu.MenuAction;
import at.o2xfs.operator.ui.swing.menu.MenuButton;
import at.o2xfs.operator.ui.swing.menu.TaskMenuAction;

public class UIFrame implements SwingUIConfigKey, InputDeviceListener {

	private static final Logger LOG = LoggerFactory.getLogger(UIFrame.class);

	private JFrame frame = null;

	private ContentPanel contentPanel = null;

	private JScrollPane contentScrollPane = null;

	private List<MenuButton> buttons = null;

	private List<InputDevice> inputDevices = null;

	private SwingUIConfig config = null;

	private List<MenuAction> menuActions = null;

	private int upButtonIndex = -1;

	private int downButtonIndex = -1;

	private int backButtonIndex = -1;

	private int nextButtonIndex = -1;

	public UIFrame(final SwingUIConfig config) {
		buttons = new ArrayList<MenuButton>();
		inputDevices = new ArrayList<InputDevice>();
		menuActions = new ArrayList<MenuAction>();
		this.config = config;
		initComponents();
		initInputDevices();
	}

	private void initComponents() {
		final GraphicsDevice screen = config.getScreenDevice();
		frame = new JFrame(screen.getDefaultConfiguration());
		frame.setTitle("O2XfsOperator");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(final WindowEvent e) {
				O2XfsOperator.getInstance().shutdown();
			}

		});
		final Container contentPane = frame.getContentPane();
		contentPane.setLayout(null);
		contentPane.setPreferredSize(new Dimension(640, 480));
		frame.pack();
		contentPanel = new ContentPanel(this);
		contentScrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		contentScrollPane.setBorder(BorderFactory.createEmptyBorder());
		new Scroller(this);
		initLayout();
		frame.setVisible(true);
	}

	private void initLayout() {
		final UILayout layout = new UILayout(this);
		// TODO: Fallback handling
		upButtonIndex = layout.getUpButtonIndex();
		downButtonIndex = layout.getDownButtonIndex();
		backButtonIndex = layout.getBackButtonIndex();
		nextButtonIndex = layout.getNextButtonIndex();
		for (final MenuButton menuButton : buttons) {
			frame.add(menuButton.getButton());
		}
		frame.repaint();
	}

	protected Dimension getSize() {
		/*
		 * FIXME: final GraphicsDevice graphicsDevice =
		 * frame.getGraphicsConfiguration() .getDevice(); final DisplayMode
		 * displayMode = graphicsDevice.getDisplayMode(); return new
		 * Rectangle(displayMode.getWidth(), displayMode.getHeight());
		 */
		return new Dimension(640, 480);
	}

	protected JScrollPane getContentScrollPane() {
		return contentScrollPane;
	}

	public void setContentBounds(final Rectangle r) {
		contentScrollPane.setBounds(r);
		frame.add(contentScrollPane);
	}

	public List<MenuButton> getButtons() {
		return buttons;
	}

	void clearContents() {
		menuActions.clear();
		for (final MenuButton button : buttons) {
			button.removeMenuAction();
		}
		contentPanel.removeAll();
	}

	protected void setBackCommand(final TaskCommand backCommand) {
		if (backCommand != null) {
			final MenuButton backButton = buttons.get(backButtonIndex);
			if (backButton.hasMenuAction()) {
				for (int i = 0; i < buttons.size(); i++) {
					final MenuButton button = buttons.get(i);
					if (!button.hasMenuAction()) {
						button.setMenuAction(backButton.getMenuAction());
						break;
					}
				}
			}
			setButtonAction(backButtonIndex, backCommand);
		}
	}

	protected void setNextCommand(final TaskCommand command) {
		if (command != null) {
			setButtonAction(nextButtonIndex, command);
		}
	}

	protected void setCommands(final List<TaskCommand> commands) {
		removeTaskMenuActions();
		for (int i = 0; i < buttons.size(); i++) {
			if (i < commands.size()) {
				setButtonAction(i, commands.get(i));
			}
		}
	}

	private void setButtonAction(final int buttonIndex, final TaskCommand command) {
		final MenuButton button = buttons.get(buttonIndex);
		final MenuAction menuAction = new TaskMenuAction(command);
		command.addTaskCommandListener(() -> button.setEnabled(command.isEnabled()));
		button.setMenuAction(menuAction);
		button.setEnabled(command.isEnabled());
		menuActions.add(menuAction);
	}

	private void removeTaskMenuActions() {
		for (final MenuButton button : buttons) {
			for (final MenuAction menuAction : menuActions) {
				if (menuAction.equals(button.getMenuAction())) {
					button.removeMenuAction();
					menuActions.remove(menuAction);
					break;
				}
			}
		}
	}

	protected void setContent(final UIContent content) {
		contentPanel.setContent(content);
	}

	public void dispose() {
		frame.dispose();
	}

	protected MenuButton getUpButton() {
		return buttons.get(upButtonIndex);
	}

	protected MenuButton getDownButton() {
		return buttons.get(downButtonIndex);
	}

	protected MenuButton getNextButton() {
		return buttons.get(nextButtonIndex);
	}

	protected SwingUIConfig getConfig() {
		return config;
	}

	@Override
	public void keyPressed(final VirtualKey key) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				for (MenuButton button : buttons) {
					button.keyPressed(key);
				}
			}
		});
	}

	private void initInputDevices() {
		final String method = "initInputDevices()";
		final List<String> deviceNames = config.getValues(KEY_INPUT_DEVICES);
		for (final String deviceName : deviceNames) {
			final Properties deviceProperties = config.getProperties(deviceName);
			final String className = deviceProperties.getProperty("Class");
			try {
				final Class<InputDevice> clazz = (Class<InputDevice>) Class.forName(className);
				final InputDevice inputDevice = clazz.newInstance();
				inputDevices.add(inputDevice);
				inputDevice.init(deviceProperties);
				inputDevice.addInputDeviceListener(this);
				inputDevice.start();
			} catch (final Exception e) {
				if (LOG.isErrorEnabled()) {
					LOG.error(method, "Error creating InputDevice: " + className, e);
				}
			}
		}
	}

	@Override
	public void supportedKeysChange() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				updateSupportedKeys();
			}
		});
	}

	private void updateSupportedKeys() {
		for (final InputDevice inputDevice : inputDevices) {
			final Set<VirtualKey> supportedKeys = inputDevice.getSupportedKeys();
			for (final VirtualKey key : supportedKeys) {
				for (final MenuButton menuButton : buttons) {
					if (menuButton.isSupportedKey(key) && !menuButton.isActiveKey(key)) {
						menuButton.addActiveKey(key);
					}
				}
			}
		}
	}

	List<InputDevice> getInputDevices() {
		return inputDevices;
	}

	void closeInputDevices() {
		for (final InputDevice inputDevice : inputDevices) {
			inputDevice.removeInputDeviceListener(this);
			inputDevice.stop();
		}
	}
}