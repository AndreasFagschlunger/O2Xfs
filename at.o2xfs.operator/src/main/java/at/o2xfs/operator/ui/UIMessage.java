package at.o2xfs.operator.ui;

import java.util.Arrays;
import java.util.Objects;

import at.o2xfs.operator.ui.content.text.Label;

public class UIMessage implements UIElement {

	public enum Severity {
		INFO, WARN, ERROR, FATAL;
	}

	private final Severity severity;
	private final Label messageId;
	private final Object[] arguments;

	public UIMessage(Label messageId) {
		this(Severity.INFO, messageId);
	}

	public UIMessage(Severity severity, Label messageId, Object... arguments) {
		Objects.requireNonNull(severity);
		Objects.requireNonNull(messageId);
		this.severity = severity;
		this.messageId = messageId;
		this.arguments = Arrays.copyOf(arguments, arguments.length);
	}

	public Severity getSeverity() {
		return severity;
	}

	public Label getMessageId() {
		return messageId;
	}

	public Object[] getArguments() {
		return Arrays.copyOf(arguments, arguments.length);
	}
}