package at.o2xfs.xfs.service.cmd.event;

public interface CommandListener<T extends CompleteEvent> {

	public void onCancel(CancelEvent event);

	public void onError(ErrorEvent event);

	public void onComplete(T event);
}
