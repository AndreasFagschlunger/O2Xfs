package at.o2xfs.xfs.service.cdm.xfs3;

import at.o2xfs.xfs.service.cmd.event.CommandListener;
import at.o2xfs.xfs.service.cmd.event.SucessEvent;

public interface PresentListener extends CommandListener<SucessEvent>, InputP6Listener, InfoAvailableListener {

}
