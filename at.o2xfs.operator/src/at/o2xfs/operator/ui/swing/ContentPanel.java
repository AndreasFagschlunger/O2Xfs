/*
 * Copyright (c) 2012, Andreas Fagschlunger. All rights reserved.
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

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.operator.ui.content.text.ErrorMessage;
import at.o2xfs.operator.ui.content.text.Label;
import at.o2xfs.operator.ui.content.text.TextChangedListener;
import at.o2xfs.operator.ui.content.text.TextInput;
import at.o2xfs.operator.ui.content.text.WarningLabel;
import at.o2xfs.operator.ui.swing.i18n.Messages;
import at.o2xfs.operator.ui.swing.table.RowSelector;
import at.o2xfs.operator.ui.swing.table.SwingUITable;
import at.o2xfs.operator.ui.swing.table.TableAdjuster;

public class ContentPanel extends JPanel {

	private static final Logger LOG = LoggerFactory
			.getLogger(ContentPanel.class);

	private UIFrame uiFrame = null;

	public ContentPanel(final UIFrame uiFrame) {
		super();
		this.uiFrame = uiFrame;
		setLayout(new GridBagLayout());
	}

	private void addComponent(final Component component) {
		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = getComponentCount();
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0d;
		if (component instanceof JTable) {
			gbc.weighty = 1.0d;
		}
		add(component, gbc);
	}

	private void addWarning(final Label label) {
		final MessagePanel warningPanel = MessagePanel.createWarningPanel();
		warningPanel.setText(Messages.getText(label));
		addComponent(warningPanel);
	}

	private void addError(final ErrorMessage error) {
		final MessagePanel errorPanel = MessagePanel.createErrorPanel();
		errorPanel.setText(Messages.getMessage(error));
		addComponent(errorPanel);
	}

	private void addTable(final Table table) {
		final JTable jTable = new SwingUITable(uiFrame.getConfig(), table);
		new TableAdjuster(jTable).adjust();
		if (table.hasCommands()) {
			new RowSelector(jTable, table, uiFrame.getUpButton(),
					uiFrame.getDownButton(), uiFrame.getNextButton());
		}
		addComponent(jTable.getTableHeader());
		addComponent(jTable);
	}

	private void addTextField(final TextInput textInput) {
		final JTextField textField = new JTextField();
		textInput.addTextChangedListener(new TextChangedListener() {

			@Override
			public void textChanged() {
				textField.setText(textInput.getText());
				textField.setCaretPosition(textField.getDocument().getLength());
			}
		});
		addComponent(textField);
	}

	private void addText(final Label label) {
		final String text = Messages.getText(label);
		final JPanel panel = new JPanel();
		panel.add(new JLabel(text));
		panel.setBorder(BorderFactory.createLineBorder(new Color(0x7A, 0x8A,
				0x99), 1));
		addComponent(panel);
	}

	void setContents(final List<Object> contents) {
		removeAll();
		for (final Object content : contents) {
			if (content instanceof WarningLabel) {
				addWarning((Label) content);
			} else if (content instanceof Label) {
				addText((Label) content);
			} else if (content instanceof ErrorMessage) {
				addError((ErrorMessage) content);
			} else if (content instanceof Table) {
				addTable((Table) content);
			} else if (content instanceof TextInput) {
				addTextField((TextInput) content);
			}
		}
		revalidate();
		repaint();
	}

}
