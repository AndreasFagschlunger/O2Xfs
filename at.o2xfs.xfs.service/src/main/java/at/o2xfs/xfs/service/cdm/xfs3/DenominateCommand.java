package at.o2xfs.xfs.service.cdm.xfs3;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.cdm.CdmExecuteCommand;
import at.o2xfs.xfs.cdm.CdmMessage;
import at.o2xfs.xfs.cdm.v3_00.CashUnitError3;
import at.o2xfs.xfs.cdm.v3_00.Denominate3;
import at.o2xfs.xfs.cdm.v3_00.Denomination3;
import at.o2xfs.xfs.service.ReflectiveFactory;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cdm.CdmService;
import at.o2xfs.xfs.service.cmd.AbstractAsyncXfsCommand;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;

public class DenominateCommand extends AbstractAsyncXfsCommand<DenominateListener, DenominationEvent> {

	private static final Logger LOG = LoggerFactory.getLogger(DenominateCommand.class);

	private final CdmService service;

	private final Denominate3 denominate;

	public DenominateCommand(CdmService service, Denominate3 denominate) {
		this.service = service;
		this.denominate = new Denominate3(denominate);
	}

	@Override
	protected XfsCommand createCommand() {
		return new XfsExecuteCommand<CdmExecuteCommand>(service, CdmExecuteCommand.DENOMINATE, denominate);
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
		for (DenominateListener each : listeners) {
			each.onCashUnitError(cashUnitError);
		}
	}

	@Override
	protected DenominationEvent createCompleteEvent(Pointer results) {
		return DenominationEvent.build(ReflectiveFactory.create(service.getXfsVersion(), results, Denomination3.class));
	}
}