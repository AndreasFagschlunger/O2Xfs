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

package at.o2xfs.operator.task.xfs.ptr;

import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.operator.ui.content.text.Label;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.ptr.PaperStatus;
import at.o2xfs.xfs.service.ptr.PTRService;
import at.o2xfs.xfs.service.ptr.PTRStatusCallable;
import at.o2xfs.xfs.v3_00.ptr.PtrStatus3;
import at.o2xfs.xfs.v3_10.ptr.PtrStatus310;
import at.o2xfs.xfs.v3_20.ptr.PtrStatus320;

public class PTRStatusTask extends PTRServiceTask {

	private Table table = null;

	public PTRStatusTask() {
		super();
	}

	public PTRStatusTask(PTRService service) {
		super(service);
	}

	@Override
	protected void execute() {
		table = new Table(getClass(), "Component", "Value");
		try {
			final PtrStatus3 status = new PTRStatusCallable(service).call();
			addRow("Device", status.getDevice());
			addRow("Media", status.getMedia());
			addPaperSupplyStates(status.getPaper());
			addRow("Toner", status.getToner());
			addRow("Ink", status.getInk());
			addRow("Lamp", status.getLamp());
			addRow("RetractBins", status.getRetractBins());
			addRow("MediaOnStacker", status.getMediaOnStacker());
			addRow("Extra", status.getExtra());
			if (status instanceof PtrStatus310) {
				PtrStatus310 status310 = (PtrStatus310) status;
				addRow("GuidLights", status310.getGuidLights());
				addRow("DevicePosition", status310.getDevicePosition());
				addRow("PowerSaveRecoveryTime", status310.getPowerSaveRecoveryTime());
			}
			if (status instanceof PtrStatus320) {
				PtrStatus320 status320 = (PtrStatus320) status;
				addRow("PaperTypes", status320.getPaperTypes());
				addRow("AntiFraudModule", status320.getAntiFraudModule());
			}
			getContent().setUIElement(table);
		} catch (final XfsException e) {
			showException(e);
		}
	}

	private void addPaperSupplyStates(final PaperStatus[] paperStates) {
		for (int i = 0; i < paperStates.length; i++) {
			if (PaperStatus.NOTSUPP.equals(paperStates[i])) {
				continue;
			}
			addRow("Paper_" + i, paperStates[i]);
		}
	}

	private void addRow(final String label, final Object value) {
		table.addRow(new Label(getClass(), label), value);
	}
}