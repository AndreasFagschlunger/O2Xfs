package at.o2xfs.xfs.service.cdm.xfs3;

import at.o2xfs.xfs.service.cmd.event.CommandListener;
import at.o2xfs.xfs.service.cmd.event.SucessEvent;

public interface RejectListener extends CommandListener<SucessEvent>, CashUnitErrorListener, InputP6Listener, InfoAvailableListener {

}
