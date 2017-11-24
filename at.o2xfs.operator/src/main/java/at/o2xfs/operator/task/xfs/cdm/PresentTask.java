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

import java.util.Set;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.task.CloseTaskCommand;
import at.o2xfs.operator.task.TaskCommand;
import at.o2xfs.operator.task.xfs.XfsServiceTask;
import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.operator.ui.content.text.Label;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.cdm.Position;
import at.o2xfs.xfs.v3_00.cdm.CdmCaps3;
import at.o2xfs.xfs.v3_00.cdm.CdmStatus3;
import at.o2xfs.xfs.v3_30.cdm.ItemInfoSummary330;
import at.o2xfs.xfs.service.cdm.CdmService;
import at.o2xfs.xfs.service.cdm.CdmServiceAdapter;
import at.o2xfs.xfs.service.cdm.CdmServiceListener;
import at.o2xfs.xfs.service.cdm.xfs3.CdmStatusCommand;
import at.o2xfs.xfs.service.cdm.xfs3.PresentCommand;
import at.o2xfs.xfs.service.cdm.xfs3.PresentListener;
import at.o2xfs.xfs.service.cmd.event.CancelEvent;
import at.o2xfs.xfs.service.cmd.event.ErrorEvent;
import at.o2xfs.xfs.service.cmd.event.SuccessEvent;

public class PresentTask extends XfsServiceTask<CdmService> implements PresentListener {

	private static final Logger LOG = LoggerFactory.getLogger(PresentTask.class);

	private class ExecutePresent extends TaskCommand {

		private final Position outputPosition;

		public ExecutePresent(Position outputPosition) {
			this.outputPosition = outputPosition;
		}

		@Override
		public Label getLabel() {
			return Label.valueOf("Present");
		}

		@Override
		public void execute() {
			createPresentCommand(outputPosition);
		}
	}

	private class ItemsTakenListener extends CdmServiceAdapter {

		@Override
		public void onItemsTaken(Position position) {
			itemsTaken();
		}
	}

	private final CdmServiceListener serviceListener;

	private CdmCaps3 capabilities;

	private CdmStatus3 status;

	private PresentCommand presentCommand = null;

	private boolean finished = false;

	public PresentTask(CdmService service) {
		super(service);
		serviceListener = new ItemsTakenListener();
	}

	@Override
	protected Class<CdmService> getServiceClass() {
		return CdmService.class;
	}

	@Override
	protected void execute() {
		final String method = "execute()";
		try {
			capabilities = service.getCapabilities();
			status = new CdmStatusCommand(service).call();
			presentCommand = null;
			finished = false;
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "capabilities=" + capabilities);
			}
			if (LOG.isInfoEnabled()) {
				LOG.info(method, "CdmStatus3: " + status);
			}
			synchronized (this) {
				showPresentCommands();
				while (presentCommand == null) {
					wait();
				}
				getCommands().clear();
				try {
					showMessage("Please wait...");
					service.addCdmServiceListener(serviceListener);
					presentCommand.addCommandListener(this);
					presentCommand.execute();
					showCancelCommand();
					while (!finished) {
						wait();
					}
				} finally {
					service.removeCdmServiceListener(serviceListener);
				}
			}
		} catch (InterruptedException e) {
			LOG.error(method, "Interrupted", e);
		} catch (XfsException e) {
			showException(e);
			setCloseCommand();
		}
	}

	private void showPresentCommands() {
		Set<Position> positions = capabilities.getPositions();
		if (positions.size() == 1) {
			getCommands().setNextCommand(new ExecutePresent(positions.iterator().next()));
		} else {
			Table table = new Table(getClass(), "Output positions");
			for (Position each : positions) {
				table.addRowWithCommand(new ExecutePresent(each), each);
			}
			getContent().set(table);
		}
	}

	private void createPresentCommand(Position outputPosition) {
		synchronized (this) {
			presentCommand = new PresentCommand(service, outputPosition);
			notifyAll();
		}
	}

	private void showCancelCommand() {
		getCommands().setBackCommand(new TaskCommand() {

			@Override
			public Label getLabel() {
				return new Label(PresentTask.class, "Cancel");
			}

			@Override
			public void execute() {
				presentCommand.cancel();
				setEnabled(false);
			}
		});
		getCommands().getBackCommand().setEnabled(true);
	}

	private void itemsTaken() {
		synchronized (this) {
			finished = true;
			notifyAll();
		}
		taskManager.closeTask();
	}

	@Override
	public void onInfoAvailable(ItemInfoSummary330 itemInfoSummary) {

	}

	@Override
	public void onInputP6() {

	}

	@Override
	public void onCancel(CancelEvent event) {
		showMessage("Cancelled.");
		finish();
	}

	@Override
	public void onError(ErrorEvent event) {
		showException(event.getException());
		finish();
	}

	@Override
	public void onComplete(SuccessEvent event) {
		showMessage("Take items.");
		getCommands().clear();
	}

	private void finish() {
		getCommands().clear();
		getCommands().setNextCommand(new CloseTaskCommand(taskManager));
		synchronized (this) {
			finished = true;
			notifyAll();
		}
	}
}
