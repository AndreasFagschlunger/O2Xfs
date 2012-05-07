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

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.task.ExecuteTaskCommand;
import at.o2xfs.operator.task.Task;
import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.operator.ui.content.text.Label;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.idc.WFSIDCCAPS;
import at.o2xfs.xfs.service.cmd.idc.IDCCapabilitiesCommand;
import at.o2xfs.xfs.service.idc.IDCService;
import at.o2xfs.xfs.service.util.ExceptionUtil;

public class IDCCapabilities extends Task {

	private static final Logger LOG = LoggerFactory
			.getLogger(IDCCapabilities.class);

	private IDCService idcService = null;

	private Table table = null;

	public IDCCapabilities(final IDCService idcService) {
		ExceptionUtil.nullArgument("idcService");
		this.idcService = idcService;
	}

	@Override
	protected void execute() {
		final IDCCapabilitiesCommand command = new IDCCapabilitiesCommand(
				idcService);
		try {
			final WFSIDCCAPS capabilities = command.execute();
			createTable(capabilities);
		} catch (final XfsException e) {
			final String method = "execute()";
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error getting capabilities", e);
			}
			showError(e);
		}
		taskManager.setNextCommand(new ExecuteTaskCommand(getParent(),
				taskManager));
	}

	private void createTable(final WFSIDCCAPS caps) {
		table = new Table(getClass(), "Capability", "Value");
		addRow("Type", caps.getType());
		addRow("Compound", caps.isCompound());
		addRow("ReadTracks", caps.getReadTracks());
		addRow("WriteTracks", caps.getWriteTracks());
		addRow("ChipProtocols", caps.getChipProtocols());
		addRow("Cards", caps.getCards());
		addRow("SecType", caps.getSecType());
		addRow("PowerOnOption", caps.getPowerOnOption());
		addRow("PowerOffOption", caps.getPowerOffOption());
		addRow("FluxSensorProgrammable", caps.isFluxSensorProgrammable());
		addRow("ReadWriteAccessFollowingEject",
				caps.isReadWriteAccessFollowingEject());
		addRow("WriteMode", caps.getWriteMode());
		addRow("ChipPower", caps.getChipPower());
		addRow("Extra", caps.getExtra());
		addRow("DIPMode", caps.getDIPMode());
		addRow("MemoryChipProtocols", caps.getMemoryChipProtocols());
		addRow("GuidLights", caps.getGuidLights());
		addRow("EjectPosition", caps.getEjectPosition());
		addRow("PowerSaveControl", caps.isPowerSaveControl());
		addRow("ParkingStations", caps.getParkingStations());
		addRow("AntiFraudModule", caps.isAntiFraudModule());
		taskManager.setContent(table);
	}

	private void addRow(final String label, final Object value) {
		table.addRow(new Label(getClass(), label), value);
	}
}
