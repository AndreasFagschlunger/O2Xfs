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