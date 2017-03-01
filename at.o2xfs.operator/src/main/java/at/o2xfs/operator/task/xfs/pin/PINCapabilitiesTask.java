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

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.operator.ui.content.text.Label;
import at.o2xfs.xfs.XfsException;
import at.o2xfs.xfs.pin.WFSPINCAPS;
import at.o2xfs.xfs.service.pin.PINService;
import at.o2xfs.xfs.service.pin.cmd.PINCapabilitiesCommand;

public class PINCapabilitiesTask extends PINServiceTask {

	private static final Logger LOG = LoggerFactory.getLogger(PINCapabilitiesTask.class);

	public PINCapabilitiesTask() {
		super();
	}

	public PINCapabilitiesTask(PINService service) {
		super(service);
	}

	@Override
	protected void execute() {
		String method = "execute()";
		final PINCapabilitiesCommand command = new PINCapabilitiesCommand(service);
		try {
			final WFSPINCAPS capabilities = command.call();
			showTable(capabilities);
		} catch (final XfsException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(method, "Error executing PINCapabilitiesCommand: " + command, e);
			}
			showException(e);
		}
	}

	private void showTable(final WFSPINCAPS caps) {
		final Table table = new Table(getClass(), "Capability", "Value");
		table.addRow(createRow("Type", caps.getType()));
		table.addRow(createRow("Compound", caps.isCompound()));
		table.addRow(createRow("KeyNum", caps.getKeyNum()));
		table.addRow(createRow("Algorithms", caps.getAlgorithms()));
		table.addRow(createRow("PinFormats", caps.getPINFormats()));
		table.addRow(createRow("DerivationAlgorithms", caps.getDerivationAlgorithms()));
		table.addRow(createRow("PresentationAlgorithms", caps.getPresentationAlgorithms()));
		table.addRow(createRow("Display", caps.getDisplay()));
		table.addRow(createRow("IDConnect", caps.isIDConnect()));
		table.addRow(createRow("IDKey", caps.getIDKey()));
		table.addRow(createRow("ValidationAlgorithms", caps.getValidationAlgorithms()));
		table.addRow(createRow("KeyCheckModes", caps.getKeyCheckModes()));
		table.addRow(createRow("Extra", caps.getExtra()));
		table.addRow(createRow("GuidLights", caps.getGuidLights()));
		table.addRow(createRow("PINCanPersistAfterUse", caps.isPINCanPersistAfterUse()));
		table.addRow(createRow("AutoBeep", caps.getAutoBeep()));
		table.addRow(createRow("HSMVendor", caps.getHSMVendor()));
		table.addRow(createRow("HSMJournaling", caps.isHSMJournaling()));
		table.addRow(createRow("RSAAuthenticationScheme", caps.getRSAAuthenticationSchemes()));
		table.addRow(createRow("RSASignatureAlgorithm", caps.getRSASignatureAlgorithms()));
		table.addRow(createRow("RSACryptAlgorithm", caps.getRSACryptAlgorithms()));
		table.addRow(createRow("RSAKeyCheckMode", caps.getRSAKeyCheckModes()));
		table.addRow(createRow("SignatureScheme", caps.getSignatureSchemes()));
		table.addRow(createRow("EMVImportSchemes", caps.getEMVImportSchemes()));
		table.addRow(createRow("EMVHashAlgorithm", caps.getEMVHashAlgorithms()));
		table.addRow(createRow("KeyImportThroughParts", caps.isKeyImportThroughParts()));
		table.addRow(createRow("ENCIOProtocols", caps.getEncIOProtocols()));
		table.addRow(createRow("TypeCombined", caps.isTypeCombined()));
		table.addRow(createRow("SetPinblockDataRequired", caps.isSetPinblockDataRequired()));
		table.addRow(createRow("KeyBlockImportFormats", caps.getKeyBlockImportFormats()));
		table.addRow(createRow("PowerSaveControl", caps.isPowerSaveControl()));
		table.addRow(createRow("AntiFraudModule", caps.isAntiFraudModule()));
		getContent().setUIElement(table);
	}

	private Object[] createRow(final String label, final Object value) {
		return new Object[] { new Label(getClass(), label), value };
	}
}