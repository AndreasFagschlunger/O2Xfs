package at.o2xfs.xfs.service.siu.cmd;

import at.o2xfs.xfs.service.cmd.event.CommandListener;
import at.o2xfs.xfs.service.cmd.event.SuccessEvent;
import at.o2xfs.xfs.siu.SIUPortError;

public interface SetGuidLightListener extends CommandListener<SuccessEvent> {

	void onPortError(SIUPortError portError);
}
