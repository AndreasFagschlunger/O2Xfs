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

package at.o2xfs.xfs.service.cdm.xfs3;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.win32.Pointer;
import at.o2xfs.win32.ULONG;
import at.o2xfs.win32.USHORT;
import at.o2xfs.xfs.WFSResult;
import at.o2xfs.xfs.cdm.CdmExecuteCommand;
import at.o2xfs.xfs.cdm.CdmMessage;
import at.o2xfs.xfs.cdm.NoteErrorReason;
import at.o2xfs.xfs.v3_00.cdm.CashUnitError3;
import at.o2xfs.xfs.v3_00.cdm.Denomination3;
import at.o2xfs.xfs.v3_00.cdm.Dispense3;
import at.o2xfs.xfs.v3_30.cdm.ItemInfoSummary330;
import at.o2xfs.xfs.service.ReflectiveFactory;
import at.o2xfs.xfs.service.XfsServiceManager;
import at.o2xfs.xfs.service.cdm.CdmService;
import at.o2xfs.xfs.service.cmd.AbstractAsyncXfsCommand;
import at.o2xfs.xfs.service.cmd.XfsCommand;
import at.o2xfs.xfs.service.cmd.XfsExecuteCommand;
import at.o2xfs.xfs.type.RequestId;
import at.o2xfs.xfs.win32.XfsWord;

public class DispenseCommand extends AbstractAsyncXfsCommand<DispenseListener, DenominationEvent> {

	private static final Logger LOG = LoggerFactory.getLogger(DispenseCommand.class);

	private final CdmService service;
	private final Dispense3 dispense;

	public DispenseCommand(CdmService service, Dispense3 dispense) {
		this.service = service;
		this.dispense = dispense;
	}

	@Override
	protected XfsCommand createCommand() {
		return new XfsExecuteCommand<CdmExecuteCommand>(service, CdmExecuteCommand.DISPENSE, dispense);
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
				case EXEE_DELAYEDDISPENSE:
					fireDelayedDispense(new ULONG(wfsResult.getResults()).longValue());
					break;
				case EXEE_STARTDISPENSE:
					fireStartDispense(new RequestId(wfsResult.getResults()));
					break;
				case EXEE_CASHUNITERROR:
					fireCashUnitError(ReflectiveFactory.create(service.getXfsVersion(), wfsResult.getResults(), CashUnitError3.class));
					break;
				case EXEE_PARTIALDISPENSE:
					firePartialDispense(new USHORT(wfsResult.getResults()).intValue());
					break;
				case EXEE_SUBDISPENSEOK:
					fireSubDispenseOk(ReflectiveFactory.create(service.getXfsVersion(), wfsResult.getResults(), Denomination3.class));
					break;
				case EXEE_INCOMPLETEDISPENSE:
					fireIncompleteDispense(ReflectiveFactory.create(service.getXfsVersion(), wfsResult.getResults(), Denomination3.class));
					break;
				case EXEE_NOTEERROR:
					fireNoteError(new XfsWord<>(NoteErrorReason.class, wfsResult.getResults()).get());
					break;
				case EXEE_INPUT_P6:
					fireInputP6();
					break;
				case EXEE_INFO_AVAILABLE:
					fireInfoAvailable(ReflectiveFactory.create(service.getXfsVersion(), wfsResult.getResults(), ItemInfoSummary330.class));
					break;
				default:
					throw new IllegalArgumentException("CdmMessage: " + message);
			}
		} finally {
			XfsServiceManager.getInstance().free(wfsResult);
		}
	}

	private void fireDelayedDispense(long delay) {
		String method = "fireDelayedDispense(long)";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, delay);
		}
		for (DispenseListener each : listeners) {
			each.onDelayedDispense(delay);
		}
	}

	private void fireStartDispense(RequestId requestId) {
		String method = "fireStartDispense(RequestId)";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, requestId);
		}
		for (DispenseListener each : listeners) {
			each.onStartDispense(requestId);
		}
	}

	private void fireCashUnitError(CashUnitError3 cashUnitError) {
		String method = "fireCashUnitError(CashUnitError3)";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, cashUnitError);
		}
		for (DispenseListener each : listeners) {
			each.onCashUnitError(cashUnitError);
		}
	}

	private void firePartialDispense(int dispNum) {
		String method = "firePartialDispense(int)";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, dispNum);
		}
		for (DispenseListener each : listeners) {
			each.onPartialDispense(dispNum);
		}
	}

	private void fireSubDispenseOk(Denomination3 denomination) {
		String method = "fireSubDispenseOk(Denomination3)";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, denomination);
		}
		for (DispenseListener each : listeners) {
			each.onSubDispenseOk(denomination);
		}
	}

	private void fireIncompleteDispense(Denomination3 denomination) {
		String method = "fireIncompleteDispense(Denomination3)";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, denomination);
		}
		for (DispenseListener each : listeners) {
			each.onIncompleteDispense(denomination);
		}
	}

	private void fireNoteError(NoteErrorReason reason) {
		String method = "fireNoteError(NoteErrorReason)";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, reason);
		}
		for (DispenseListener each : listeners) {
			each.onNoteError(reason);
		}
	}

	private void fireInputP6() {
		String method = "fireInputP6()";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, "");
		}
		for (DispenseListener each : listeners) {
			each.onInputP6();
		}
	}

	private void fireInfoAvailable(ItemInfoSummary330 itemInfoSummary) {
		String method = "fireInfoAvailable(ItemInfoSummary330)";
		if (LOG.isInfoEnabled()) {
			LOG.info(method, itemInfoSummary);
		}
		for (DispenseListener each : listeners) {
			each.onInfoAvailable(itemInfoSummary);
		}
	}

	@Override
	protected DenominationEvent createCompleteEvent(Pointer results) {
		return DenominationEvent.build(ReflectiveFactory.create(service.getXfsVersion(), results, Denomination3.class));
	}
}
