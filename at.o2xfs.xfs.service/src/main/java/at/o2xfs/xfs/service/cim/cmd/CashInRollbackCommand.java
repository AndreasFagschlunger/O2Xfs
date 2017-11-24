/*
 * Copyright (c) 2017, Andreas Fagschlunger. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.xfs.service.cim.cmd;

import java.util.List;
import java.util.Optional;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.Pointer;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.cim.CimExecuteCommand;
import at.o2xfs.xfs.cim.CimMessage;
import at.o2xfs.xfs.v3_00.cim.CashInfo3;
import at.o2xfs.xfs.v3_00.cim.CashUnitError3;
import at.o2xfs.xfs.v3_00.cim.P6Info3;
import at.o2xfs.xfs.v3_10.cim.ItemInfoSummary310;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cim.CimFactory;
import at.o2xfs.xfs.service.cim.CimService;
import at.o2xfs.xfs.service.cmd.AbstractAsyncXfsCommand;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;

public final class CashInRollbackCommand
		extends AbstractAsyncXfsCommand<CashInRollbackListener, CashInRollbackCompleteEvent> {

	private static final Logger LOG = LoggerFactory.getLogger(CashInRollbackCommand.class);

	private final CimService cimService;

	public CashInRollbackCommand(CimService cimService) {
		this.cimService = cimService;
	}

	@Override
	protected XfsCommand createCommand() {
		return new XfsExecuteCommand<CimExecuteCommand>(cimService, CimExecuteCommand.CASH_IN_ROLLBACK);
	}

	@Override
	public void fireIntermediateEvent(WFSResult wfsResult) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("fireIntermediateEvent(WFSResult)", "wfsResult=" + wfsResult);
		}
		try {
			CimMessage message = wfsResult.getEventID(CimMessage.class);
			switch (message) {
			case EXEE_CASHUNITERROR:
				fireCashUnitError(
						CimFactory.create(cimService.getXfsVersion(), wfsResult.getResults(), CashUnitError3.class));
				break;
			case EXEE_INPUT_P6:
				fireInputP6(CimFactory.fromNullTerminatedArray(cimService.getXfsVersion(), wfsResult.getResults(),
						P6Info3.class));
				break;
			case EXEE_INFO_AVAILABLE:
				fireInfoAvailable(CimFactory.fromNullTerminatedArray(cimService.getXfsVersion(), wfsResult.getResults(),
						ItemInfoSummary310.class));
				break;
			default:
				throw new IllegalArgumentException(message.toString());
			}
		} finally {
			XfsServiceManager.getInstance().free(wfsResult);
		}
	}

	private void fireCashUnitError(CashUnitError3 cashUnitError) {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireCashUnitError(CashUnitError3)", "cashUnitError=" + cashUnitError);
		}
		for (CashInRollbackListener each : listeners) {
			each.onCashUnitError(cashUnitError);
		}
	};

	private void fireInputP6(List<P6Info3> p6Infos) {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireInputP6(Pointer)", "p6Infos=" + p6Infos);
		}
		for (CashInRollbackListener each : listeners) {
			each.onInputP6(p6Infos);
		}
	};

	private void fireInfoAvailable(List<ItemInfoSummary310> itemInfoSummaries) {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireInfoAvailable(Pointer)", "itemInfoSummaries=" + itemInfoSummaries);
		}
		for (CashInRollbackListener each : listeners) {
			each.onInfoAvailable(itemInfoSummaries);
		}
	};

	@Override
	protected CashInRollbackCompleteEvent createCompleteEvent(Pointer results) {
		Optional<CashInfo3> cashInfo = Optional.empty();
		if (!Pointer.NULL.equals(results)) {
			cashInfo = Optional.of(CimFactory.create(cimService.getXfsVersion(), results, CashInfo3.class));
		}
		if (LOG.isInfoEnabled()) {
			LOG.info("createCompleteEvent(Pointer)", cashInfo);
		}
		return CashInRollbackCompleteEvent.build(cashInfo);
	}
}
