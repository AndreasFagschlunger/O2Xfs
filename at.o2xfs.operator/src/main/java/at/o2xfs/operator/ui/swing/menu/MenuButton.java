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

package at.o2xfs.operator.ui.swing.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import org.apache.commons.lang3.builder.ToStringBuilder;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.ui.input.VirtualKey;
import at.o2xfs.operator.ui.swing.SwingUtil;
import at.o2xfs.operator.ui.swing.i18n.Messages;

public class MenuButton implements ActionListener {

	private static final Logger LOG = LoggerFactory.getLogger(MenuButton.class);

	private JButton button = null;

	private List<VirtualKey> supportedKeys = null;

	private List<VirtualKey> activeKeys = null;

	private boolean shortcutsVisible = false;

	private MenuAction menuAction = null;

	public MenuButton() {
		button = new JButton();
		button.setFocusPainted(false);
		button.setVisible(false);
		button.addActionListener(this);
		supportedKeys = new ArrayList<VirtualKey>();
		activeKeys = new ArrayList<VirtualKey>();
	}

	public JButton getButton() {
		return button;
	}

	public MenuAction getMenuAction() {
		return menuAction;
	}

	public boolean hasMenuAction() {
		return menuAction != null;
	}

	public void setMenuAction(final MenuAction menuAction) {
		this.menuAction = menuAction;
		setText(this.menuAction.getText());
		button.setEnabled(true);
		button.setVisible(true);
	}

	private void setText(String text) {
		if (shortcutsVisible && !activeKeys.isEmpty()) {
			text += getActiveKeysString();
		}
		button.setText(SwingUtil.textToHTML(text.toString()));
	}

	public void removeMenuAction() {
		menuAction = null;
		button.setVisible(false);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (LOG.isInfoEnabled()) {
			final String method = "actionPerformed(ActionEvent)";
			LOG.info(method, "Pressed: " + button.getText());
		}
		menuAction.actionPerformed();
	}

	public void setEnabled(boolean b) {
		button.setEnabled(b);
	}

	private String getActiveKeysString() {
		final StringBuilder s = new StringBuilder(" (");
		int iMax = activeKeys.size() - 1;
		for (int i = 0;; i++) {
			final VirtualKey activeKey = activeKeys.get(i);
			s.append(Messages.getText(activeKey.name()));
			if (i == iMax) {
				break;
			}
			s.append(", ");
		}
		s.append(')');
		return s.toString();
	}

	public void addSupportedKey(final VirtualKey key) {
		if (!supportedKeys.contains(key)) {
			supportedKeys.add(key);
		}
	}

	public boolean isSupportedKey(final VirtualKey key) {
		return supportedKeys.contains(key);
	}

	public void addActiveKey(final VirtualKey key) {
		activeKeys.add(key);
	}

	public boolean isActiveKey(final VirtualKey key) {
		return activeKeys.contains(key);
	}

	public void keyPressed(final VirtualKey key) {
		if (button.isVisible() && isActiveKey(key)) {
			button.doClick();
		}
	}

	public void setShortcutsVisible(final boolean shortcutsVisible) {
		this.shortcutsVisible = shortcutsVisible;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("button", button).append("supportedKeys", supportedKeys).append("activeKeys", activeKeys)
				.append("shortcutsVisible", shortcutsVisible).toString();
	}

}