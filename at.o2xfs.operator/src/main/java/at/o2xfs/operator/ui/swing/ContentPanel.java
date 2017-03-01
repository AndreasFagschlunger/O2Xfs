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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.ui.UIComponent;
import at.o2xfs.operator.ui.UIContent;
import at.o2xfs.operator.ui.UIElement;
import at.o2xfs.operator.ui.UIMessage;
import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.operator.ui.content.text.ExceptionMessage;
import at.o2xfs.operator.ui.content.text.InputText;
import at.o2xfs.operator.ui.content.text.Label;
import at.o2xfs.operator.ui.content.text.Text;
import at.o2xfs.operator.ui.content.text.TextChangedListener;
import at.o2xfs.operator.ui.content.text.TextInput;
import at.o2xfs.operator.ui.events.MessageListener;
import at.o2xfs.operator.ui.swing.i18n.Messages;
import at.o2xfs.operator.ui.swing.renderkit.InputTextRenderer;
import at.o2xfs.operator.ui.swing.table.RowSelector;
import at.o2xfs.operator.ui.swing.table.SwingUITable;
import at.o2xfs.operator.ui.swing.table.TableAdjuster;

public class ContentPanel extends JPanel implements MessageListener {

	private static final Logger LOG = LoggerFactory.getLogger(ContentPanel.class);

	private final UIFrame uiFrame;

	private final Map<UIComponent, InputTextRenderer> renderers;

	private final MessagePanel messagePanel = MessagePanel.createErrorPanel();

	private UIContent uiContent;

	public ContentPanel(final UIFrame uiFrame) {
		super();
		this.uiFrame = uiFrame;
		renderers = new HashMap<>();
		setLayout(new GridBagLayout());
		messagePanel.setVisible(false);
	}

	public void addComponent(final Component component) {
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
		} else {
			final Dimension d = component.getPreferredSize();
			component.setPreferredSize(new Dimension(getPreferredSize().width, d.height));
		}
		add(component, gbc);
	}

	private void addInfo(final Label label) {
		final MessagePanel messagePanel = MessagePanel.createInformationPanel();
		messagePanel.setText(Messages.getText(label));
		addComponent(messagePanel);
	}

	private void addWarning(final Label label) {
		final MessagePanel warningPanel = MessagePanel.createWarningPanel();
		warningPanel.setText(Messages.getText(label));
		addComponent(warningPanel);
	}

	private void addError(final Label label) {
		final MessagePanel errorPanel = MessagePanel.createErrorPanel();
		errorPanel.setText(Messages.getText(label));
		addComponent(errorPanel);
	}

	private void addError(final ExceptionMessage error) {
		final MessagePanel errorPanel = MessagePanel.createErrorPanel();
		errorPanel.setText(Messages.getMessage(error));
		addComponent(errorPanel);
	}

	private void addTable(final Table table) {
		final JTable jTable = new SwingUITable(uiFrame.getConfig(), table);
		new TableAdjuster(jTable).adjust();
		new RowSelector(jTable, table, uiFrame.getUpButton(), uiFrame.getDownButton(), uiFrame.getNextButton());
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

	private void setTitle(Text title) {
		StringBuilder text = new StringBuilder();
		for (Label label : title.getText()) {
			text.append(Messages.getText(label));
		}
		JLabel titleLabel = new JLabel(SwingUtil.textToHTML(text.toString()));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		addComponent(titleLabel);
	}

	private void addText(final Label label) {
		final String text = Messages.getText(label);
		final JPanel panel = new JPanel(new BorderLayout());
		final JLabel jLabel = new JLabel(SwingUtil.textToHTML(text));
		jLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(jLabel, BorderLayout.CENTER);
		panel.setBorder(BorderFactory.createLineBorder(new Color(0x7A, 0x8A, 0x99), 1));
		addComponent(panel);
	}

	void setContent(final UIContent content) {
		renderers.clear();
		if (uiContent != null) {
			uiContent.removeMessageListener(this);
		}
		uiContent = content;
		removeAll();
		uiContent.addMessageListener(this);
		setTitle(content.getTitle());
		messagePanel.setVisible(false);
		addComponent(messagePanel);
		for (final UIElement element : content.getUIElements()) {
			if (element instanceof Label) {
				addText((Label) element);
			} else if (element instanceof UIMessage) {
				UIMessage uiMessage = (UIMessage) element;
				switch (uiMessage.getSeverity()) {
					case INFO:
						addInfo(uiMessage.getMessageId());
						break;
					case WARN:
						addWarning(uiMessage.getMessageId());
						break;
					default:
						addError(uiMessage.getMessageId());
						break;
				}
			} else if (element instanceof ExceptionMessage) {
				addError((ExceptionMessage) element);
			} else if (element instanceof Table) {
				addTable((Table) element);
			} else if (element instanceof TextInput) {
				addTextField((TextInput) element);
			} else if (element instanceof InputText) {
				InputTextRenderer renderer = new InputTextRenderer((InputText) element);
				renderers.put((InputText) element, renderer);
				renderer.render(this);
			}
		}
		revalidate();
		repaint();
	}

	@Override
	public void onClearMessage(UIComponent uiComponent) {
		InputTextRenderer renderer = renderers.get(uiComponent);
		if (renderer != null) {
			renderer.hideMessage();
		}
	}

	@Override
	public void onClearMessages() {
		messagePanel.setVisible(false);
	}

	@Override
	public void onMessageChange() {
		Map<UIComponent, List<UIMessage>> uiMessages = uiContent.getUIMessage();
		for (Map.Entry<UIComponent, List<UIMessage>> entry : uiMessages.entrySet()) {
			if (renderers.containsKey(entry.getKey())) {
				InputTextRenderer renderer = renderers.get(entry.getKey());
				renderer.showMessage(entry.getValue().get(0));
			} else {
				messagePanel.setText(Messages.getText(entry.getValue().get(0).getMessageId()));
				messagePanel.setVisible(true);
			}
			uiMessages.remove(entry.getKey());
		}
	}
}