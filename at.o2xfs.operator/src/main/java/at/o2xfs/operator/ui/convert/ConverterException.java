package at.o2xfs.operator.ui.convert;

import at.o2xfs.operator.ui.UIMessage;

public class ConverterException extends Exception {

	private final UIMessage message;

	public ConverterException(UIMessage message) {
		this(message, null);
	}

	public ConverterException(UIMessage message, Throwable cause) {
		super(cause);
		this.message = message;
	}

	public UIMessage getUIMessage() {
		return message;
	}
}