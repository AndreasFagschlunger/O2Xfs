package at.o2xfs.xfs.service.cdm.xfs3;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.cdm.CdmExecuteCommand;
import at.o2xfs.xfs.cdm.CdmMessage;
import at.o2xfs.xfs.cdm.v3_00.CashUnitError3;
import at.o2xfs.xfs.cdm.v3_30.ItemInfoSummary3_30;
import at.o2xfs.xfs.service.ReflectiveFactory;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cdm.CdmService;
import at.o2xfs.xfs.service.cmd.AbstractAsyncXfsCommand;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.service.cmd.event.SucessEvent;

public class RejectCommand extends AbstractAsyncXfsCommand<RejectListener, SucessEvent> {

	private static final Logger LOG = LoggerFactory.getLogger(RejectCommand.class);

	private final CdmService service;

	public RejectCommand(CdmService service) {
		this.service = service;
	}

	@Override
	protected XfsCommand createCommand() {
		return new XfsExecuteCommand<>(service, CdmExecuteCommand.REJECT);
	}

	@Override
	public void fireIntermediateEvent(WFSResult wfsResult) {
		String method = "fireIntermediateEvent(WFSResult)";
		if (LOG.isDebugEnabled()) {
			LOG.debug(method, "wfsResult=" + wfsResult);
		}
		try {
			CdmMessage message = wfsResult.getEventID(CdmMessage.class);
			switch (message) {
				case EXEE_CASHUNITERROR:
					fireCashUnitError(ReflectiveFactory.create(service.getXfsVersion(), wfsResult.getResults(), CashUnitError3.class));
					break;
				case EXEE_INPUT_P6:
					fireInputP6();
					break;
				case EXEE_INFO_AVAILABLE:
					fireInfoAvailable(ReflectiveFactory.create(service.getXfsVersion(), wfsResult.getResults(), ItemInfoSummary3_30.class));
					break;
				default:
					throw new IllegalArgumentException("CdmMessage: " + message);
			}
		} finally {
			XfsServiceManager.getInstance().free(wfsResult);
		}
	}

	private void fireCashUnitError(CashUnitError3 cashUnitError) {
		String method = "fireCashUnitError(CashUnitError3)";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, cashUnitError);
		}
		for (RejectListener each : listeners) {
			each.onCashUnitError(cashUnitError);
		}
	}

	private void fireInputP6() {
		String method = "fireInputP6()";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, "");
		}
		for (RejectListener each : listeners) {
			each.onInputP6();
		}
	}

	private void fireInfoAvailable(ItemInfoSummary3_30 itemInfoSummary) {
		String method = "fireInfoAvailable(ItemInfoSummary3_30)";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, itemInfoSummary);
		}
		for (RejectListener each : listeners) {
			each.onInfoAvailable(itemInfoSummary);
		}
	}

	@Override
	protected SucessEvent createCompleteEvent(Pointer results) {
		return SucessEvent.build();
	}
}