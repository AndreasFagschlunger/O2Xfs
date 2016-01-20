/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.operator.task.xfs.idc;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.task.ExecuteTaskCommand;
import at.o2xfs.operator.task.xfs.CancelCommand;
import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.idc.IDCSecType;
import at.o2xfs.xfs.idc.IDCTrack;
import at.o2xfs.xfs.idc.WFSIDCCAPS;
import at.o2xfs.xfs.idc.WFSIDCCARDDATA;
import at.o2xfs.xfs.idc.WfsIDCStatus;
import at.o2xfs.xfs.service.idc.IDCService;
import at.o2xfs.xfs.service.idc.cmd.IDCCapabilitiesCommand;
import at.o2xfs.xfs.service.idc.cmd.IDCStatusCommand;
import at.o2xfs.xfs.service.idc.cmd.ReadCardCommand;
import at.o2xfs.xfs.service.idc.cmd.ReadCardListener;

import java.util.List;

public class IDCReadTask
		extends IDCTask
		implements ReadCardListener {

	private final static Logger LOG = LoggerFactory.getLogger(IDCReadTask.class);

	private IDCService service = null;

	private ReadCardCommand command = null;

	private void createTable(final List<WFSIDCCARDDATA> cardDataList) {
		final Table table = new Table(getClass(), "DataSource", "Status", "Data");
		for (WFSIDCCARDDATA cardData : cardDataList) {
			table.addRow(createRow(cardData));
		}
		getContent().setUIElement(table);
	}

	private Object[] createRow(final WFSIDCCARDDATA cardData) {
		final Object[] row = new Object[3];
		row[0] = cardData.getDataSource();
		row[1] = cardData.getStatus();
		row[2] = cardData.getData();
		return row;
	}

	@Override
	protected boolean setCloseCommandPerDefault() {
		return false;
	}

	@Override
	protected void doExecute(IDCService service) {
		final String method = "doExecute(IDCService)";
		this.service = service;
		command = new ReadCardCommand(service);
		WFSIDCCAPS caps;
		try {
			caps = new IDCCapabilitiesCommand(service).call();
		} catch (final XfsException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error getting IDC capabilities", e);
			}
			showException(e);
			setCloseCommand();
			return;
		}
		for (final IDCTrack track : caps.getReadTracks()) {
			command.addReadData(track);
		}
		if (!caps.getChipProtocols().isEmpty()) {
			command.addReadData(IDCTrack.CHIP);
		}
		if (!IDCSecType.NOTSUPP.equals(caps.getSecType())) {
			command.addReadData(IDCTrack.SECURITY);
		}
		command.addCommandListener(this);
		command.execute();
		getCommands().addCommand(new CancelCommand(getClass(), command));
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
			final WfsIDCStatus status = new IDCStatusCommand(service).call();
			if (LOG.isDebugEnabled()) {
				LOG.debug(method, "status=" + status);
			}
			switch (status.getMedia()) {
				case JAMMED:
				case PRESENT:
					return true;
				default:
					return false;
			}
		} catch (final XfsException e) {
			LOG.error(method, "Error retrieving status", e);
		}
		return false;
	}

	private void finishTask() {
		command.removeCommandListener(this);
		getCommands().clear();
		if (isCardPresent()) {
			getCommands().setNextCommand(new ExecuteTaskCommand(taskManager, new EjectCardTask(service)));
		} else {
			setCloseCommand();
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
		showException(e);
		finishTask();
	}
}