package at.o2xfs.operator.ui.swing.renderkit;

import java.awt.Container;
import java.util.Collection;

import at.o2xfs.operator.ui.UIMessage;
import at.o2xfs.operator.ui.swing.ContentPanel;
import at.o2xfs.operator.ui.swing.MessagePanel;
import at.o2xfs.operator.ui.swing.i18n.Messages;

public class MessageRenderer {

	private final Collection<UIMessage> messages;

	public MessageRenderer(Collection<UIMessage> messages) {
		this.messages = messages;
	}

	public void render(Container container) {
		for (UIMessage each : messages) {
			MessagePanel panel = MessagePanel.createErrorPanel();
			panel.setText(Messages.getText(each.getMessageId()));
			((ContentPanel) container).addComponent(panel);
		}
	}
}