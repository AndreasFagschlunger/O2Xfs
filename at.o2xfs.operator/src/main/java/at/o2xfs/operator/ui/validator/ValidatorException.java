package at.o2xfs.operator.ui.validator;

import at.o2xfs.operator.ui.UIMessage;

public class ValidatorException extends Exception {

	private final UIMessage uiMessage;

	public ValidatorException(UIMessage uiMessage) {
		this.uiMessage = uiMessage;
	}

	public UIMessage getUIMessage() {
		return uiMessage;
	}
}