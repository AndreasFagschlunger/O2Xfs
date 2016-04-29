package at.o2xfs.xfs.service.cdm.xfs3;

import at.o2xfs.xfs.cdm.v3_30.IncompleteRetract3_30;
import at.o2xfs.xfs.service.cmd.event.CommandListener;
import at.o2xfs.xfs.service.cmd.event.SucessEvent;

public interface ResetListener extends CommandListener<SucessEvent>, CashUnitErrorListener, InputP6Listener, InfoAvailableListener {

	void onIncompleteRetract(IncompleteRetract3_30 incompleteRetract);
}
