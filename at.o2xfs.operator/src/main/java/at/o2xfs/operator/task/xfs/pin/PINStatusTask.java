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

package at.o2xfs.operator.task.xfs.pin;

import java.util.Map;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.operator.ui.content.text.Label;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.pin.WFSPINSTATUS;
import at.o2xfs.xfs.service.pin.PINService;
import at.o2xfs.xfs.service.pin.cmd.PINStatusCommand;

public class PINStatusTask extends PINServiceTask {

	private final static Logger LOG = LoggerFactory.getLogger(PINStatusTask.class);

	private Table table = null;

	public PINStatusTask() {
		super();
	}

	public PINStatusTask(PINService service) {
		super(service);
	}

	@Override
	protected void execute() {
		String method = "execute()";
		try {
			final WFSPINSTATUS pinStatus = new PINStatusCommand(service).call();
			if (LOG.isInfoEnabled()) {
				LOG.debug(method, "pinStatus=" + pinStatus);
			}
			table = new Table(getClass(), "Component", "Status");
			fillTable(pinStatus);
			getContent().setUIElement(table);
		} catch (final XfsException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error executing PINStatusCommand for PINService: " + service, e);
			}
		}
	}

	private void fillTable(final WFSPINSTATUS pinStatus) {
		addRow("DeviceState", pinStatus.getDevice());
		addRow("EncryptionState", pinStatus.getEncStat());
		addRow("GuidLights", pinStatus.getGuidLights());
		addRow("AutoBeepModes", pinStatus.getAutoBeepModes());
		addRow("CertificateState", pinStatus.getCertificateState());
		addRow("DevicePosition", pinStatus.getDevicePosition());
		addRow("PowerSaveRecoveryTime", pinStatus.getPowerSaveRecoveryTime());
		addRow("AntiFraudModule", pinStatus.getAntiFraudModule());
		for (final Map.Entry<String, String> entry : pinStatus.getExtra().entrySet()) {
			table.addRow(entry.getKey(), entry.getValue());
		}
	}

	private void addRow(final String label, final Object value) {
		table.addRow(new Label(getClass().getSimpleName(), label), value);
	}
}