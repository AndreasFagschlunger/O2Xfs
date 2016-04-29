package at.o2xfs.operator.task.xfs.cdm;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.task.xfs.XfsServiceTask;
import at.o2xfs.xfs.cdm.v3_00.CashUnitError3;
import at.o2xfs.xfs.cdm.v3_30.IncompleteRetract3_30;
import at.o2xfs.xfs.cdm.v3_30.ItemInfoSummary3_30;
import at.o2xfs.xfs.service.cdm.CdmService;
import at.o2xfs.xfs.service.cdm.xfs3.ResetCommand;
import at.o2xfs.xfs.service.cdm.xfs3.ResetListener;
import at.o2xfs.xfs.service.cmd.event.CancelEvent;
import at.o2xfs.xfs.service.cmd.event.ErrorEvent;
import at.o2xfs.xfs.service.cmd.event.SucessEvent;

public class ResetTask extends XfsServiceTask<CdmService> implements ResetListener {

	private static final Logger LOG = LoggerFactory.getLogger(ResetTask.class);

	private enum State {
		START, CANCEL, ERROR, SUCCESS;
	}

	private ResetCommand resetCommand;

	private State state;

	@Override
	protected Class<CdmService> getServiceClass() {
		return CdmService.class;
	}

	@Override
	protected void execute() {
		String method = "execute()";
		try {
			synchronized (this) {
				state = State.START;
				resetCommand = new ResetCommand(service);
				resetCommand.addCommandListener(this);
				resetCommand.execute();
				while (State.START == state) {
					wait();
				}
				setCloseCommand();
			}
		} catch (InterruptedException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Interrupted", e);
			}
		}
	}

	private void updateState(State state) {
		synchronized (this) {
			this.state = state;
			notifyAll();
		}
	}

	@Override
	public void onCashUnitError(CashUnitError3 cashUnitError) {
	}

	@Override
	public void onInputP6() {
	}

	@Override
	public void onInfoAvailable(ItemInfoSummary3_30 itemInfoSummary) {
	}

	@Override
	public void onIncompleteRetract(IncompleteRetract3_30 incompleteRetract) {
	}

	@Override
	public void onCancel(CancelEvent event) {
		updateState(State.CANCEL);
	}

	@Override
	public void onError(ErrorEvent event) {
		showException(event.getException());
		updateState(State.ERROR);
	}

	@Override
	public void onComplete(SucessEvent event) {
		updateState(State.SUCCESS);
	}
}