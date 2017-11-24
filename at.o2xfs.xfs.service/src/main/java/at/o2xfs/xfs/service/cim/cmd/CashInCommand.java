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
import at.o2xfs.xfs.cim.Reason;
import at.o2xfs.xfs.v3_00.cim.CashUnitError3;
import at.o2xfs.xfs.v3_00.cim.NoteNumberList3;
import at.o2xfs.xfs.v3_00.cim.P6Info3;
import at.o2xfs.xfs.v3_10.cim.ItemInfoSummary310;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cim.CimFactory;
import at.o2xfs.xfs.service.cim.CimService;
import at.o2xfs.xfs.service.cmd.AbstractAsyncXfsCommand;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.win32.XfsWord;

public final class CashInCommand extends AbstractAsyncXfsCommand<CashInListener, CashInCompleteEvent> {

	private static final Logger LOG = LoggerFactory.getLogger(CashInCommand.class);

	private final CimService cimService;

	public CashInCommand(CimService cimService) {
		this.cimService = cimService;
	}

	@Override
	protected XfsCommand createCommand() {
		return new XfsExecuteCommand<CimExecuteCommand>(cimService, CimExecuteCommand.CASH_IN);
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
			case EXEE_INPUTREFUSE:
				fireInputRefuse(new XfsWord<>(Reason.class, wfsResult.getResults()).get());
				break;
			case EXEE_NOTEERROR:
				fireNoteError(new XfsWord<>(Reason.class, wfsResult.getResults()).get());
				break;
			case EXEE_SUBCASHIN:
				fireSubCashIn(
						CimFactory.create(cimService.getXfsVersion(), wfsResult.getResults(), NoteNumberList3.class));
				break;
			case EXEE_INPUT_P6:
				fireInputP6(CimFactory.fromNullTerminatedArray(cimService.getXfsVersion(), wfsResult.getResults(),
						P6Info3.class));
				break;
			case EXEE_INFO_AVAILABLE:
				fireInfoAvailable(CimFactory.fromNullTerminatedArray(cimService.getXfsVersion(), wfsResult.getResults(),
						ItemInfoSummary310.class));
				break;
			case EXEE_INSERTITEMS:
				fireInsertItems();
				break;
			// case EXEE_INCOMPLETEREPLENISH:
			// fireIncompleteReplenish(CimFactory.create(cimService.getXfsVersion(),
			// wfsResult.getResults(),
			// IncompleteReplenish320.class));
			// break;
			// case EXEE_INCOMPLETEDEPLETE:
			// fireIncompleteDeplete(CimFactory.create(cimService.getXfsVersion(),
			// wfsResult.getResults(),
			// IncompleteDeplete330.class));
			// break;
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
		for (CashInListener each : listeners) {
			each.onCashUnitError(cashUnitError);
		}
	};

	private void fireInputRefuse(Reason reason) {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireInputRefuse(Reason)", "reason=" + reason);
		}
		for (CashInListener each : listeners) {
			each.onInputRefuse(reason);
		}
	};

	private void fireNoteError(Reason reason) {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireNoteError(Reason)", "reason=" + reason);
		}
		for (CashInListener each : listeners) {
			each.onNoteError(reason);
		}
	};

	private void fireSubCashIn(NoteNumberList3 noteNumberList) {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireSubCashIn(NoteNumberList3)", "noteNumberList=" + noteNumberList);
		}
		for (CashInListener each : listeners) {
			each.onSubCashIn(noteNumberList);
		}
	};

	private void fireInputP6(List<P6Info3> p6Infos) {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireInputP6(Pointer)", "p6Infos=" + p6Infos);
		}
		for (CashInListener each : listeners) {
			each.onInputP6(p6Infos);
		}
	};

	private void fireInfoAvailable(List<ItemInfoSummary310> itemInfoSummaries) {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireInfoAvailable(Pointer)", "itemInfoSummaries=" + itemInfoSummaries);
		}
		for (CashInListener each : listeners) {
			each.onInfoAvailable(itemInfoSummaries);
		}
	};

	private void fireInsertItems() {
		if (LOG.isInfoEnabled()) {
			LOG.info("fireInsertItems()", "");
		}
		for (CashInListener each : listeners) {
			each.onInsertItems();
		}
	};

	@Override
	protected CashInCompleteEvent createCompleteEvent(Pointer results) {
		Optional<NoteNumberList3> noteNumberList = Optional.empty();
		if (!Pointer.NULL.equals(results)) {
			noteNumberList = Optional
					.of(CimFactory.create(cimService.getSrvcVersion().getVersion(), results, NoteNumberList3.class));
		}
		if (LOG.isInfoEnabled()) {
			LOG.info("createCompleteEvent(Pointer)", noteNumberList);
		}
		return CashInCompleteEvent.build(noteNumberList);
	}
}
