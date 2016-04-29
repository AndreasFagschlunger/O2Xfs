package at.o2xfs.xfs.service.cdm.xfs3;

import at.o2xfs.xfs.cdm.v3_00.Denomination3;
import at.o2xfs.xfs.service.cmd.event.CompleteEvent;

public final class DenominationEvent implements CompleteEvent {

	private final Denomination3 denomination;

	private DenominationEvent(Denomination3 denomination) {
		this.denomination = denomination;
	}

	public Denomination3 getDenomination() {
		return denomination;
	}

	public static DenominationEvent build(Denomination3 denomination) {
		return new DenominationEvent(denomination);
	}
}
