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

package at.o2xfs.operator.task.xfs.cdm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.task.CloseTaskCommand;
import at.o2xfs.operator.task.TaskCommand;
import at.o2xfs.operator.task.xfs.XfsServiceTask;
import at.o2xfs.operator.task.xfs.cdm.DispenseWizard.Status;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.cdm.NoteErrorReason;
import at.o2xfs.xfs.v3_00.cdm.CashUnit3;
import at.o2xfs.xfs.v3_00.cdm.CashUnitError3;
import at.o2xfs.xfs.v3_00.cdm.CashUnitInfo3;
import at.o2xfs.xfs.v3_00.cdm.CdmCaps3;
import at.o2xfs.xfs.v3_00.cdm.CurrencyExp3;
import at.o2xfs.xfs.v3_00.cdm.Denomination3;
import at.o2xfs.xfs.v3_00.cdm.Dispense3;
import at.o2xfs.xfs.v3_30.cdm.ItemInfoSummary330;
import at.o2xfs.xfs.service.cdm.CdmService;
import at.o2xfs.xfs.service.cdm.xfs3.CashUnitInfoCommand;
import at.o2xfs.xfs.service.cdm.xfs3.DenominationEvent;
import at.o2xfs.xfs.service.cdm.xfs3.DispenseCommand;
import at.o2xfs.xfs.service.cdm.xfs3.DispenseListener;
import at.o2xfs.xfs.service.cmd.event.CancelEvent;
import at.o2xfs.xfs.service.cmd.event.ErrorEvent;
import at.o2xfs.xfs.type.RequestId;

public class DispenseTask extends XfsServiceTask<CdmService> implements DispenseListener {

	private static final Logger LOG = LoggerFactory.getLogger(DispenseTask.class);

	private class CancelCommand extends TaskCommand {

		public CancelCommand() {
			super(true);
		}

		@Override
		public void execute() {
			dispenseCommand.cancel();
			setEnabled(false);
		}
	}

	private CdmCaps3 capabilities;

	private CashUnit3[] cashUnits;

	private List<CashUnit3> sortedCashUnits;

	private long[] values = null;

	private DispenseCommand dispenseCommand = null;

	private boolean complete = false;
	private boolean cancel = false;
	private boolean error = false;

	@Override
	protected Class<CdmService> getServiceClass() {
		return CdmService.class;
	}

	@Override
	protected void execute() {
		String method = "execute()";
		try {
			capabilities = service.getCapabilities();
			CashUnitInfo3 cashUnitInfo = new CashUnitInfoCommand(service).call();
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "cashUnitInfo=" + cashUnitInfo);
			}
			cashUnits = cashUnitInfo.getList();
			values = new long[cashUnits.length];
			sortedCashUnits = new ArrayList<>();
			for (int i = 0; i < cashUnits.length; i++) {
				CashUnit3 each = cashUnits[i];
				switch (each.getType()) {
					case NA:
					case REJECTCASSETTE:
					case RETRACTCASSETTE:
						break;
					default:
						sortedCashUnits.add(each);
						break;
				}
			}
			Collections.sort(sortedCashUnits, new CashUnitComparator());
			if (sortedCashUnits.isEmpty()) {
				setCloseCommand();
			} else {
				DispenseWizard wizard = createWizard();
				if (Status.OK == wizard.open()) {
					for (DispenseWizardPage each : wizard.getPages()) {
						if (each.getValue() != null) {
							values[indexOf(each.getCashUnit())] = each.getValue().longValue();
						}
					}
					synchronized (this) {
						dispenseCommand = createDispenseCommand();
						try {
							dispenseCommand.addCommandListener(this);
							dispenseCommand.execute();
							getCommands().setBackCommand(new CancelCommand());
							while (!(complete || cancel || error)) {
								wait();
							}
						} finally {
							dispenseCommand.removeCommandListener(this);
						}
						if (complete && capabilities.isIntermediateStacker()) {
							taskManager.execute(new PresentTask(service));
						} else {
							getCommands().setBackCommand(new CloseTaskCommand(taskManager));
						}
					}
				} else {
					taskManager.closeTask();
				}
			}
		} catch (XfsException e) {
			LOG.error(method, "Error executing task", e);
			showException(e);
			setCloseCommand();
		} catch (InterruptedException e) {
			LOG.error(method, "Interrupted", e);
		}
	}

	private int indexOf(CashUnit3 cashUnit) {
		for (int i = 0; i < cashUnits.length; i++) {
			if (cashUnit == cashUnits[i]) {
				return i;
			}
		}
		throw new IllegalArgumentException("cashUnit=" + cashUnit + ",cashUnits=" + Arrays.toString(cashUnits));
	}

	private DispenseWizard createWizard() throws XfsException {
		DispenseWizard result = new DispenseWizard(getCommands(), getContent(), capabilities.getMaxDispenseItems());
		for (CashUnit3 cashUnit : sortedCashUnits) {
			CurrencyExp3 currencyExp = service.getCurrencyExponents().stream().filter(e -> Arrays.equals(e.getCurrencyID(), cashUnit.getCurrencyID())).findFirst().get();
			result.addPage(new DispenseWizardPage(cashUnit, currencyExp));
		}
		return result;
	}

	private DispenseCommand createDispenseCommand() {
		return new DispenseCommand(service,
				new Dispense3.Builder().present(!capabilities.isIntermediateStacker()).denomination(new Denomination3.Builder().values(values).build()).build());
	}

	@Override
	public void onInfoAvailable(ItemInfoSummary330 itemInfoSummary) {

	}

	@Override
	public void onInputP6() {

	}

	@Override
	public void onCashUnitError(CashUnitError3 cashUnitError) {

	}

	@Override
	public void onDelayedDispense(long delay) {

	}

	@Override
	public void onStartDispense(RequestId requestId) {

	}

	@Override
	public void onPartialDispense(int dispNum) {

	}

	@Override
	public void onSubDispenseOk(Denomination3 denomination) {

	}

	@Override
	public void onIncompleteDispense(Denomination3 denomination) {

	}

	@Override
	public void onNoteError(NoteErrorReason reason) {

	}

	@Override
	public void onCancel(CancelEvent event) {
		synchronized (this) {
			cancel = true;
			notifyAll();
		}
	}

	@Override
	public void onError(ErrorEvent event) {
		showException(event.getException());
		synchronized (this) {
			error = true;
			notifyAll();
		}
	}

	@Override
	public void onComplete(DenominationEvent event) {
		synchronized (this) {
			complete = true;
			notifyAll();
		}
	}
}
