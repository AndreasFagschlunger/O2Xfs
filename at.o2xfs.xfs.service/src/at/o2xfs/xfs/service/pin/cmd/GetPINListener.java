package at.o2xfs.xfs.service.pin.cmd;

import at.o2xfs.xfs.pin.WfsPINKey;
import at.o2xfs.xfs.service.cmd.event.CommandListener;

public interface GetPINListener extends CommandListener<GetPINCompleteEvent> {

	public void onKeyPress(WfsPINKey pinKey);

	public void onEnterData();
}
