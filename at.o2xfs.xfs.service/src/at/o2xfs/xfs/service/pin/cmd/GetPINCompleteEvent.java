package at.o2xfs.xfs.service.pin.cmd;

import at.o2xfs.xfs.pin.WfsPINEntry;
import at.o2xfs.xfs.service.cmd.event.CompleteEvent;

public class GetPINCompleteEvent implements CompleteEvent {

	private final WfsPINEntry pinEntry;

	private GetPINCompleteEvent(final WfsPINEntry pinEntry) {
		this.pinEntry = pinEntry;
	}

	public WfsPINEntry getPINEntry() {
		return new WfsPINEntry(pinEntry);
	}

	public static final GetPINCompleteEvent create(WfsPINEntry pinEntry) {
		return new GetPINCompleteEvent(pinEntry);
	}
}
