package at.o2xfs.operator.ui.events;

import at.o2xfs.operator.ui.UIComponent;

public interface MessageListener {

	void onClearMessage(UIComponent uiComponent);

	void onClearMessages();

	void onMessageChange();
}
