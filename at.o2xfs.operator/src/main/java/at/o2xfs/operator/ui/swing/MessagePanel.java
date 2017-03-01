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

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class MessagePanel extends JPanel {

	private JLabel label = null;

	private MessagePanel(final Color fg, final Color bg, final Icon icon) {
		super(new BorderLayout());
		setForeground(fg);
		setBackground(bg);
		initComponents(icon);
	}

	private void initComponents(final Icon icon) {
		Border border = new LineBorder(getForeground(), 5);
		border = BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setBorder(border);
		label = new JLabel(icon, SwingConstants.LEFT);
		label.setVerticalTextPosition(SwingConstants.TOP);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setForeground(getForeground());
		add(label, BorderLayout.CENTER);
	}

	public void setText(final String text) {
		label.setText(text);
	}

	public static MessagePanel createErrorPanel() {
		final Color fg = new Color(0x99, 0x00, 0x00);
		final Color bg = new Color(0xEE, 0x98, 0x97);
		final Icon errorIcon = UIManager.getIcon("OptionPane.errorIcon");
		return new MessagePanel(fg, bg, errorIcon);
	}

	public static MessagePanel createWarningPanel() {
		final Color fg = new Color(0x5F, 0x33, 0x34);
		final Color bg = new Color(0xE6, 0xCF, 0x5D);
		final Icon warningIcon = UIManager.getIcon("OptionPane.warningIcon");
		return new MessagePanel(fg, bg, warningIcon);
	}

	public static MessagePanel createQuestionPanel() {
		final Color fg = new Color(0x33, 0x66, 0x33);
		final Color bg = new Color(0x9A, 0xC8, 0x99);
		final Icon icon = UIManager.getIcon("OptionPane.questionIcon");
		return new MessagePanel(fg, bg, icon);
	}

	public static MessagePanel createInformationPanel() {
		final Color fg = new Color(0x63, 0x82, 0xBF);
		final Color bg = new Color(0xCC, 0xCC, 0xFF);
		// final Color bg = new Color(0xB8, 0xCF, 0xF5);
		// final Color bg = new Color(0xEE, 0xEE, 0xEE);
		final Icon icon = UIManager.getIcon("OptionPane.informationIcon");
		return new MessagePanel(fg, bg, icon);
	}
}