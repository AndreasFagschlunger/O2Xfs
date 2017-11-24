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

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.task.xfs.XfsServiceTask;
import at.o2xfs.xfs.v3_00.cdm.CashUnitError3;
import at.o2xfs.xfs.v3_30.cdm.IncompleteRetract330;
import at.o2xfs.xfs.v3_30.cdm.ItemInfoSummary330;
import at.o2xfs.xfs.service.cdm.CdmService;
import at.o2xfs.xfs.service.cdm.xfs3.ResetCommand;
import at.o2xfs.xfs.service.cdm.xfs3.ResetListener;
import at.o2xfs.xfs.service.cmd.event.CancelEvent;
import at.o2xfs.xfs.service.cmd.event.ErrorEvent;
import at.o2xfs.xfs.service.cmd.event.SuccessEvent;

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
	public void onInfoAvailable(ItemInfoSummary330 itemInfoSummary) {
	}

	@Override
	public void onIncompleteRetract(IncompleteRetract330 incompleteRetract) {
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
	public void onComplete(SuccessEvent event) {
		updateState(State.SUCCESS);
	}
}
