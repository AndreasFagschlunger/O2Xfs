package at.o2xfs.xfs.service.cmd.event;

public class ErrorEvent {

	private final Exception exception;

	public ErrorEvent(Exception exception) {
		this.exception = exception;
	}

	public Exception getException() {
		return exception;
	}
}
