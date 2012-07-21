/*
 * Copyright (c) 2012, Andreas Fagschlunger. All rights reserved.
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

package at.o2xfs.operator.task.xfs.idc;

import java.util.List;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.task.ExecuteTaskCommand;
import at.o2xfs.operator.task.Task;
import at.o2xfs.operator.task.TaskCommand;
import at.o2xfs.operator.task.xfs.CancelCommand;
import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.idc.IDCSecType;
import at.o2xfs.xfs.idc.IDCTrack;
import at.o2xfs.xfs.idc.WFSIDCCAPS;
import at.o2xfs.xfs.idc.WFSIDCCARDDATA;
import at.o2xfs.xfs.idc.WFSIDCSTATUS;
import at.o2xfs.xfs.service.cmd.idc.IDCCapabilitiesCommand;
import at.o2xfs.xfs.service.cmd.idc.IDCStatusCommand;
import at.o2xfs.xfs.service.cmd.idc.ReadCardCommand;
import at.o2xfs.xfs.service.cmd.idc.ReadCardListener;
import at.o2xfs.xfs.service.idc.IDCService;

public class IDCReadTask extends Task implements ReadCardListener {

	private final static Logger LOG = LoggerFactory
			.getLogger(IDCReadTask.class);

	private IDCService idcService = null;

	private ReadCardCommand command = null;

	/**
	 * @param taskManager
	 * @param previousTask
	 */
	public IDCReadTask(final IDCService idcService) {
		this.idcService = idcService;
	}

	private void createTable(final List<WFSIDCCARDDATA> cardDataList) {
		final Table table = new Table(getClass(), "DataSource", "Status",
				"Data");
		for (WFSIDCCARDDATA cardData : cardDataList) {
			table.addRow(createRow(cardData));
		}
		taskManager.setContent(table);
	}

	private Object[] createRow(final WFSIDCCARDDATA cardData) {
		final Object[] row = new Object[3];
		row[0] = cardData.getDataSource();
		row[1] = cardData.getStatus();
		row[2] = cardData.getData();
		return row;
	}

	@Override
	public void execute() throws Exception {
		final String method = "execute()";
		command = new ReadCardCommand(idcService);
		WFSIDCCAPS caps;
		try {
			caps = new IDCCapabilitiesCommand(idcService).execute();
		} catch (final Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error getting IDC capabilities", e);
			}
			throw e;
		}
		for (final IDCTrack track : caps.getReadTracks()) {
			command.addReadData(track);
		}
		if (!caps.getChipProtocols().isEmpty()) {
			command.addReadData(IDCTrack.WFS_IDC_CHIP);
		}
		if (!IDCSecType.WFS_IDC_SECNOTSUPP.equals(caps.getSecType())) {
			command.addReadData(IDCTrack.WFS_IDC_SECURITY);
		}
		command.addCommandListener(this);
		command.execute();
		taskManager.addTaskCommand(new CancelCommand(getClass(), command));
		showMessage("InsertCard");
	}

	@Override
	public void cardInserted() {
		showMessage("CardInserted");
	}

	@Override
	public void cardInvalid() {
		showMessage("CardInvalid");
	}

	@Override
	public void cardRead(final List<WFSIDCCARDDATA> cardDataList) {
		createTable(cardDataList);
	}

	private boolean isCardPresent() {
		final String method = "isCardPresent()";
		try {
			final WFSIDCSTATUS status = new IDCStatusCommand(idcService)
					.execute();
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "status=" + status);
			}
			switch (status.getMedia()) {
				case WFS_IDC_MEDIAJAMMED:
				case WFS_IDC_MEDIAPRESENT:
					return true;
				default:
					return false;
			}
		} catch (final XfsException e) {
			LOG.error(method, "Error retrieving status", e);
		} catch (final InterruptedException e) {
			// TODO: ?
		}
		return false;
	}

	private void finishTask() {
		command.removeCommandListener(this);
		taskManager.clearCommands();
		TaskCommand nextCommand = null;
		if (isCardPresent()) {
			final EjectCardTask ejectCardTask = new EjectCardTask(idcService);
			ejectCardTask.setParent(getParent());
			nextCommand = new ExecuteTaskCommand(ejectCardTask, taskManager);
		} else if (hasParent()) {
			nextCommand = new ExecuteTaskCommand(getParent(), taskManager);
		}
		if (nextCommand != null) {
			taskManager.setNextCommand(nextCommand);
		}
	}

	@Override
	public void commandSuccessful() {
		finishTask();

	}

	@Override
	public void commandCancelled() {
		showMessage("Cancelled");
		finishTask();
	}

	@Override
	public void commandFailed(final Exception e) {
		final String method = "commandFailed(Exception)";
		if (LOG.isErrorEnabled()) {
			LOG.error(method, "ReadCardCommand failed", e);
		}
		showError(e);
		finishTask();
	}

}
