package at.o2xfs.xfs.service.cmd.event;

public final class SucessEvent implements CompleteEvent {

	private SucessEvent() {
		super();
	}

	public static SucessEvent build() {
		return new SucessEvent();
	}
}
