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

package at.o2xfs.operator.ui.swing.renderkit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import at.o2xfs.operator.ui.UIMessage;
import at.o2xfs.operator.ui.UIMessage.Severity;
import at.o2xfs.operator.ui.content.text.InputText;
import at.o2xfs.operator.ui.swing.SwingUtil;
import at.o2xfs.operator.ui.swing.i18n.Messages;
import at.o2xfs.operator.ui.swing.util.Colors;

public class InputTextRenderer {

	private static final String BORDER_ID = "at.o2xfs.operator.ui.swing.renderkit.InputTextRenderer.BORDER";

	private class ModifyListener implements DocumentListener {

		@Override
		public void insertUpdate(DocumentEvent e) {
			onModifyText();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			onModifyText();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			onModifyText();
		}
	}

	private final InputText inputText;
	private final JTextField textField;
	private final JLabel fieldMessage;

	public InputTextRenderer(InputText inputText) {
		this.inputText = inputText;
		textField = new JTextField();
		textField.setText(inputText.getValue());
		textField.getDocument().addDocumentListener(new ModifyListener());
		fieldMessage = new JLabel();
	}

	private void onModifyText() {
		inputText.setValue(textField.getText());

	}

	public void showMessage(UIMessage message) {
		setBorder(getForeground(message.getSeverity()));
		fieldMessage.setForeground(getForeground(message.getSeverity()));
		fieldMessage.setText(SwingUtil.textToHTML(Messages.getText(message.getMessageId())));
		fieldMessage.setVisible(true);
	}

	public void hideMessage() {
		fieldMessage.setVisible(false);
		setBorder(Colors.getColor(BORDER_ID));
	}

	private Color getForeground(Severity severity) {
		switch (severity) {
			case FATAL:
			case ERROR:
				return Colors.getColor(Colors.ERROR_ID);
			case WARN:
				return Colors.getColor(Colors.WARN_ID);
			case INFO:
				return Colors.getColor(Colors.INFO_ID);
			default:
				return Color.BLACK;
		}
	}

	private void setBorder(Color color) {
		Border border = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(color), BorderFactory.createEmptyBorder(6, 12, 6, 12));
		textField.setBorder(border);
	}

	public void render(Container container) {
		textField.setFont(textField.getFont().deriveFont(16.0f));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = container.getComponentCount();
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0d;

		container.add(textField, gbc);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridy = container.getComponentCount();
		gbc.weighty = 0.1d;

		JPanel panel = new JPanel(new BorderLayout());
		fieldMessage.setVisible(false);
		panel.add(fieldMessage, BorderLayout.NORTH);

		panel.setPreferredSize(new Dimension(container.getPreferredSize().width, panel.getPreferredSize().height));

		container.add(panel, gbc);
		textField.requestFocusInWindow();
	}
}