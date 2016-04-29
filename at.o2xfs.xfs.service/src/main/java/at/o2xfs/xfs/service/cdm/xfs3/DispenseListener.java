package at.o2xfs.xfs.service.cdm.xfs3;

import at.o2xfs.xfs.cdm.NoteErrorReason;
import at.o2xfs.xfs.cdm.v3_00.Denomination3;
import at.o2xfs.xfs.service.cmd.event.CommandListener;
import at.o2xfs.xfs.type.RequestId;

public interface DispenseListener extends CommandListener<DenominationEvent>, CashUnitErrorListener, InputP6Listener, InfoAvailableListener {

	void onDelayedDispense(long delay);

	void onStartDispense(RequestId requestId);

	void onPartialDispense(int dispNum);

	void onSubDispenseOk(Denomination3 denomination);

	void onIncompleteDispense(Denomination3 denomination);

	void onNoteError(NoteErrorReason reason);

}